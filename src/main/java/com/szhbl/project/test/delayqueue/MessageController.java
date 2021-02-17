package com.szhbl.project.test.delayqueue;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.sms.YunPianSMSUtils;
import com.szhbl.framework.config.rabbit.delay.DelayMsgProvider;
import com.szhbl.framework.config.rabbit.delay.OrderDocDelayMsgProvider;
import com.szhbl.project.documentcenter.domain.vo.DocOrderMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delay")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private DelayMsgProvider messageProvider;

    @Autowired
    private OrderDocDelayMsgProvider orderDocDelayMsgProvider;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/sendMessage/{delay}")
    public void sendMessage(@PathVariable("delay")Long delay) throws Exception {
//        MessagePojo pojo = new MessagePojo();
//        DocOrderMsg msg = new DocOrderMsg();
//        msg.setClassDate(new Date());
//        msg.setFileTypeKey("123");
//        msg.setFileTypeText("456");
//        msg.setFileTypeTextEn("qwer");
//        msg.setOrderNumber("ZIH234D23");
//        pojo.setMessage(msg);
//        sendDelayMsg(msg, delay);

        YunPianSMSUtils.sendDriverNewOrder("17638568129","123");
    }


    /**
     * 发送延迟消息
     * @param msg 消息对象
     * @param delay 延迟时间
     * @throws Exception
     */
    public synchronized void sendDelayMsg(DocOrderMsg msg, Long delay) throws Exception {
        // 统计每天发送量并作为延迟秒数附加
//        String delayKey = Constants.RABBITMQ + ":" + DateUtils.dateTime();
//        redisTemplate.opsForValue().increment(delayKey);
        // 自增消息id
        String delayMessageKey =  Constants.RABBITMQ + ":" + "delayMessageId";
        redisTemplate.opsForValue().increment(delayMessageKey);

        MessagePojo pojo = new MessagePojo();
        pojo.setMessage(msg);
        pojo.setMessageId(redisTemplate.opsForValue().get(delayMessageKey).toString());

        // 判断秒数是否大于 864,000‬ （10天秒数：10d*24h*60m*60s）
        if(delay > Constants.TENDAY_SECOND){
            // 大于10天的话。延迟10天，并将延迟数 减10天记入消息
            pojo.setDelayNow(Constants.TENDAY_SECOND);
            pojo.setDelayNext(delay - Constants.TENDAY_SECOND);
        } else {
            pojo.setDelayNow(delay);
            pojo.setDelayNext(0L);
        }
        messageProvider.sendMessage(pojo);
    }
}
