package com.szhbl.project.order.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.GwczBookingVo;

/**
 * 订单Service接口
 *
 * @author dps
 * @date 2020-01-21
 */
public interface IBusiShipOrderService
{

    /**
     * 国外场站拼箱订舱信息处理
     *
     * @param gwczBookingVo
     * @return
     */
    boolean gwczAdd(GwczBookingVo gwczBookingVo);

    /**
     * 查询托书箱量
     */
    public String orderZgCount(String orderId);

    /**
     * 托书修改整柜箱量
     */
    public int zgCountChange(BusiShippingorders busiShippingorder);

    /**
     * 已审核托书再次订舱-新增托书记录
     */
    public int insertBusiShippingorder(BusiShippingorders busiShippingorder);

    /**
     * 再次订舱最后一步，更新托书中最新询价信息
     */
    public int insertOrderInquiry(String orderId);

    /**
     * 未审核托书再次订舱
     *
     * @param busiShippingorder 订单
     * @return 结果
     */
    public int insertShiporder(BusiShippingorders busiShippingorder) throws JsonProcessingException;
}
