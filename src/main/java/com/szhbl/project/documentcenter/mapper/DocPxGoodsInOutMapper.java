package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.vo.PxGoodsInOut;

/**
 * 拼箱出入库表
 *
 * @author hp
 * @date 2020-05-6
 */
public interface DocPxGoodsInOutMapper
{
    /**
     * 查询拼箱出入库表信息
     * @param orderId
     * @return
     */
    public PxGoodsInOut selectDocPxGoodsInOutByOrderId(String orderId);

    /**
     * 新增拼箱出入库表信息
     *
     * @return 结果
     */
    public int insertDocPxGoodsInOut(PxGoodsInOut pxGoodsInOut);

    /**
     * 删除拼箱出入库表信息
     *
     * @return 结果
     */
    public int deleteDocPxGoodsInOutByOrderId(String orderId);
}
