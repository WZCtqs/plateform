package com.szhbl.project.basic.mapper;

import com.szhbl.project.basic.domain.DangerousGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 危险品Mapper接口
 *
 * @author dps
 * @date 2020-01-15
 */
@Repository
public interface DangerousGoodsMapper {
    /**
     * 查询危险品
     *
     * @param goodsId 危险品ID
     * @return 危险品
     */
    public DangerousGoods selectDangerousGoodsById(String goodsId);

    /**
     * 查询危险品
     *
     * @param goodsName 危险品名称
     * @return 危险品
     */
    public DangerousGoods selectDangerousGoodsByName(String goodsName); //危险品和疑似危险品
    //危险品（订舱）
    public DangerousGoods selectDangerousGoodsByNameByLevel(String goodsName,String goodsEnName); //危险品
    public DangerousGoods selectReportHsByNameByLevel(String goodsReport); //报关hs
    public DangerousGoods selectClearanceHsByNameByLevel(String goodsClearance); //清关hs
    //危险品、疑似危险品备注
    public List<DangerousGoods> selectDanGoodsByName(String goodsName,String goodsEnName);
    public List<DangerousGoods> selectDanGoodsByReportHs(String goodsReport);
    public List<DangerousGoods> selectDanGoodsByClearanceHs(String goodsClearance);

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
     * 删除危险品
     *
     * @param goodsId 危险品ID
     * @return 结果
     */
    public int deleteDangerousGoodsById(String goodsId);

    /**
     * 批量删除危险品
     *
     * @param goodsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDangerousGoodsByIds(String[] goodsIds);

    public DangerousGoods getCnEnName(@Param("name") String name, @Param("language") String language);
}
