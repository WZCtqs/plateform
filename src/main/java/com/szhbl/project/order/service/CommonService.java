package com.szhbl.project.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.track.OrderGoodsTrackDel;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.Date;
import java.util.Map;

public interface CommonService {

    //托书信息对比（涉及询价）
    public Map orderCompare(BusiShippingorders orderbackup, BusiShippingorderGoods goodsbackup, BusiShippingorders orderinfo, BusiShippingorderGoods goodsinfo);

    //从托书中筛选托书商品表信息
    public BusiShippingorderGoods orderGoodsInfo(BusiShippingorders busiShippingorder);

    //重新询价的信息存入托书
    public BusiShippingorders orderInfoByInquiry(BookingInquiryResult inquiryInfo,String classEastandwest);

    //获取排舱信息
    public Boolean isSpace(String classId, BusiShippingorders busiShippingorder);

    /**
     * 获取订单特殊备注（不用）
     */
    public Map selectSpecialRemark(String goodsName,String goodsReport,String classId,String orderUnloadsite);

    /**
     * 获取订单品名特殊备注
     */
    public String goodsSpecialRemark(BusiShippingorders busiShippingorder);

    /**
     * 获取订单hs特殊备注
     */
    public String hsSpecialRemark(BusiShippingorders busiShippingorder);

    //集疏发送邮件
    public void sendEmailJs(String[] sendEmails, ShippingOrder orderInfoRabbmq,String binningwayOld);

    //再次订舱驳回时给集疏发送邮件
    public void sendEmailJsBh(ShippingOrder orderInfo);

    //删除转待审核暂存表
    public void deleteTem(String orderId);

    //推送托书消息队列
    public void orderInfoMQ(ShippingOrder orderInfoRabbmq,String messageType) throws JsonProcessingException;

    //获取货物详细信息
    public String goodsInfoDetail(String inquiryRecordId);

    //推送排舱货物状态消息队列
    public void trackInfoMQ(String orderId,String boxNum) throws JsonProcessingException;

    //判断是否是特种箱
    public boolean isSpecialBox(String containerType);

    //获取箱型名称
    public String containerTypeName(String containerType);

    //箱号校验
    public boolean checkDigit(String containerNumber);

    //计算整柜价格
    public String zgPrice(String price,Integer containerNum,String boxAmount);

    //计算提还箱费价格
    public String zgBoxFee(String price,Integer containerNum,String boxAmount);

    //判断是否增加转待审核次数
    public String isZdCount(Date classDate,String classEastandwest);

    //推送排舱信息删除消息队列
    public void orderTrackDeleteMQ(OrderGoodsTrackDel orderTrackDel,String type) throws JsonProcessingException;

    public byte[] toByteArray (Object obj);

    //从托书中筛选询价表信息
    public BookingInquiry inquiryInfo(BusiShippingorders busiShippingorder);
}
