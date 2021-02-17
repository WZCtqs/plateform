package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.file.FileUploadUtils;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.client.VO.ProblemFileVo2;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocSendGoodsVO;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import com.szhbl.project.order.domain.BusiOrderColumn;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description : 自送货接口
 * 客户上传自备箱装箱照，存放数据库格式为  箱号，铅封号，装箱照url拼接
 * @Author : wangzhichao
 * @Date: 2020-12-17 17:21
 */
@Slf4j
@RestController
@RequestMapping("/docSendGoodsSelf")
public class DocOrderSendGoodsSelfController {

    @Autowired
    private IOrderDocumentService docOrderDocumentService;

    @Autowired
    private IDocOrderService docOrderService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommonService commonService;

    /**
     * 根据箱号
     *
     * @param orderId
     * @param containerCount
     * @return
     */
    @GetMapping("/getConList")
    public List<DocSendGoodsVO> getList(String orderId, String orderNumber, Integer containerCount) {
        BusiOrderColumn busiOrderColumn = busiShippingorderService.selectBusiOrderColumnByOrderId(orderId);
        DocOrderDocument param = new DocOrderDocument();
        param.setOrderId(orderId);
        param.setFileTypeKey("boxing_photo_file");
        // 查询来自客户端上传和平台端上传的装箱照信息
        List<DocOrderDocument> documentList = docOrderDocumentService.selectSendGoodsSelf(param);
        List<DocSendGoodsVO> list = new ArrayList<>();
        for (int i = 0; i < containerCount; i++) {
            DocSendGoodsVO goodsVO = new DocSendGoodsVO();
            goodsVO.setOrderId(orderId);
            goodsVO.setOrderNumber(orderNumber);
            goodsVO.setClassEastAndWest(busiOrderColumn.getClassEastandwest());
            goodsVO.setIsConsolidation(busiOrderColumn.getIsConsolidation());
            goodsVO.setShipOrderBinningWay(busiOrderColumn.getShipOrderBinningWay());
            if (documentList.size() > i) {
                goodsVO.setBoxingphotoFail(documentList.get(i).getBoxingphotoFail());
                goodsVO.setBoxingphotoStatus(documentList.get(i).getBoxingphotoStatus());
                goodsVO.setConSealFail(documentList.get(i).getContainerFail());
                goodsVO.setConSealStatus(documentList.get(i).getContainerStatus());
                goodsVO.setDocId(documentList.get(i).getId());
                goodsVO.setSealNumber(documentList.get(i).getSealnumber());
                goodsVO.setContainerNo(documentList.get(i).getContainerNo());
                if (StringUtils.isNotEmpty(documentList.get(i).getFileName())) {
                    String[] fileUrlArr = documentList.get(i).getFileUrl().split(";");
                    String[] fileNameArr = documentList.get(i).getFileName().split(";");
                    List<ProblemFileVo2> urls = new ArrayList<>();
                    for (int j = 0; j < fileNameArr.length; j++) {
                        ProblemFileVo2 url = new ProblemFileVo2();
                        url.setFileName(fileNameArr[j]);
                        url.setUrl(fileUrlArr[j]);
                        urls.add(url);
                    }
                    goodsVO.setUrlList(urls);
                }
            }
            list.add(goodsVO);
        }
        return list;
    }

