package com.szhbl.project.documentcenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.file.FileUploadUtils;
import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.cabinarrangement.domain.BusiShippingorderPc;
import com.szhbl.project.cabinarrangement.service.IBusiShippingorderPcService;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import com.szhbl.project.documentcenter.service.IOrderNoticeFileService;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderGoodsService;
import com.szhbl.project.order.service.IShippingorderExaminfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 客户端单证上传接口
 *
 * @author HP
 */
@Slf4j
@Api(tags = "客户端单证上传接口")
@RestController
@RequestMapping("client/document")
public class ClientDoucmentController extends BaseController {

    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IOrderNoticeFileService orderNoticeFileService;

    @Autowired
    private IBusiShippingorderPcService busiShippingorderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private IShippingorderExaminfoService shippingorderExaminfoService;
    @Autowired
    private IBusiShippingorderGoodsService busiShippingorderGoodsService;

    /**
     * 修改单证
     */
    @ApiOperation(value = "修改单证")
    @PreAuthorize("@ss.hasPermi('document:order:edit')")
    @Log(title = "托书单证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderDocument docOrderDocument) {
        return toAjax(orderDocumentService.updateOrderDocument(docOrderDocument));
    }

    /**
     * 删除单证
     */
    @ApiOperation(value = "删除单证")
    @PreAuthorize("@ss.hasPermi('document:order:remove')")
    @Log(title = "托书单证", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderDocumentService.deleteOrderDocumentByIds(ids));
    }

    /**
     * 文件上传
     */
    @Log(title = "客户端上传文件", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "客户端上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "file", required = true),
    })
    @PostMapping("/fileupload")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            /*本地上传*/
            String fileUrl = FileUploadUtils.upload(SzhblConfig.getUploadPath(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileUrl", fileUrl);
            return ajax;
        } else {
            return AjaxResult.error();
        }
    }

    @ApiOperation("邮件发送客户提醒单证上传")
    @GetMapping("/emailNotice")
    public AjaxResult emailNotice(String orderNumber, String orderId) {
        return orderDocumentService.sendClientEmailNotice(orderNumber, orderId);
    }

    /**
     * 提货时间
     */
    @ApiOperation(value = "提货时间")
    @ApiImplicitParam(name = "orderId,orderNumber,uploadBy,uploadDept,fileTypeKey,pickGoodsTime,pickGoodsContacts,pickGoodsAddress", value = "提货时间参数", required = true)
    @Log(title = "提货时间", businessType = BusinessType.INSERT)
    @PostMapping("/pickgoodstime")
    public AjaxResult getPickGoodsTime(@RequestBody DocOrderDocument docOrderDocument) {
        BusiShippingorders orderInfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(docOrderDocument.getOrderId()); //订单表原数据
        docOrderDocument.setFormSystem("1");
        docOrderDocument.setUploadTime(new Date());
        docOrderDocument.setCreateTime(new Date());
        docOrderDocument.setPickGoodsTimecConfirm(1);
        Map map = new HashMap();
        map.put("orderId", docOrderDocument.getOrderId());
        map.put("pickGoodsTimec", docOrderDocument.getPickGoodsTimec());
        map.put("pickGoodsContacts", docOrderDocument.getPickGoodsContacts());
        map.put("pickGoodsAddress", docOrderDocument.getPickGoodsAddress());
        map.put("pickGoodsPhone", docOrderDocument.getPickGoodsPhone());
        map.put("confirmRemark", docOrderDocument.getConfirmRemark());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ShipOrderRabbitmq.CLIENT_TOPIC_EXCHANGE, ShipOrderRabbitmq.PICK_GOODSTIME_ROUTINGKEY, map, correlationData);
        BusiShippingorderPc busiShippingorderPc = new BusiShippingorderPc();
        busiShippingorderPc.setOrderId(docOrderDocument.getOrderId());
        busiShippingorderPc.setShipOrderUnloadtime(DateUtils.parseDate(docOrderDocument.getPickGoodsTimec()));
        busiShippingorderService.updateBusiShippingorder(busiShippingorderPc);
        orderDocumentService.deleteByOrderIdAndFileTypeKey(docOrderDocument.getOrderId(), DocumentsType.PICK_GOODS_FILE);
        for (int i = 0; i < docOrderDocument.getUrlList().size(); i++) {
            DocOrderDocument doc = new DocOrderDocument();
            doc.setOrderNumber(docOrderDocument.getOrderNumber());
            doc.setOrderId(docOrderDocument.getOrderId());
            doc.setFormSystem("1");
            doc.setUploadTime(new Date());
            doc.setCreateTime(new Date());
            doc.setPickGoodsTimecConfirm(1);
            doc.setFileUrl(docOrderDocument.getUrlList().get(i).getUrl());
            doc.setFileName(docOrderDocument.getUrlList().get(i).getFileName());
            doc.setFileTypeKey("pick_goods_time_filec");
            orderDocumentService.insertOrderDocument(doc);
            // 提货文件--发送箱行亚欧、箱管系统、关务
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "insert");
            jsonObject.put("filename", docOrderDocument.getUrlList().get(i).getFileName());
            jsonObject.put("order_id", docOrderDocument.getOrderId());
            jsonObject.put("url", docOrderDocument.getUrlList().get(i).getUrl());
            CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.CLIENT_TOPIC_EXCHANGE, "client.pickgoodstimefile", jsonObject, data);
        }
        //新增订舱公告
        if (StringUtils.isNotNull(orderInfo) && StringUtils.isNotEmpty(docOrderDocument.getPickGoodsTimec())) {
            String shipUnloadTimeN = DateUtils.parseStr(DateUtils.parseDate(docOrderDocument.getPickGoodsTimec())); //统一日期格式
            if (!StringUtils.equals(DateUtils.parseStr(orderInfo.getShipOrderUnloadtime()), shipUnloadTimeN)) {
                String orderid = docOrderDocument.getOrderId();
                String editrecord = "<th>修改人：客户端单证中心,修改时间：" + DateUtils.parseStr(DateUtils.getNowDate()) + "</th>";
                editrecord = editrecord + "提货时间：由" + DateUtils.parseStr(orderInfo.getShipOrderUnloadtime()) + "修改为" + docOrderDocument.getPickGoodsTimec() + "<td><###>";
                ShippingorderExaminfo orderExaminfo = new ShippingorderExaminfo();
                orderExaminfo.setOrderId(orderid);
                orderExaminfo.setExamDate(DateUtils.getNowDate());
                orderExaminfo.setEditRecord(editrecord);
                orderExaminfo.setExamInfo("提货时间修改");
                orderExaminfo.setExamType("2");  //插入为2，失败为1，成功为0
                shippingorderExaminfoService.insertShippingorderExaminfo(orderExaminfo);
                //更新托书修改记录
                BusiShippingorderGoods goodsInfo = busiShippingorderGoodsService.selectBusiShippingorderGoodsByOrderId(orderid);//商品表原数据
                if (StringUtils.isNotNull(goodsInfo)) {
                    BusiShippingorderGoods busiShippingGoods = new BusiShippingorderGoods(); //更新商品表
                    String goodsHistoryEditrecord = goodsInfo.getGoodsHistoryEditrecord() + editrecord;
                    busiShippingGoods.setGoodsHistoryEditrecord(goodsHistoryEditrecord);
                    busiShippingGoods.setOrderId(orderid);
                    busiShippingorderGoodsService.updateBusiShippingorderGoods(busiShippingGoods);
                }
                BusiShippingorderPc orderUpd = new BusiShippingorderPc(); //更新托书表
                orderUpd.setOrderId(orderid);
                orderUpd.setTjTime(DateUtils.getNowDate());
                orderUpd.setCreatedate(DateUtils.getNowDate());
                orderUpd.setIsupdate("1");
                busiShippingorderService.updateBusiShippingorder(orderUpd);
            }
        }
        //最新托书推送子系统
        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(docOrderDocument.getOrderId());
        if (StringUtils.isNotNull(orderInfoRabbmq)) {
            String messagetype = "7";
            try {
                commonService.orderInfoMQ(orderInfoRabbmq, messagetype); //推送消息队列
            } catch (JsonProcessingException e) {
                log.error("客户编辑提货时间发送子系统失败", e.toString(), e.getStackTrace());
            }
        }
        return toAjax(orderDocumentService.insertOrderDocument(docOrderDocument));
    }

    @GetMapping("/followVehicle/download")
    @ApiOperation("下载模板")
    public void downloadModel(HttpServletResponse response, String orderId) {
        ServletOutputStream out = null;
        // todo 判断如何下载
        String port = orderNoticeFileService.selectPortByOrderId(orderId);
        String typeName = (port.equals("86_833418") ? "山口" : "二连") + "随车模板.xlsx";
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("templates/" + typeName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode(typeName, "UTF-8");
            //fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @GetMapping("/followVehicle/downloadBack")
    @ApiOperation("下载模板")
    public void downloadBack(HttpServletResponse response) {
        ServletOutputStream out = null;
        String typeName = "回程随车模板.xlsx";
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("templates/" + typeName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode(typeName, "UTF-8");
            //fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
