package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DocUrlHandle;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.file.FileUploadUtils;
import com.szhbl.common.utils.pdf.OrderNoticePDFUtils0;
import com.szhbl.common.utils.pdf.OrderNoticePDFUtils1;
import com.szhbl.common.utils.pdf.PdfConstants;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.email.IMailService;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.OrderNoticeFile;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.mapper.BusiShippingorderOneInstorageMapper;
import com.szhbl.project.documentcenter.mapper.OrderNoticeFileMapper;
import com.szhbl.project.documentcenter.service.IBusiStationService;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import com.szhbl.project.documentcenter.service.IOrderNoticeFileService;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 入货通知书Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-03
 */
@Slf4j
@Service
public class OrderNoticeFileServiceImpl implements IOrderNoticeFileService {

    private static String subject = "订舱系统入货通知书 Booking System Shipping Order";
    private static String contents = "入货通知书已发送，请登录订舱系统查看入货通知书具体内容.";
    @Resource
    private OrderNoticeFileMapper orderNoticeFileMapper;

    @Resource
    private BusiShippingorderOneInstorageMapper instorageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IBusiStationService stationService;
    @Autowired
    private ISysUserService userService;

    /**
     * 入货通知书列表查询
     *
     * @param orderNoticeFile
     * @return
     */
    @Override
    public List<OrderNoticeFile> orderNoticeFileList(OrderNoticeFile orderNoticeFile) {
        String deptCode = orderNoticeFile.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (orderNoticeFile.getDeptCode().contains("YWB")) {
                if (orderNoticeFile.getDeptCode().length() > 15) {
                    orderNoticeFile.setReadType("0");
                } else if (deptCode.equals("YWB1000100")) { // 业务部职级人员，根据规则查询
                    orderNoticeFile.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) { // 业务部职级人员，根据规则查询
                    orderNoticeFile.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) { // 业务部职级人员，根据规则查询
                    orderNoticeFile.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) { // 业务部职级人员，根据规则查询
                    orderNoticeFile.setReadType("5");
                } else {
                    orderNoticeFile.setReadType("1");
                }
            }
        }
        List<OrderNoticeFile> list = orderNoticeFileMapper.orderNoticeFileList(orderNoticeFile);
        for (OrderNoticeFile file : list) {
            if (null == file.getClassSite()) {
                file.setClassSite("");
            }
            file.setFileUrl(DocUrlHandle.urlHandle(file.getFileUrl()));
        }
        return list;
    }

    /**
     * 发送邮件(不带附件)
     *
     * @param orderIds
     * @return
     */
    @Override
    public AjaxResult simpleEmail(String[] orderIds) {
        /*查询订单信息获取发货人邮箱*/
        List<OrderNoticeFile> list = orderNoticeFileMapper.selectConsignorEmailByOrderIds(orderIds);
        /*循环发送邮箱*/
        String errorNumber = "";
        for (int i = 0; i < list.size(); i++) {
            String emails =
                    list.get(i)
                            .getClientEmail()
                            .replace(";", ",")
                            .replace("|", ",")
                            .replace("/", ",")
                            .replace("；", ",");
            log.info(emails);
            String[] emailArr = emails.split(",");
            for (int j = 0; j < emailArr.length; j++) {
                try {
                    mailService.sendSimpleMail(emailArr[j], subject, contents);
                } catch (Exception e) {
                    errorNumber += list.get(i).getOrderNumber() + ";";
                }
            }
        }
        if (errorNumber.equals("")) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(200, errorNumber + "存在邮箱发送失败情况，请注意查看邮箱格式是否正确！");
        }
    }

    @Override
    public AjaxResult fileEmail(String[] orderIds) {
        /*查询订单信息获取发货人邮箱*/
        List<OrderNoticeFile> list = orderNoticeFileMapper.selectConsignorEmailByOrderIds(orderIds);
        /*循环发送邮箱*/
        String errorNumber = "";
        for (int i = 0; i < list.size(); i++) {
            String contents =
                    "<html>\n"
                            + "<head>\n"
                            + "</head>\n"
                            + "<body>\n<span>"
                            + list.get(i).getClientUnit()
                            + ":</span><br/>\n"
                            + "<span>&nbsp;&nbsp;&nbsp;&nbsp;您好, "
                            + list.get(i).getOrderNumber()
                            + "入货通知书已随附件发送，请您注意查收!</span><br/>\n"
                            + "<span>&nbsp;&nbsp;&nbsp;&nbsp;Hello, "
                            + list.get(i).getOrderNumber()
                            + "Shipping Order has sent to you by email, please check it in time. Thank you!</span><br/>\n"
                            + "</body>\n"
                            + "</html>\n";
            String emails =
                    list.get(i)
                            .getClientEmail()
                            .replace(";", ",")
                            .replace("|", ",")
                            .replace("/", ",")
                            .replace("；", ",");
            log.info(emails);
            String[] emailArr = emails.split(",");
            for (int j = 0; j < emailArr.length; j++) {
                try {
                    mailService.sendAttachmentsMail(emailArr[j], subject, contents, list.get(i).getFileUrl());
                } catch (Exception e) {
                    errorNumber += list.get(i).getOrderNumber() + ";";
                }
            }
        }
        if (errorNumber.equals("")) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(200, errorNumber + "存在邮箱发送失败情况，请注意查看邮箱格式是否正确！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrderNoticeFile(String orderId) {
        /*删除入货通知书已发送信息*/
        instorageMapper.deleteByOrderId(orderId);
        /*删除入货通知书文件信息*/
        return orderNoticeFileMapper.deleteOrderNoticeFile(orderId);
    }

    @Override
    public boolean createOrderNoticePDF(String orderId) {
        BusiShippingorder order = orderNoticeFileMapper.selectBusiShippingorderById(orderId);
        if (StringUtils.isNotEmpty(order.getClientTjr())) {
            order.setClientTjr(
                    order.getClientTjr().indexOf("/") > 0
                            ? order.getClientTjr().substring(0, order.getClientTjr().indexOf("/"))
                            : order.getClientTjr());
        } else {
            order.setClientTjr("/");
        }
        if (StringUtils.isNotEmpty(order.getOrderMerchandiserId())) {
            boolean hasMoreIds = order.getOrderMerchandiserId().contains("|");
            String[] ids =
                    hasMoreIds
                            ? order.getOrderMerchandiserId().split("|")
                            : new String[]{order.getOrderMerchandiserId()};
            List<SysUser> users = userService.selectUsersByDcIds(ids);
            StringBuilder sb = new StringBuilder();
            for (SysUser user : users) {
                sb.append(user.getNickName()).append(":").append(user.getPhonenumber()).append("; ");
            }
            order.setOrderMerchandiser(sb.toString());
        }
        log.info(order.getOrderNumber() + " create pdf msg : " + order.toString());
        try {
            new Thread(
                    () -> {
                        try {
                            if (order.getClassEastAndWest().equals("0")) {
                                OrderNoticePDFUtils0.createOrderNoticePDF0(order);
                            } else {
                                //                        BusiStation station =
                                // stationService.selectBusiStationById(order.getStationID());
                                //                        if (null != station) {
                                String zp = order.getIsConsolidation(); // 0:整，1：拼
                                String bg = order.getClientOrderBindingWay(); // 0:是，1：否
                                String flag = zp + bg;
                                switch (flag) {
                                    case "00":
                                        order.setCuntofftime(order.getFclCustomsTime());
                                        break;
                                    case "01":
                                        order.setCuntofftime(order.getFclCustomsNotTime());
                                        break;
                                    case "10":
                                        order.setCuntofftime(order.getLclCustomsTime());
                                        break;
                                    case "11":
                                        order.setCuntofftime(order.getLclCustomsNotTime());
                                        break;
                                }
                                //                        }
                                OrderNoticePDFUtils1.createOrderNoticePDF1(order);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    })
                    .start();
            DocOrderDocument document = new DocOrderDocument();
            document.setFormSystem("班列平台");
            document.setOrderId(order.getOrderId());
            document.setOrderNumber(order.getOrderNumber());
            document.setFileTypeKey(DocumentsType.ORDER_NOTICE_FILE);
            String fileName =
                    FileUploadUtils.getPathFileName(
                            PdfConstants.getNoticePDFPath(), order.getOrderNumber() + ".pdf");
            document.setFileUrl(SzhblConfig.getFileServer() + fileName);
            document.setFileName(order.getOrderNumber() + ".pdf");
            document.setUploadTime(new Date());
            document.setCreateTime(new Date());
            List<DocOrderDocument> list =
                    orderDocumentService.getByOrderIdFileKey(orderId, DocumentsType.ORDER_NOTICE_FILE);
            if (list.size() > 0) {
                orderDocumentService.updateOrderDocumentByOrderId(document);
            } else {
                orderDocumentService.insertOrderDocument(document);
            }
            /*----------发送邮件----------*/
            String emails =
                    order
                            .getClientEmail()
                            .replace(";", ",")
                            .replace("|", ",")
                            .replace("/", ",")
                            .replace("；", ",");
            log.info(emails);
            String[] emailArr = emails.split(",");
            int index = 0;
            String contents =
                    "<html>\n"
                            + "<head>\n"
                            + "</head>\n"
                            + "<body>\n<span>"
                            + order.getClientUnit()
                            + ":</span><br/>\n"
                            + "<span>&nbsp;&nbsp;&nbsp;&nbsp;您好, "
                            + order.getOrderNumber()
                            + " 入货通知书已发送，请您注意查收!</span><br/>\n"
                            + "<span>&nbsp;&nbsp;&nbsp;&nbsp;Hello, "
                            + order.getOrderNumber()
                            + " Shipping Order has sent to you , please check it in time. Thank you!</span><br/>\n"
                            + "</body>\n"
                            + "</html>\n";
            for (int j = 0; j < emailArr.length; j++) {
                try {
                    // 发送客户 入仓通知书功能
                    mailService.sendHtmlMail(emailArr[j], subject, contents);
                    index++;
                } catch (Exception e) {
                }
            }
            /*--------------------系统推送--------------------*/
            OrderDocUrl orderDocUrl = new OrderDocUrl();
            orderDocUrl.setFileName(document.getFileName());
            orderDocUrl.setFileUrl(document.getFileUrl());
            orderDocUrl.setOrderId(orderId);
            orderDocUrl.setOrderNumber(document.getOrderNumber());
            CorrelationData correlationData = new CorrelationData(orderId);
            rabbitTemplate.convertAndSend(
                    ShipOrderRabbitmq.ORDER_NOTICE_PDF_TOPIC_EXCHANGE,
                    ShipOrderRabbitmq.ORDER_NOTICE_PDF_ROUTINGKEY,
                    orderDocUrl,
                    correlationData);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public String selectPortByOrderId(String orderId) {
        return orderNoticeFileMapper.selectPortByOrderId(orderId);
    }

    @Override
    public List<BusiShippingorder> selectOrderByClassBh(String classBh) {
        return orderNoticeFileMapper.selectOrderByClassBh(classBh);
    }

    @Override
    public List<String> getExamineOrderId() {
        return orderNoticeFileMapper.getExamineOrderId();
    }
}
