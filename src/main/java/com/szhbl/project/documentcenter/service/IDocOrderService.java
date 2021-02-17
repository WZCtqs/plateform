package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrder;

import java.util.List;

/**
 * 订单—单证（订单所需单证）Service接口
 *
 * @author hp
 * @date 2020-04-13
 */
public interface IDocOrderService
{
    /**
     * 查询订单—单证（订单所需单证）
     *
     * @param orderId 订单—单证（订单所需单证）ID
     * @return 订单—单证（订单所需单证）
     */
    public List<DocOrder> selectDocOrderByOrderId(String orderId);

    public DocOrder selectByOrderidTypeKey(String orderId, String fileTypeKey);

    public int updateByorderidTypeKey(DocOrder docOrder);

    public int updateActualSupply(DocOrder docOrder);

    /**
     * 查询订单—单证（订单所需单证）列表
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 订单—单证（订单所需单证）集合
     */
    public List<DocOrder> selectDocOrderList(DocOrder docOrder);

    /**
     * 新增订单—单证（订单所需单证）
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 结果
     */
    public int insertDocOrder(DocOrder docOrder);

    /**
     * 根据托书id 新增所需单证信息及消息提醒延迟发送
     * @param orderId
     * @return
     */
    public int insertDocOrderMatch(String orderId) throws Exception;

    /**
     * 删除订单—单证（订单所需单证）信息
     *
     * @param id 订单—单证（订单所需单证）ID
     * @return 结果
     */
    public int deleteDocOrderByOrderId(String orderId);
}
