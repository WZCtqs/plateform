package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.Merchandiser;
import com.szhbl.project.order.domain.vo.OrderImportList;
import com.szhbl.project.order.domain.vo.inquiryResultZx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusiShipOrderMapper {
    public inquiryResultZx inquiryPirce(String inquiryRecordId); //查询整柜价格信息
    public String orderZgCount(String orderId); //查询托书中整柜箱量
    public Double selectZgCount(String classId); //查询班列已定的整柜数量
    public Integer selectClassZgCount(String classId); //查询班列整柜总数
    public inquiryResultZx inquiryNum(String inquiryRecordId); //查询提货、送货报价编号
    /**
     * 委托书汇总订单列表
     * @param busiShippingorder 订单
     * @return 订单集合
     */
    public List<OrderImportList> selectOrderImportList(OrderImportList busiShippingorder);
    public Double importZgCount(OrderImportList busiShippingorder); //委托书汇总导出-整柜箱量
    public OrderImportList importPxVm(OrderImportList busiShippingorder); //委托书汇总导出-拼箱体积重量
    public int updateOrderinquiry(BusiShippingorders orderUpd); //再次订舱更新托书询价信息
    public String orderSiteCode(String orderId); //查询托书最新上货站
    public String selectMerchandiser(@Param("classEastandwest") String classEastandwest, @Param("clientId") String clientId);//查询客户跟单
    public List<Merchandiser> selectMerchandiserList(String[] merchandiserId);//查询客户跟单列表
    public List<String> orderIdListByClient(String clientId); //查询该客户二个月内订单总数
    public List<String> orderIdListT(@Param("dateType") String dateType); //测试使用

    public int updateInquiry(inquiryResultZx bookingInquiry); //更新箱量
    public int updateInquiryResult(inquiryResultZx bookingInquiry);//更新价格
}
