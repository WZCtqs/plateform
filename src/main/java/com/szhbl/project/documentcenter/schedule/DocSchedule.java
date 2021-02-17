package com.szhbl.project.documentcenter.schedule;

import com.szhbl.framework.config.rabbit.order.EmailRabbitmqConfig;
import com.szhbl.project.client.dto.LatterNoticeDto;
import com.szhbl.project.client.service.IDocClientLattersService;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description : 单证中心定时任务
 * @Author : wangzhichao
 * @Date: 2020-10-08 14:54
 */
@Component
public class DocSchedule {

    @Autowired
    private IDocClientLattersService clientLattersService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IOrderDocumentService orderDocumentService;

    /**
     * 每天上午9点30分开始执行
     */
    @Scheduled(cron = "0 30 9 1/1 * ? ")
    @ApiOperation("更新班列状态")
    public void emailNoticeToClient() {
        List<LatterNoticeDto> latterNoticeDtos = clientLattersService.selectOrderToEmailNoticeLatters();
        List<LatterNoticeDto> lastList = new ArrayList<>();
        // 客户信息分组
        Set<String> clientSet = new HashSet<>();
        Set<String> gendanSet = new HashSet<>();
        StringBuffer orderNumber = new StringBuffer();
        for (LatterNoticeDto dto : latterNoticeDtos) {
            /*查询是否已出正本*/
            List<DocOrderDocument> list = orderDocumentService.getByOrderIdFileKey(dto.getOrderId(), "lading_bill_formal_file");
            if (list.size() == 0) {
                clientSet.add(dto.getClientEmail());
                gendanSet.add(dto.getMerchandiserId());
                orderNumber.append(dto.getOrderNumber() + ";");
                lastList.add(dto);
            }
        }
        // 提取客户需要提醒的托书
        List<LatterNoticeDto> clientList = new ArrayList<>();
        for (String client : clientSet) {
            LatterNoticeDto dto = new LatterNoticeDto();
            dto.setClientEmail(client);
            String orderNumbers = "";
            for (LatterNoticeDto noticeDto : lastList) {
                if (noticeDto.getClientEmail().equals(client)) {
                    orderNumbers = orderNumbers + noticeDto.getOrderNumber() + ";";
                }
            }
            dto.setOrderNumber(orderNumbers);
            clientList.add(dto);
        }
        System.out.println(clientList.size());

        List<LatterNoticeDto> gendanList = new ArrayList<>();
        for (String client : gendanSet) {
            LatterNoticeDto dto = new LatterNoticeDto();
            dto.setMerchandiserId(client);
            String orderNumbers = "";
            for (LatterNoticeDto noticeDto : lastList) {
                if (noticeDto.getMerchandiserId().equals(client)) {
                    orderNumbers = orderNumbers + noticeDto.getOrderNumber() + ";";
                }
            }
            dto.setOrderNumber(orderNumbers);
            gendanList.add(dto);
        }

        LatterNoticeDto noticeDto = new LatterNoticeDto();
        noticeDto.setOrderNumber(orderNumber.toString());
        noticeDto.setMerchandiserId("zhidanzu");
        gendanList.add(noticeDto);

        for (LatterNoticeDto dto : clientList) {
            rabbitTemplate.convertAndSend(EmailRabbitmqConfig.EMAIL_EXCHANGE, EmailRabbitmqConfig.EMAIL_LATTERS_ROUTINGKEY, dto);
        }
        for (LatterNoticeDto dto : gendanList) {
            rabbitTemplate.convertAndSend(EmailRabbitmqConfig.EMAIL_EXCHANGE, EmailRabbitmqConfig.EMAIL_LATTERS_ROUTINGKEY_GD, dto);
        }
    }

}
