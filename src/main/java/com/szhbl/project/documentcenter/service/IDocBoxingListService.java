package com.szhbl.project.documentcenter.service;


import com.szhbl.project.documentcenter.domain.vo.PxBoxingList;

/**
 * 订单—单证（订单所需单证）Service接口
 *
 * @author hp
 * @date 2020-04-13
 */
public interface IDocBoxingListService
{
    /**
     * 查询入仓核实单信息
     * @param orderId
     * @return
     */
    public PxBoxingList selectDocBoxingListByOrderId(String orderId);

    /**
     * 新增入仓核实单信息
     *
     * @return 结果
     */
    public int insertDocBoxingList(PxBoxingList pxBoxingList);

    /**
     * 删除入仓核实单信息
     *
     * @return 结果
     */
    public int deleteDocBoxingListByOrderId(String orderId);

    public int deleteByOrderIdXiangHao(String orderId,String xianghao);
}
