package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.vo.GWCZArrivalGoods;

/**
 * 拼箱出入库表
 *
 * @author hp
 * @date 2020-05-6
 */
public interface DocGWCZArrivalGoodsMapper {
    /**
     * 查询拼箱出入库表信息
     *
     * @param orderId
     * @return
     */
    public GWCZArrivalGoods selecGwczArrivalGoodsByOrderId(String orderId);

    /**
     * 新增拼箱出入库表信息
     *
     * @return 结果
     */
    public int insertGwczArrivalGoods(GWCZArrivalGoods pxGoodsInOut);

    /**
     * 删除拼箱出入库表信息
     *
     * @return 结果
     */
    public int deleteByOrderId(String orderId);
}
