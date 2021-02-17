package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.OrderExamVo;

import java.util.List;

/**
 * 订单转待审核信息(订舱公告)Service接口
 * 
 * @author dps
 * @date 2020-04-01
 */
public interface IShippingorderExaminfoService 
{
    /**
     * 查询订单转待审核信息(订舱公告)
     * 
     * @param examId 订单转待审核信息(订舱公告)ID
     * @return 订单转待审核信息(订舱公告)
     */
    public ShippingorderExaminfo selectShippingorderExaminfoById(String examId);

    /**
     * 查询订单转待审核信息(订舱公告)
     *
     * @param orderId 订单转待审核信息(订舱公告)ID
     * @return 订单转待审核信息(订舱公告)
     */
    public ShippingorderExaminfo selectShippingorderExaminfoByOrderId(String orderId);

    /**
     * 查询订单转待审核信息(订舱公告)列表
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 订单转待审核信息(订舱公告)集合
     */
    public List<ShippingorderExaminfo> selectShippingorderExaminfoList(ShippingorderExaminfo shippingorderExaminfo);

    /**
     * 每次转待审核修改记录
     */
    public String oneEditRecord(String examId);

    /**
     * 托书转待原因列表
     *
     * @param orderId 订单转待审核信息(订舱公告)
     * @return 订单转待审核信息(订舱公告)集合
     */
    public List<ShippingorderExaminfo> selectOrderExaminfoList(String orderId);

    /**
     * 添加订舱组备注
     *
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    public int addRemark(ShippingorderExaminfo shippingorderExaminfo);

    /**
     * 新增订单转待审核信息(订舱公告)
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    public int insertShippingorderExaminfo(ShippingorderExaminfo shippingorderExaminfo);

    /**
     * 修改订单转待审核信息(订舱公告)
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    public int updateShippingorderExaminfo(ShippingorderExaminfo shippingorderExaminfo);

    /**
     * 批量删除订单转待审核信息(订舱公告)
     * 
     * @param examIds 需要删除的订单转待审核信息(订舱公告)ID
     * @return 结果
     */
    public int deleteShippingorderExaminfoByIds(String[] examIds);

    /**
     * 删除订单转待审核信息(订舱公告)信息
     * 
     * @param examId 订单转待审核信息(订舱公告)ID
     * @return 结果
     */
    public int deleteShippingorderExaminfoById(String examId);

    /**
     * 查询时间段内托书转待审核次数
     *
     * @param lineType 线路
     * @param startDate 开始日期
     * @param endDate 截止日期
     * @return 结果
     */
    List<OrderExamVo> getTimes(String lineType,String startDate, String endDate);
}
