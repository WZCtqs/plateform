package com.szhbl.project.enquiry.listenner;

import com.rabbitmq.client.Channel;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.mapper.BookingInquiryMapper;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JsDeleteListenner {

    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private BookingInquiryMapper bookingInquiryMapper;

//    @RabbitListener(queues = "business_quoter_delete_queue_js")
    public void orderInfoListener(Channel channel, Message message){
        try {
            log.debug("集疏系统发来的数据-------------------------集疏系统发来的询价结果删除数据");
            String data = StringEscapeUtils.unescapeJavaScript(new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            data = StringUtils.substring(data,0, data.length());
            Map<String,String> map = FastJsonUtils.json2Map(data);
            log.debug("询价结果删除数据map-----------------"+map);
            String inquiryNum=map.get("inquiryCode");//集疏询价编码
            BookingInquiryResult bookingInquiryResult=bookingInquiryResultMapper.selectBookingInquiryResultByInquiryNum(inquiryNum);
            if(bookingInquiryResult!=null){
                BookingInquiry bookingInquiry = bookingInquiryMapper.selectBookingInquiryById(bookingInquiryResult.getInquiryId());
//                Integer num=busiShippingorderMapper.selectOrderNumByInquiryResultId(bookingInquiryResult.getId());//查询询价结果是否已经绑定订单
                if(!"3".equals(bookingInquiry.getInquiryState())){
                    //查询询价绑定的结果
                    List<BookingInquiryResult> bookingInquiryResultList=bookingInquiryResultMapper.selectBookingInquiryResultWithInquiryId(bookingInquiryResult.getInquiryId());
                    //只有一条，置空对应字段值
                    if(bookingInquiryResultList.size()==1){
                        //去 DeliveryFees，DeliveryRemark，DropStation，DeliveryAging
                        if("0".equals(bookingInquiry.getEastOrWest())){
                            bookingInquiryResultList.get(0).setDeliveryFees(null);
                            bookingInquiryResultList.get(0).setDeliveryRemark(null);
                            bookingInquiryResultList.get(0).setDropStation(null);
                            bookingInquiryResultList.get(0).setDeliveryAging(null);
                            bookingInquiryResultList.get(0).setDeliveryCurrencyType(null);
                        }
                        //回 PickUpFees，PickUpRemark，UploadStation，PickUpAging
                        else if("1".equals(bookingInquiry.getEastOrWest())){
                            bookingInquiryResultList.get(0).setPickUpFees(null);
                            bookingInquiryResultList.get(0).setPickUpRemark(null);
                            bookingInquiryResultList.get(0).setUploadStation(null);
                            bookingInquiryResultList.get(0).setPickUpAging(null);
                            bookingInquiryResultList.get(0).setPickUpCurrencyType(null);
                        }
                        //InquiryNum，jsBidder，JsRemark
                        bookingInquiryResultList.get(0).setInquiryNum(null);
                        bookingInquiryResultList.get(0).setJsBidder(null);
                        bookingInquiryResultList.get(0).setJsRemark(null);
                        bookingInquiryResultMapper.updateBookingInquiryResultById(bookingInquiryResultList.get(0));
                        //更新为询价中
                        bookingInquiry.setInquiryState("1");
                        bookingInquiryMapper.updateBookingInquiry(bookingInquiry);
                    }
                    //多条直接删除该条询价结果
                    else{
                        bookingInquiryResultMapper.deleteBookingInquiryResultById(bookingInquiryResult.getId());
                    }
                }
            }
        }catch (IOException e) {
            log.error("集疏报价结果数据删除失败：{}",e.toString(),e.getStackTrace());
        }
    }
}
