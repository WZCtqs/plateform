package com.szhbl.project.documentcenter.schedule;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.framework.config.rabbit.order.EmailRabbitmqConfig;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.client.dto.LatterNoticeDto;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description : 提单发送邮件
 * @Author : wangzhichao
 * @Date: 2020-10-09 10:03
 */
@Slf4j
@Component
public class DocScheduleListener {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IMailService mailService;

    @RabbitListener(queues = EmailRabbitmqConfig.EMAIL_LATTERS_QUEUE_GD)
    public void getGendan(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            LatterNoticeDto dto = FastJsonUtils.json2Bean(new String(message.getBody()), LatterNoticeDto.class);
            String subject = "电放-正本提单提醒-" + dto.getOrderNumber();
            String contents = "班列即将到站，请及时出具舱位号为：" + dto.getOrderNumber() + "的电放提单或正本提单，以免影响提货。";

            if (!"zhidanzu".equals(dto.getMerchandiserId())) {
                // 查询跟单员邮箱
                String[] ids =
                        dto.getMerchandiserId().contains("|")
                                ? dto.getMerchandiserId().split("|")
                                : new String[]{dto.getMerchandiserId()};
                List<SysUser> users = userService.selectUsersByDcIds(ids);
                String[] gdEmails = new String[users.size()];
                for (int i = 0; i < users.size(); i++) {
                    gdEmails[i] = users.get(i).getEmail();
                }
                if (gdEmails.length > 0) {
                    String gdEmail = Arrays.toString(gdEmails)
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");
                    log.info("gdEmails:" + gdEmail + "orderNumber:" + dto.getOrderNumber());
                    MailUtils.mailByAll(
                            "smtp.qiye.163.com",
                            "0",
                            "zhidan2@zih718.com",
                            "Document02",
                            gdEmail,
                            subject,
                            contents);
                }
            } else {
                MailUtils.mailByAll(
                        "smtp.qiye.163.com",
                        "0",
                        "zhidan2@zih718.com",
                        "Document02",
                        "zhidan2@zih718.com",
                        subject,
                        contents);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @RabbitListener(queues = EmailRabbitmqConfig.EMAIL_LATTERS_QUEUE)
    public void sendClient(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            LatterNoticeDto dto = FastJsonUtils.json2Bean(new String(message.getBody()), LatterNoticeDto.class);
            String subject = "电放-正本提单提醒-" + dto.getOrderNumber();
            String contents = "班列即将到站，请及时出具舱位号为：" + dto.getOrderNumber() + "的电放提单或正本提单，以免影响提货。";
            String emails = dto.getClientEmail()
                    .replace(";", ",")
                    .replace("|", ",")
                    .replace("/", ",")
                    .replace("；", ",");
            log.info("clientEmails:" + emails + "orderNumber:" + dto.getOrderNumber());
            MailUtils.mailByAll(
                    "smtp.qiye.163.com",
                    "0",
                    "zhidan2@zih718.com",
                    "Document02",
                    emails,
                    subject,
                    contents);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}