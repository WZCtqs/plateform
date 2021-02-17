package com.szhbl.project.order.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.trains.domain.BusiClasses;

import java.util.List;

/**
 * @Description : 客户转待审核申请服务
 * @Author : wangzhichao
 * @Date: 2020-07-02 16:08
 */
public interface IBusiShippingOrderToCheckService {

    public AjaxResult busiShippingOrderToCheck(BookingInquiryBackup bookingInquiryBackup);

    AjaxResult bhToCheck(String orderId, String inquiryResultId, Long inquiryId);

    AjaxResult inquiryAndSendCoData(BusiShippingorders busiShippingorders);

    /**
     * 托书修改时获取班列信息
     */
    public BusiShippingorders selectOrderByInquiryRecord(String inquiryRecordId); //根据询价结果查询询价信息

    public BusiShippingorders selectOrderByInquiryRecordBackup(String inquiryRecordId); //根据询价结果查询草稿表中最新询价信息

    public List<BusiClasses> selectZXList(BusiShippingorders busiShippingorder); //整柜

    public List<BusiClasses> selectPXList(BusiShippingorders busiShippingorder); //拼箱

    AjaxResult confirmToCheck(String orderId, String expire);
}
