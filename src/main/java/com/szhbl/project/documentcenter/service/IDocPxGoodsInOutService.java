package com.szhbl.project.documentcenter.service;


import com.szhbl.project.documentcenter.domain.vo.PxGoodsInOut;

/**
 * 拼箱出入库表数据
 *
 * @author hp
 * @date 2020-04-13
 */
public interface IDocPxGoodsInOutService
{
    /**
     * 查询拼箱出入库表
     * @param orderId
     * @return
     */
    public PxGoodsInOut selectDocPxGoodsInOutByOrderId(String orderId);

    /**
     * 新增拼箱出入库表
     *
     * @return 结果
     */
    public int insertDocPxGoodsInOut(PxGoodsInOut pxGoodsInOut);

    /**
     * 删除拼箱出入库表
     *
     * @return 结果
     */
    public int deleteDocPxGoodsInOutByOrderId(String orderId);
}
