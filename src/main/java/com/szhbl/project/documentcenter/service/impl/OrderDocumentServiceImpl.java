package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.email.IMailService;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.client.VO.ProblemFileVo2;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.*;
import com.szhbl.project.documentcenter.mapper.*;
import com.szhbl.project.documentcenter.service.IOrderDocumentService;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.mapper.BookingInquiryGoodsDetailsMapper;
import com.szhbl.project.track.domain.TrackTwoLevel;
import com.szhbl.project.track.mapper.TrackTwoLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 托书单证Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-03
 */
@Service
public class OrderDocumentServiceImpl implements IOrderDocumentService {
    @Resource
    private OrderDocumentMapper orderDocumentMapper;

    @Resource
    private DocPxInBoxCheckMapper pxInBoxCheckMapper;

    @Resource
    private DocPxGoodsInOutMapper pxGoodsInOutMapper;

    @Resource
    private DocPxYardLoadedInMapper yardLoadedInMapper;

    @Resource
    private DocPxBoxingListMapper pxBoxingListMapper;

    @Resource
    private DocGWCZArrivalGoodsMapper gwczArrivalGoodsMapper;

    @Autowired
    private DocOrderMapper docOrderMapper;

    @Autowired
    private BookingInquiryGoodsDetailsMapper inquiryGoodsDetailsMapper;

    @Autowired
    private IMailService mailService;

    @Autowired
    private TrackTwoLevelMapper trackTwoLevelMapper;

