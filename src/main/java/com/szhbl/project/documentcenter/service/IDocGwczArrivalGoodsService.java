package com.szhbl.project.documentcenter.service;


import com.szhbl.project.documentcenter.domain.vo.GWCZArrivalGoods;

/**
 * 拼箱出入库表数据
 *
 * @author hp
 * @date 2020-04-13
 */
public interface IDocGwczArrivalGoodsService {
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
    public int insertGwczArrivalGoods(GWCZArrivalGoods arrivalGoods);

    /**
     * 删除拼箱出入库表信息
     *
     * @return 结果
     */
    public int deleteByOrderId(String orderId);
}
