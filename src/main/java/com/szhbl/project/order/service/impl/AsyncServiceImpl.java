package com.szhbl.project.order.service.impl;

import com.szhbl.common.utils.ClientEmailUtil;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import com.szhbl.project.documentcenter.service.IOrderNoticeFileService;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.AsyncService;
import com.szhbl.project.order.service.IBusiShippingorderBackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异步实现类
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private IOrderNoticeFileService orderNoticeFileService;
    @Autowired
    private IDocOrderService docOrderService;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private IBusiClientsService busiClientsService;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private IBusiShippingorderBackupService busiShippingorderBackupService;

    //生成配舱通知书
    @Override
    @Async("threadPoolTaskExecutor")
    public void createOrderNotice(String orderId) {
        try {
            docOrderService.insertDocOrderMatch(orderId);
            orderNoticeFileService.createOrderNoticePDF(orderId);
            log.debug("配舱通知书异步调用===========");
        } catch (Exception e) {
            log.error("配舱通知书接口调用失败",e.toString(),e.getStackTrace());
        }
    }

    //跟单、业务、客户发送邮件
    @Override
    @Async("threadPoolTaskExecutor")
    public void orderSendEmail(String orderId,String content){
        ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(orderId);//订单表数据
        BusiShippingorders orderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderId);
        String yuyan = orderinfo.getYuyan();//0是中文订单1是英文
        String orderNumberOld = "";
        if(StringUtils.isNotNull(orderBackup)){
            orderNumberOld = orderBackup.getOrderNumber();
        }
        if(StringUtils.isNotNull(orderinfo)){
            String mailContent = "";
            if("1".equals(yuyan)){
                mailContent = ClientEmailUtil.enOrderEmailContent(content,orderinfo,orderNumberOld);
            }else{
                mailContent = ClientEmailUtil.chOrderEmailContent(content,orderinfo,orderNumberOld);
            }
            if(StringUtils.isNotEmpty(mailContent)){
                String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                //给客户、跟单、业务发送邮件提醒
                BusiClients clientsInfo = busiClientsService.selectBusiClientsById(orderinfo.getClientId());
                if(StringUtils.isNotNull(clientsInfo)){
                    //客户邮箱
                    String clientMail = orderinfo.getClientEmail(); //客户邮箱
                    clientMail = StringUtils.replace(clientMail,"；",";");
                    if(StringUtils.isNotEmpty(clientMail)){
                        String[] clientMailArr = clientMail.split(";");
                        for(int i=0;i<clientMailArr.length;i++){
                            try{
                                MailUtils.mailByAll("smtp.qiye.163.com","0","booking2@zih718.com","Lgzih718",clientMailArr[i], "订舱系统提醒-客户"+content+"(Booking System)",mailContent);
                            }catch (Exception e) {
                                log.error("客户邮箱发送失败",e.toString(),e.getStackTrace());
                            }
                        }
                    }
                    //推荐人邮箱
                    String tjrId = clientsInfo.getClientTjrId();
                    if(StringUtils.isNotEmpty(tjrId)){
                        String tjrEmail = busiShippingorderMapper.selectOrderTjrEmail(tjrId);
                        if(StringUtils.isNotEmpty(tjrEmail)){
                            String[] tjrEmailArr = tjrEmail.split(";");
                            for(int i=0;i<tjrEmailArr.length;i++){
                                try{
                                    MailUtils.mailByAll("smtp.qiye.163.com","0","booking2@zih718.com","Lgzih718",tjrEmailArr[i], "订舱系统提醒-客户"+content+"(Booking System)",mailContent);
                                }catch (Exception e) {
                                    log.error("推荐人邮箱发送失败",e.toString(),e.getStackTrace());
                                }
                            }
                        }
                    }
                    //跟单邮箱
                    List<String> merchandiserEmails;
                    String merchandiserIds = orderinfo.getOrderMerchandiserId();
                    String[] merchandiserId;
                    if(StringUtils.isNotEmpty(classEastandwest)){
//                        if(classEastandwest.equals("0")){ //西向跟单
//                            merchandiserIds = clientsInfo.getwMerchandiserId();
//                        }
//                        if(classEastandwest.equals("1")){ //东向跟单
//                            merchandiserIds = clientsInfo.geteMerchandiserId();
//                        }
                        if(StringUtils.isNotEmpty(merchandiserIds)){
                            merchandiserId = merchandiserIds.trim().split("\\|");
                            merchandiserEmails = busiShippingorderMapper.selectOrderMerEmail(merchandiserId);
                            if(merchandiserEmails.size()>0){
                                String[] sendEmails = merchandiserEmails.toArray(new String[merchandiserEmails.size()]); //邮箱集合转化为数组
                                for(int i=0;i<sendEmails.length;i++){
                                    try{
                                        MailUtils.mailByAll("smtp.qiye.163.com","0","booking2@zih718.com","Lgzih718",sendEmails[i], "订舱系统提醒-客户"+content+"(Booking System)",mailContent);
                                    }catch (Exception e) {
                                        log.error("跟单邮箱发送失败",e.toString(),e.getStackTrace());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //跟单、业务发送邮件
    @Override
    @Async("threadPoolTaskExecutor")
    public void orderSendEmailIn(String orderId,String content){
        ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(orderId);//订单表数据
        BusiShippingorders orderBackup = busiShippingorderBackupService.selectBusiShippingorderBackupById(orderId);
        String yuyan = orderinfo.getYuyan();//0是中文订单1是英文
        String orderNumberOld = "";
        if(StringUtils.isNotNull(orderBackup)){
            orderNumberOld = orderBackup.getOrderNumber();
        }
        if(StringUtils.isNotNull(orderinfo)){
            String mailContent = "";
            if("1".equals(yuyan)){
                mailContent = ClientEmailUtil.enOrderEmailContent(content,orderinfo,orderNumberOld);
            }else{
                mailContent = ClientEmailUtil.chOrderEmailContent(content,orderinfo,orderNumberOld);
            }
            if(StringUtils.isNotEmpty(mailContent)){
                String classEastandwest = orderinfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
                //给跟单、业务发送邮件提醒
                BusiClients clientsInfo = busiClientsService.selectBusiClientsById(orderinfo.getClientId());
                if(StringUtils.isNotNull(clientsInfo)){
                    //推荐人邮箱
                    String tjrId = clientsInfo.getClientTjrId();
                    if(StringUtils.isNotEmpty(tjrId)){
                        String tjrEmail = busiShippingorderMapper.selectOrderTjrEmail(tjrId);
                        if(StringUtils.isNotEmpty(tjrEmail)){
                            String[] tjrEmailArr = tjrEmail.split(";");
                            for(int i=0;i<tjrEmailArr.length;i++){
                                try{
                                    MailUtils.mailByAll("smtp.qiye.163.com","0","booking2@zih718.com","Lgzih718",tjrEmailArr[i], "订舱系统提醒-客户"+content+"(Booking System)",mailContent);
                                }catch (Exception e) {
                                    log.error("推荐人邮箱发送失败",e.toString(),e.getStackTrace());
                                }
                            }
                        }
                    }
                    //跟单邮箱
                    List<String> merchandiserEmails;
                    String merchandiserIds = orderinfo.getOrderMerchandiserId();
                    String[] merchandiserId;
                    if(StringUtils.isNotEmpty(classEastandwest)){
//                        if(classEastandwest.equals("0")){ //西向跟单
//                            merchandiserIds = clientsInfo.getwMerchandiserId();
//                        }
//                        if(classEastandwest.equals("1")){ //东向跟单
//                            merchandiserIds = clientsInfo.geteMerchandiserId();
//                        }
                        if(StringUtils.isNotEmpty(merchandiserIds)){
                            merchandiserId = merchandiserIds.trim().split("\\|");
                            merchandiserEmails = busiShippingorderMapper.selectOrderMerEmail(merchandiserId);
                            if(merchandiserEmails.size()>0){
                                String[] sendEmails = merchandiserEmails.toArray(new String[merchandiserEmails.size()]); //邮箱集合转化为数组
                                for(int i=0;i<sendEmails.length;i++){
                                    try{
                                        MailUtils.mailByAll("smtp.qiye.163.com","0","booking2@zih718.com","Lgzih718",sendEmails[i], "订舱系统提醒-客户"+content+"(Booking System)",mailContent);
                                    }catch (Exception e) {
                                        log.error("跟单邮箱发送失败",e.toString(),e.getStackTrace());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
