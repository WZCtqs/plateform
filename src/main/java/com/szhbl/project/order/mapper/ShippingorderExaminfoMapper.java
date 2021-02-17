package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.vo.OrderExamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单转待审核信息(订舱公告)Mapper接口
 * 
 * @author dps
 * @date 2020-04-01
 */
@Repository
public interface ShippingorderExaminfoMapper 
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
     * 删除订单转待审核信息(订舱公告)
     * 
     * @param examId 订单转待审核信息(订舱公告)ID
     * @return 结果
     */
    public int deleteShippingorderExaminfoById(String examId);

    /**
     * 批量删除订单转待审核信息(订舱公告)
     * 
     * @param examIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteShippingorderExaminfoByIds(String[] examIds);

    List<OrderExamVo> getTimes(@Param("lineType") String lineType,@Param("startDate") String startDate,@Param("endDate") String endDate);
}