    @Override
    public List<OrderDocuments> orderDocumentOrderList(OrderDocuments orderDocuments) {
        String deptCode = orderDocuments.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (orderDocuments.getDeptCode().contains("YWB")) {
                if (orderDocuments.getDeptCode().length() > 15) {
                    orderDocuments.setReadType("0");
                } else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    orderDocuments.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    orderDocuments.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    orderDocuments.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    orderDocuments.setReadType("5");
                } else {
                    orderDocuments.setReadType("1");
                }
            }
        }
        List<OrderDocuments> list = new ArrayList<>();
        // 如果有箱号条件，先查询箱号下的装箱照托书
        if (StringUtils.isNotEmpty(orderDocuments.getContainerNo())) {
            List<String> orderList = orderDocumentMapper.getOrderByContainerNo(orderDocuments.getContainerNo().trim());
            if (orderList.size() > 0) {
                String orderIds = "";
                for (int i = 0; i < orderList.size(); i++) {
                    orderIds = orderIds + (i == 0 ? "'" + orderList.get(i) + "'" : ",'" + orderList.get(i) + "'");
                }
                orderDocuments.setOrderIds(orderIds);
            } else {
                return list;
            }
        }
        list = orderDocumentMapper.orderDocumentOrderList(orderDocuments);
        for (OrderDocuments d : list) {
//            LadingBill ladingBill = ladingBillMapper.selectLadingBillByOrderId(d.getOrderId());
//            d.setLadingBillDraft(ladingBill);
            // 入仓核实单-回程到货数据
            if (d.getClassEastAndWest().equals("0")) {
                PxInBoxCheck pxInBoxCheck = pxInBoxCheckMapper.selectDocInBoxCheckByOrderId(d.getOrderId());
                d.setPxInBoxCheck(pxInBoxCheck);
            } else {
                GWCZArrivalGoods gwczArrivalGoods = gwczArrivalGoodsMapper.selecGwczArrivalGoodsByOrderId(d.getOrderId());
                d.setGwczArrivalGoods(gwczArrivalGoods);
            }
            // 拼箱出入库表
            PxGoodsInOut pxGoodsInOut = pxGoodsInOutMapper.selectDocPxGoodsInOutByOrderId(d.getOrderId());
            d.setPxGoodsInOut(pxGoodsInOut);
            // 重箱进站表
            List<PxYardLoadedIn> pxYardLoadedIn = yardLoadedInMapper.selectDocYardLoadedInByOrderId(d.getOrderId());
            d.setPxYardLoadedIn(pxYardLoadedIn);
            // 装柜清单件重尺
            PxBoxingList pxBoxingList = pxBoxingListMapper.selectDocBoxingListByOrderId(d.getOrderId());
            d.setPxBoxingList(pxBoxingList);
            //回程进站时间
//            DocOrderInstation instation = instationMapper.selectDocOrderInstationByOrderId(d.getOrderId());
//            d.setInstation(instation);
            //拆箱代理
//            DocOrderUnpackingagent unpackingagent = unpackingagentMapper.selectOrderUnpackingagentByOrderId(d.getOrderId());
//            d.setUnpackingagent(unpackingagent);
        }
        return list;
    }

    @Override
    public List<DocOrderDocument> getByOrderIdFileKey(String orderId, String fileTypeKey) {
        DocOrderDocument document = new DocOrderDocument();
        document.setOrderId(orderId);
        document.setFileTypeKey(fileTypeKey);
        return orderDocumentMapper.selectOrderDocumentList(document);
    }

    @Override
    public List<BeyondDoc> selectBeyondByOrderId(String orderId) {
        return orderDocumentMapper.selectBeyondByOrderId(orderId);
    }

    @Override
    public DocOrderPickGoodTimeVO getPickUpGoodsTime(String orderId) {
        DocOrderPickGoodTimeVO document = new DocOrderPickGoodTimeVO();
        DocOrderDocument pickGoods = orderDocumentMapper.getByOrderIdAndFileTypeKey(orderId, DocumentsType.PICK_GOODS_FILE);
        if (pickGoods == null) {
            OrderDataForDoc busiShippingorder = orderDocumentMapper.selectOrderData(orderId);
            document.setPickGoodsTimec(DateUtils.parseStr(busiShippingorder.getShipOrderUnloadtime()));
            document.setPickGoodsContacts(busiShippingorder.getShipOrderUnloadcontacts());
            document.setPickGoodsPhone(busiShippingorder.getShipOrderUnloadway());
            document.setPickGoodsAddress(busiShippingorder.getShipOrderUnloadaddress() + busiShippingorder.getDetailedAddress());
            document.setPickGoodsTimecConfirm(0);
            document.setClassEastAndWest(busiShippingorder.getClassEastAndWest());
            document.setIsConsolidation(busiShippingorder.getIsConsolidation());
        } else {
            document.setPickGoodsTimec(pickGoods.getPickGoodsTimec());
            document.setPickGoodsContacts(pickGoods.getPickGoodsContacts());
            document.setPickGoodsPhone(pickGoods.getPickGoodsPhone());
            document.setPickGoodsAddress(pickGoods.getPickGoodsAddress());
            document.setPickGoodsTimecConfirm(pickGoods.getPickGoodsTimecConfirm());
            document.setClassEastAndWest(pickGoods.getClassEastAndWest());
            document.setIsConsolidation(pickGoods.getIsConsolidation());
        }
        if ("0".equals(document.getClassEastAndWest())) {
            List<TrackAbroadTimeVO> trackAbroadTimeVOs = trackTwoLevelMapper.selectAbroadTimeByOrderId(orderId);
            for (TrackAbroadTimeVO track : trackAbroadTimeVOs) {
                // 去程拼箱：预计提货时间 24 提货时间 26
                // 去程整柜：提箱资料时间 24 提箱时间 26
                if (track.getSort() == 24) {
                    document.setAbroadPlanPickTime(track.getTime());
                } else {
                    document.setAbroadPickTime(track.getTime());
                }
                if (StringUtils.isNotEmpty(track.getRemark()) && track.getRemark().contains("对接人")) {
                    try {
                        String[] arr = track.getRemark().split("对接人:");
                        document.setOperator(arr[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return document;
    }

    @Override
    public DocOrderDocument pickGoodsTime(String orderId) {
        DocOrderDocument pickGoods = orderDocumentMapper.getByOrderIdAndFileTypeKey(orderId, DocumentsType.PICK_GOODS_FILE);
        if (pickGoods == null) {
            DocOrderDocument document = new DocOrderDocument();
            OrderDataForDoc busiShippingorder = orderDocumentMapper.selectOrderData(orderId);
            document.setPickGoodsTimec(DateUtils.parseStr(busiShippingorder.getShipOrderUnloadtime()));
            document.setPickGoodsContacts(busiShippingorder.getShipOrderUnloadcontacts());
            document.setPickGoodsPhone(busiShippingorder.getShipOrderUnloadway());
            document.setPickGoodsAddress(busiShippingorder.getShipOrderUnloadaddress() + busiShippingorder.getDetailedAddress());
            document.setPickGoodsTimecConfirm(0);
        }
        return pickGoods;
    }

    /**
     * 查询托书单证
     *
     * @param id 托书单证ID
     * @return 托书单证
     */
    @Override
    public DocOrderDocument selectOrderDocumentById(Long id) {
        return orderDocumentMapper.selectOrderDocumentById(id);
    }

    /**
     * 查询托书单证列表
     *
     * @param docOrderDocument 托书单证
     * @return 托书单证
     */
    @Override
    public List<DocOrderDocument> selectOrderDocumentList(DocOrderDocument docOrderDocument) {
        OrderDataForDoc order = orderDocumentMapper.selectOrderData(docOrderDocument.getOrderId());
        // 查询单件重量
        List<BookingInquiryGoodsDetails> goodsDetails = inquiryGoodsDetailsMapper.selectGoodsInfoByOrderId(docOrderDocument.getOrderId());
        List<String> standards = new ArrayList<>();
        for (BookingInquiryGoodsDetails detail : goodsDetails) {
            standards.add(detail.getGoodsLength() + "*" + detail.getGoodsWidth() + "*" + detail.getGoodsHeight() + "*" + detail.getGoodsAmount());
        }
        String addreddA = "";
        if (StringUtils.isNotEmpty(order.getShipOrderUnloadaddress())) {
            addreddA = order.getShipOrderUnloadaddress();
        }
        String addressB = "";
        if (StringUtils.isNotEmpty(order.getDetailedAddress())) {
            addressB = order.getDetailedAddress();
        }
        List<DocOrderDocument> list = orderDocumentMapper.selectOrderDocumentList(docOrderDocument);
        List<DocOrderDocument> resultList = new ArrayList<>();
        if (list.size() > 0) {
            for (DocOrderDocument document : list) {
                // 处理自送货url拼接的情况
                if (document.getFileTypeKey().equals(DocumentsType.BOXING_PHOTO_FILE)) {
                    if (StringUtils.isNotEmpty(document.getFileUrl()) && document.getFileUrl().contains(";")) {
                        String[] urls = document.getFileUrl().split(";");
                        String[] names = document.getFileName().split(";");
                        for (int i = 0; i < urls.length; i++) {
//                            if (i == 0) {
//                                document.setFileUrl(urls[0]);
//                                document.setFileName(names[0]);
//                            } else {
                            DocOrderDocument self = new DocOrderDocument();
                            self.setFileName(names[i]);
                            self.setFileUrl(urls[i]);
                            self.setFileTypeKey(document.getFileTypeKey());
                            self.setOrderId(document.getOrderId());
                            self.setOrderNumber(document.getOrderNumber());
                            self.setFileTypeText(document.getFileTypeText());
                            self.setUploadTime(document.getUploadTime());
                            self.setCreateTime(document.getCreateTime());
                            resultList.add(self);
//                            }
                        }
                    } else {
                        resultList.add(document);
                    }
                } else {
                    resultList.add(document);
                }
            }
            for (DocOrderDocument document : resultList) {
                if (document.getFileTypeKey().equals(DocumentsType.PICK_GOODS_FILE)) {
                    document.setPickGoodsTimec(DateUtils.parseStr(order.getShipOrderUnloadtime()));
                    document.setPickGoodsContacts(order.getShipOrderUnloadcontacts());
                    document.setPickGoodsPhone(order.getShipOrderUnloadway());
                    document.setPickGoodsAddress(addreddA + addressB);
                    document.setPickGoodsTimecConfirm(document.getPickGoodsTimecConfirm() == null ? 0 : 1);
                    document.setGoodsName(order.getGoodsName());
                    document.setGoodsEnName(order.getGoodsEnName());
                    document.setGoodsCbm(order.getGoodsCbm());
                    document.setGoodsKgs(order.getGoodsKgs());
                    document.setGoodsStandard(standards);
                    DocOrderDocument param = new DocOrderDocument();
                    param.setOrderId(docOrderDocument.getOrderId());
                    param.setOrderNumber(docOrderDocument.getOrderNumber());
                    param.setFileTypeKey("pick_goods_time_filec");
                    List<DocOrderDocument> pickfiles = orderDocumentMapper.selectOrderDocumentList(param);
                    List<ProblemFileVo2> urls = new ArrayList<>();
                    for (DocOrderDocument docsFile : pickfiles) {
                        ProblemFileVo2 url = new ProblemFileVo2();
                        url.setFileName(docsFile.getFileName());
                        url.setUrl(docsFile.getFileUrl());
                        urls.add(url);
                    }
                    document.setUrlList(urls);
                    String nameZh = "0".equals(order.getClassEastAndWest()) ? "已派车" : "已提货";
                    TrackTwoLevel trackTwoLevel = trackTwoLevelMapper.selectTrackTwoLevelByOrderIdAndName(docOrderDocument.getOrderId(), nameZh);
                    if (trackTwoLevel != null) {
                        if (trackTwoLevel.getRemark().contains("null") || trackTwoLevel.getRemark().contains("暂无")) {
                        } else {
                            document.setDriverInfo(trackTwoLevel.getRemark());
                        }
                    }
                }
            }
        }else {
            DocOrderDocument orderDocument = new DocOrderDocument();
            orderDocument.setOrderId(docOrderDocument.getOrderId());
            orderDocument.setOrderNumber(docOrderDocument.getOrderNumber());
            orderDocument.setFileTypeKey(DocumentsType.PICK_GOODS_FILE);
            orderDocument.setPickGoodsTimec(DateUtils.parseStr(order.getShipOrderUnloadtime()));
            orderDocument.setPickGoodsContacts(order.getShipOrderUnloadcontacts());
            orderDocument.setPickGoodsPhone(order.getShipOrderUnloadway());
            orderDocument.setPickGoodsAddress(addreddA + addressB);
            orderDocument.setPickGoodsTimecConfirm(0);
            orderDocument.setGoodsName(order.getGoodsName());
            orderDocument.setGoodsEnName(order.getGoodsEnName());
            orderDocument.setGoodsCbm(order.getGoodsCbm());
            orderDocument.setGoodsKgs(order.getGoodsKgs());
            orderDocument.setGoodsStandard(standards);
            resultList.add(orderDocument);
        }
        return resultList;
    }

    /**
     * 新增托书单证
     *
     * @param docOrderDocument 托书单证
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrderDocument(DocOrderDocument docOrderDocument) {
        DocOrder docOrder = new DocOrder();
        docOrder.setOrderId(docOrderDocument.getOrderId());
        docOrder.setFileTypeKey(docOrderDocument.getFileTypeKey());
        docOrder.setActualSupply(1);
        docOrder.setActualSupplyTime(docOrderDocument.getUploadTime());
        docOrderMapper.updateActualSupply(docOrder);
        return orderDocumentMapper.insertOrderDocument(docOrderDocument);
    }

    @Override
    public int insertDocumentMatch(List<DocOrderDocument> documentList) {
        // 更新托书单证上传状态
        List<String> orderIds = new ArrayList<>();
        for (DocOrderDocument d : documentList) {
            DocOrder docOrder = new DocOrder();
            docOrder.setOrderId(d.getOrderId());
            docOrder.setFileTypeKey(d.getFileTypeKey());
            docOrder.setActualSupply(1);
            docOrder.setActualSupplyTime(d.getUploadTime());
            docOrderMapper.updateActualSupply(docOrder);
            orderIds.add(d.getOrderId());
        }
        // 批量删除原托书单证数据
        String[] orderIdArray=orderIds.toArray(new String[orderIds.size()]);
        orderDocumentMapper.deleteByOrderIds(orderIdArray);
        // 批量新增
        return orderDocumentMapper.insertDocumentMatch(documentList);
    }

    /**
     * 修改托书单证
     *
     * @param docOrderDocument 托书单证
     * @return 结果
     */
    @Override
    public int updateOrderDocument(DocOrderDocument docOrderDocument) {
        docOrderDocument.setUploadTime(new Date());
        return orderDocumentMapper.updateOrderDocument(docOrderDocument);
    }

    @Override
    public int updateOrderDocumentByOrderId(DocOrderDocument docOrderDocument) {
        docOrderDocument.setUploadTime(new Date());
        return orderDocumentMapper.updateOrderDocumentByOrderId(docOrderDocument);
    }

    /**
     * 批量删除托书单证
     *
     * @param ids 需要删除的托书单证ID
     * @return 结果
     */
    @Override
    public int deleteOrderDocumentByIds(Long[] ids) {
        return orderDocumentMapper.deleteOrderDocumentByIds(ids);
    }

    /**
     * 删除托书单证信息
     *
     * @param id 托书单证ID
     * @return 结果
     */
    @Override
    public int deleteOrderDocumentById(Long id) {
        return orderDocumentMapper.deleteOrderDocumentById(id);
    }

    @Override
    public int getCountByOrderAndTypeKey(DocOrderDocument document) {
        return orderDocumentMapper.getCountByOrderAndTypeKey(document);
    }

    @Override
    public String getOrderClientEmail(String orderId) {
        return orderDocumentMapper.getOrderClientEmail(orderId);
    }

    @Override
    public String getClientId(String orderId) {
        return orderDocumentMapper.getClientId(orderId);
    }

    @Override
    public int deleteByConAndOrderIdAndKey(String containerNo, String orderId, String fileTypeKey) {
        DocOrder docOrder = new DocOrder();
        docOrder.setOrderId(orderId);
        docOrder.setFileTypeKey(fileTypeKey);
        docOrder.setActualSupply(0);
        docOrderMapper.updateActualSupply(docOrder);
        return orderDocumentMapper.deleteByConAndOrderIdAndKey(containerNo, orderId, fileTypeKey);
    }

    @Override
    public int deleteByFileNameAndOrderIdAndKey(String fileName, String orderId, String fileTypeKey) {
        DocOrder docOrder = new DocOrder();
        docOrder.setOrderId(orderId);
        docOrder.setFileTypeKey(fileTypeKey);
        docOrder.setActualSupply(0);
        docOrderMapper.updateActualSupply(docOrder);
        return orderDocumentMapper.deleteByFileNameAndOrderIdAndKey(fileName, orderId, fileTypeKey);
    }

    @Override
    public int deleteByOrderIdAndFileTypeKey(String orderId, String fileTypeKey) {
        DocOrder docOrder = new DocOrder();
        docOrder.setOrderId(orderId);
        docOrder.setFileTypeKey(fileTypeKey);
        docOrder.setActualSupply(0);
        docOrderMapper.updateActualSupply(docOrder);
        return orderDocumentMapper.deleteByOrderIdAndFileTypeKey(orderId, fileTypeKey);
    }

    @Override
    public int deleteByOrderIdAndUrl(String orderId, String fileTypeKey,String url) {
        DocOrder docOrder = new DocOrder();
        docOrder.setOrderId(orderId);
        docOrder.setFileTypeKey(fileTypeKey);
        docOrder.setActualSupply(0);
        docOrderMapper.updateActualSupply(docOrder);
        return orderDocumentMapper.deleteByOrderIdAndUrl(orderId, url);
    }

    @Override
    public OrderDocUrl getOrderIdByNumbers(String orderNumber) {
        return orderDocumentMapper.getOrderIdByNumbers(orderNumber);
    }

    @Override
    public AjaxResult sendClientEmailNotice(String orderNumer, String orderId) {
        String email = orderDocumentMapper.getClientEmailByOrderId(orderId);
        if (StringUtils.isNotEmpty(email)) {
            String[] emails = email.split(";");
            int fail = 0;
            StringBuilder failmsg = new StringBuilder();
            for (int i = 0; i < emails.length; i++) {
                if (StringUtils.isNotEmpty(emails[i])) {
                    String result = mailService.sendSimpleMail(emails[i],
                            orderNumer + "提货时间确认提醒！",
                            "尊敬的客户，您好！ 托书编号为：" + orderNumer + "的提货时间还未确认，请及时登录系统进行确认！");
                    if (!"发送成功".equals(result)) {
                        fail++;
                        failmsg.append(emails[i] + " 失败原因：" + result);
                    }
                }
            }
            if (fail == 0) {
                return AjaxResult.success();
            } else if (fail == emails.length) {
                return AjaxResult.error(500, "发送失败。" + failmsg);
            } else if (fail < emails.length) {
                return AjaxResult.error(500, fail + "个发送失败。" + failmsg);
            } else {
                return AjaxResult.error();
            }
        } else {
            return new AjaxResult(500, "订舱方邮件不存在！");
        }
    }

    @Override
    public int updateBoxPhotoStatus(DocOrderDocument docOrderDocument) {
        return orderDocumentMapper.updateBoxPhotoStatus(docOrderDocument);
    }

    @Override
    public List<DocOrderDocument> selectSendGoodsSelf(DocOrderDocument docOrderDocument) {
        return orderDocumentMapper.selectSendGoodsSelf(docOrderDocument);
    }

    @Override
    public int deleteByDocument(DocOrderDocument docOrderDocument) {
        return orderDocumentMapper.deleteByDocument(docOrderDocument);
    }
}
