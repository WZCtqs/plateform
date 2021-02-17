package com.szhbl.project.basic.service;

import com.szhbl.project.basic.domain.DangerousGoods;

import java.util.List;

/**
 * 危险品Service接口
 *
 * @author dps
 * @date 2020-01-15
 */
public interface IDangerousGoodsService {
    /**
     * 查询危险品
     *
     * @param goodsId 危险品ID
     * @return 危险品
     */
    public DangerousGoods selectDangerousGoodsById(String goodsId);

    public String getCnEnName(String name, String language);

    /**
     * 查询危险品列表
     *
     * @param dangerousGoods 危险品
     * @return 危险品集合
     */
    public List<DangerousGoods> selectDangerousGoodsList(DangerousGoods dangerousGoods);

    /**
     * 新增危险品
     *
     * @param dangerousGoods 危险品
     * @return 结果
     */
    public int insertDangerousGoods(DangerousGoods dangerousGoods);

    /**
     * 修改危险品
     *
     * @param dangerousGoods 危险品
     * @return 结果
     */
    public int updateDangerousGoods(DangerousGoods dangerousGoods);

    /**
     * 批量删除危险品
     *
     * @param goodsIds 需要删除的危险品ID
     * @return 结果
     */
    public int deleteDangerousGoodsByIds(String[] goodsIds);

    /**
     * 删除危险品信息
     *
     * @param goodsId 危险品ID
     * @return 结果
     */
    public int deleteDangerousGoodsById(String goodsId);
}
