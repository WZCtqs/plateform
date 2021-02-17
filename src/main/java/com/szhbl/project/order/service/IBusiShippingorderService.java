package com.szhbl.project.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.order.domain.BusiOrderColumn;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.CheckOrderList;
import com.szhbl.project.order.domain.vo.OrderImportList;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.domain.vo.ShippingOrderList;
import com.szhbl.project.trains.domain.BusiClasses;

import java.util.List;
import java.util.Map;

/**
 * 订单Service接口
 * 
 * @author dps
 * @date 2020-01-21
 */
public interface IBusiShippingorderService 
{
    /**
     * 查询订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<ShippingOrderList> selectBusiShippingorderList(ShippingOrderList busiShippingorder);

    /**
     * 委托书汇总订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<OrderImportList> selectOrderImportList(OrderImportList busiShippingorder);

    /**
     * 箱量统计
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<Map> selectAmount(ShippingOrderList busiShippingorder);
    public ShippingOrderList selectAmountVW(ShippingOrderList busiShippingorder);

    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    public ShippingOrder selectBusiShippingorderById(String orderId);
    public ShippingOrder selectBusiShippingorderTemById(String orderId);

    /**
     * 查询原订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingorders selectBusiShippingorderByIdOld(String orderId);

    /**
     * 查询托书编号是否重复
     *
     * @param orderNumber 订单编号
     * @return 订单
     */
    public BusiShippingorders selectBusiShippingorderByOrderNumber(String orderNumber);

    /**
     * 查询订单商品详情
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    public List<BookingInquiryGoodsDetails> selectGoodsInfo(String inquiryRecordId);

    /**
     * 查询订单询价详情
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    public BookingInquiry selectInquiryInfo(String inquiryRecordId);

    /**
     * 查询订单询价结果详情
     *
     * @param inquiryRecordId 询价ID
     * @return 订单
     */
    public BookingInquiryResult selectInquiryRecordInfo(String inquiryRecordId);

    /**
     * 修改订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int updateBusiShippingorder(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    /**
     * 转待审核申请平台端
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderCheckApply(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    /**
     * 转待审核申请(涉及询价)
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderCheckApplyXj(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    /**
     * 更新转待审核次数
     */
    public void updateTurnCount();

    /**
     * 转待审核判断提货时间
     */
    public int picktimeCheck(BusiShippingorders busiShippingorder);

    /**
     * 提货时间发送集疏/公路审核
     */
    public void pickTimeCheckApply(String orderId) throws JsonProcessingException;

    /**
     * 更新托书暂存信息
     */
    public int updateOrderTem(String orderId,String dcGaidanState,String orderNumberNew,String stationNew) throws JsonProcessingException;

    /**
     * 根据询价结果更新暂存的托书信息
     */
    public int updateOrderByInquiry(String inquiryRecordId,String orderId,String classEastandwest);

    /**
     * 更新公告表(驳回)
     */
    public int updateOrderExam(String orderId,String refuseInfo);

    /**
     * 更新公告表(箱管驳回)
     */
    public int updateOrderExamXg(String orderId,String refuseInfo);

    /**
     * 转待审核订单列表/撤舱审核订单列表
     *
     * @param checkorder 订单
     * @return 订单集合
     */
    public List<CheckOrderList> selectCheckOrderList(CheckOrderList checkorder);

    /**
     * 转待审核操作(涉及询价)
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderCheckReplyXj(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    /**
     * 修改舱位号
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderSpaceEdit(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    /**
     * 平台端取消订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int cancelBusiShippingorder(BusiShippingorders busiShippingorder) throws JsonProcessingException;

    //客户端取消订单申请(撤舱)
    public int orderCancelApply(BusiShippingorders busiShippingorder);

    //客户端撤舱申请操作
    public int cancelCheckReply(BusiShippingorders busiShippingorder);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderByIds(String[] orderIds) throws JsonProcessingException;

    /**
     * 查询订单修改记录
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingorders selectHistoryEditRecord(String orderId);

    /**
     * 回程场站列表
     */
    public List<BusiStation> selectStationList(String isconsolidation,String orderUploadcode);

    /**
     * 查询客户跟单邮箱
     */
    public List<String> selectOrderMerEmail(String[] merchandiserId);

    /**
     * 查询客户推荐人邮箱
     */
    public String selectOrderTjrEmail(String tjrId);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderById(String orderId);


    public int updateIsExamlineByOrderId(String orderId, String IsExamline);

    /**
     * 转待审核时修改托书状态为客户待确认，前提不是草稿状态（集输公路任意已驳回）
     *
     * @param orderId
     * @param IsExamline
     * @return
     */
    public int updateIsExamlineButNotBH(String orderId, String IsExamline);


    BusiShippingorders selectForTocheckNoticeByOrderId(String orderId);

    public BusiClasses selectClassByOrderId(String OrderId, String language);

    Map selectIsExamlineByOrderId(String orderId);

    BusiOrderColumn selectBusiOrderColumnByOrderId(String orderId);
}