    /**
     * 保存箱号 铅封号
     *
     * @param request
     * @return
     */
    @PostMapping("saveConAndSeal")
    @PreAuthorize("@ss.hasPermi('document:boxphoto:save')")
    @Transactional
    public AjaxResult uploadBoxingPhoto(@RequestBody DocSendGoodsVO request) {
        if (!commonService.checkDigit(request.getContainerNo())) {
            return AjaxResult.success("con error");
        } else if (StringUtils.isEmpty(request.getContainerNo())) {
            return AjaxResult.success("con error");
        } else {
            Date nowDate = new Date();
            DocOrderDocument document = new DocOrderDocument();
            document.setId(request.getDocId());
            document.setOrderId(request.getOrderId());
            document.setOrderNumber(request.getOrderNumber());
            document.setCreateTime(nowDate);
            document.setUploadTime(nowDate);
            document.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
            document.setContainerNo(request.getContainerNo().trim());
            document.setSealnumber(request.getSealNumber().trim());
            if (request.getUrlList().size() > 0) {
                String[] fileNames =
                        request.getUrlList().stream().map(item -> item.getFileName()).toArray(String[]::new);
                for (int i = 0; i < fileNames.length; i++) {
                    String fileType = fileNames[i].substring(fileNames[i].lastIndexOf("."));
                    fileNames[i] =
                            request.getOrderNumber() + "_" + request.getContainerNo() + "_" + i + fileType;
                    request.getUrlList().get(i).setFileName(fileNames[i]);
                }
                String[] fileUrls =
                        request.getUrlList().stream().map(item -> item.getUrl()).toArray(String[]::new);
                document.setFileName(StringUtils.join(fileNames, ";"));
                document.setFileUrl(StringUtils.join(fileUrls, ";"));
            }
            if (null != request.getDocId()) {
                DocOrderDocument document1 = docOrderDocumentService.selectOrderDocumentById(request.getDocId());
                request.setFromType(document1.getFormSystem().equals("1") ? "client" : "blpt");
                request.setType("update");
                document.setUploadBy(SecurityUtils.getUsername());
                docOrderDocumentService.updateOrderDocument(document);
            } else {
                request.setFromType("blpt");
                request.setType("insert");
                document.setUploadBy(SecurityUtils.getUsername());
                document.setFormSystem("班列平台");
                if (request.getUrlList().size() > 0) {
                    document.setBoxingphotoStatus(0);
                }
                document.setContainerStatus(0);
                docOrderDocumentService.insertOrderDocument(document);
                request.setDocId(document.getId());
            }
//            if (request.getUrlList().size() > 0) {
            // 发送子系统（去程：拼箱，回程：大口岸、国外场站）信息
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(
                    "sendgoodsself_topic_exchange",
                    "sendgoodsself_coninfo_key.#",
                    request,
                    correlationData);
//            }
            return AjaxResult.success();
        }
    }

    /**
     * 删除上传箱号铅封号装箱照信息
     *
     * @param docId
     * @return
     */
    @DeleteMapping("/{docId}/{orderId}")
    @PreAuthorize("@ss.hasPermi('document:jsboxphoto:delete')")
    public AjaxResult deleteConInfo(@PathVariable("docId") Long docId, @PathVariable("orderId") String orderId) {
        //更新托书单证表状态
        DocOrder docOrder = new DocOrder();
        docOrder.setActualSupply(0);
        docOrder.setActualSupplyTime(null);
        docOrder.setOrderId(orderId);
        docOrder.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
        docOrderService.updateActualSupply(docOrder);
        DocOrderDocument document = docOrderDocumentService.selectOrderDocumentById(docId);
        // todo 发送子系统信息（去程：拼箱，回程：大口岸、国外场站）
//        BusiOrderColumn orderColumn = busiShippingorderService.selectBusiOrderColumnByOrderId(orderId);
        DocSendGoodsVO sendGoodsVO = new DocSendGoodsVO();
        sendGoodsVO.setType("delete");
        sendGoodsVO.setOrderId(document.getOrderId());
        sendGoodsVO.setOrderNumber(document.getOrderNumber());
        sendGoodsVO.setContainerNo(document.getContainerNo());
        sendGoodsVO.setDocId(docId);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("sendgoodsself_topic_exchange", "sendgoodsself_coninfo_key.#", sendGoodsVO, correlationData);
        //货物状态表数据删除

        return new AjaxResult(docOrderDocumentService.deleteOrderDocumentById(docId), "");
    }

    @ApiOperation(value = "多文件上传", notes = "多文件上传 ")
    @PostMapping("/uploadDoc")
    public AjaxResult upload(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) throws IOException {
        ProblemFileVo2[] fileUrl = FileUploadUtils.filesUpload(file, SzhblConfig.getUploadBoxingPhotoPath());
        return AjaxResult.success(fileUrl);
    }
}
