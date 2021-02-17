package com.szhbl.project.order.mapper;

import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.order.domain.BusiOrderColumn;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.CheckOrderList;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.domain.vo.ShippingOrderList;
import com.szhbl.project.trains.domain.BusiClasses;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 *
 * @author dps
 * @date 2020-01-21
 */
@Repository
public interface BusiShippingorderMapper {
    /**
     * 查询订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<ShippingOrderList> selectBusiShippingorderList(ShippingOrderList busiShippingorder);

    /**
     * 查询订单列表
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<ShippingOrder> selectBusiShippingorderLists(ShippingOrder busiShippingorder);

    public List<BusiShippingorders> selectBusiShippingorderByClassDate(String classDate);

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

    public List<String> selectBoxNum(String orderId);

    public String selectDeptCode(String orderId);

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

    public List<ShippingOrderList> selectOrderByOrderNumberLike(String orderNumber);

    /**
     * 查询订单商品详情
     */
    public List<BookingInquiryGoodsDetails> selectGoodsInfo(String inquiryRecordId);

    /**
     * 查询订单询价信息
     */
    public BookingInquiry selectInquiryInfo(String inquiryRecordId);

    /**
     * 查询订单询价结果详情
     */
    public BookingInquiryResult selectInquiryRecordInfo(String inquiryRecordId);

    /**
     * 新增订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int insertBusiShippingorder(BusiShippingorders busiShippingorder);

    /**
     * 修改订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int updateBusiShippingorder(BusiShippingorders busiShippingorder);

    /**
     * 询价结果更新到托书表
     */
    public int updateorderInquiry(BusiShippingorders busiShippingorder);

    /**
     * 转待审核申请
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderCheckApply(BusiShippingorders busiShippingorder);

    /**
     * 更新转待审核次数
     */
    public void updateTurnCount();

    /**
     * 查询该客户三个月内转待总次数
     *
     * @param clientId 客户id
     * @return 结果
     */
    public int selectTotalExam(String clientId);

    /**
     * 查询该客户三个月内订单总数
     *
     * @param clientId 客户id
     * @return 结果
     */
    public int selectTotalOrder(String clientId);

    /**
     * 转待审核订单列表/撤舱审核订单列表
     *
     * @param checkorder 订单
     * @return 订单集合
     */
    public List<CheckOrderList> selectCheckOrderList(CheckOrderList checkorder);

    /**
     * 转待审核操作
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderCheckReply(BusiShippingorders busiShippingorder);

    /**
     * 修改舱位号
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int orderSpaceEdit(BusiShippingorders busiShippingorder);

    /**
     * 平台端取消订单
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int cancelBusiShippingorder(BusiShippingorders busiShippingorder);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderByIds(String[] orderIds);

    /**
     * 查询订单修改记录
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingorders selectHistoryEditRecord(String orderId);

    /**
     * 转待审核时获取班列信息
     */
    public BusiShippingorders selectOrderByInquiryRecord(String inquiryRecordId); //根据询价结果查询询价信息

    public BusiShippingorders selectOrderByInquiryRecordBackup(String inquiryRecordId); //根据询价结果查询草稿表中最新询价信息

    public List<BusiClasses> selectZXList(BusiShippingorders busiShippingorder); //整柜

    public List<BusiClasses> selectPXList(BusiShippingorders busiShippingorder); //拼箱

    /**
     * 回程场站列表
     */
    public List<BusiStation> selectStationList(String isconsolidation, String orderUploadcode);

    /**
     * 查询客户跟单邮箱
     *
     * @param merchandiserId 跟单id
     * @return 结果
     */
    public List<String> selectOrderMerEmail(String[] merchandiserId);

    /**
     * 查询客户推荐人邮箱
     *
     * @param tjrId 推荐人id
     * @return 结果
     */
    public String selectOrderTjrEmail(String tjrId);

    /**
     * 通过询价结果id查询询价id
     *
     * @param inquiryRecordId 询价结果id
     * @return 结果
     */
    public Long selectInquiryId(String inquiryRecordId);

    /**
     * 根据询价id删除询价商品详情
     *
     * @param inquiryId 询价id
     * @return 结果
     */
    public int deleteGoodsByInquiryId(Long inquiryId);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderById(String orderId);

    /**
     * 根据托书id更新托书状态
     *
     * @param orderId
     * @param IsExamline
     * @return
     */
    public int updateIsExamlineByOrderId(@Param("orderId") String orderId, @Param("IsExamline") String IsExamline);

    /**
     * 转待审核时修改托书状态为客户待确认，前提不是草稿状态（集输公路任意已驳回）
     *
     * @param orderId
     * @param IsExamline
     * @return
     */
    public int updateIsExamlineButNotBH(@Param("orderId") String orderId, @Param("IsExamline") String IsExamline);

    /**
     * 获取托书当前询价结果id
     *
     * @param orderId
     * @return
     */
    public Long selectInquiryResultIdByOrderId(String orderId);

    public int updateInquiryResultId(@Param("orderId") String orderId, @Param("inquiryResultId") Long inquiryResultId);

    //查询询价结果是否已经绑定订单
    public Integer selectOrderNumByInquiryResultId(Long inquiryResultId);

    public BusiShippingorders selectForTocheckNoticeByOrderId(String orderId);

    BusiClasses selectClassByOrderId(@Param("orderId") String orderId, @Param("language") String language);

    Map selectIsExamlineByOrderId(String orderId);


    /**
     * 拼箱订舱查询订单列表(shahy)
     *
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<ShippingOrder> selectPxList(ShippingOrder busiShippingorder);

    BusiOrderColumn selectBusiOrderColumnByOrderId(String orderId);
}
