package com.szhbl.project.cabinarrangement.mapper;

import com.szhbl.project.cabinarrangement.domain.BusiShippingorderGoodsPc;
import com.szhbl.project.cabinarrangement.vo.px;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单商品Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-15
 */
@Repository
public interface BusiShippingorderGoodsPcMapper
{
    /**
     * 查询订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 订单商品
     */
    public BusiShippingorderGoodsPc selectBusiShippingorderGoodsById(String goodsId);

    /**
     * 查询订单商品列表
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 订单商品集合
     */
    public List<BusiShippingorderGoodsPc> selectBusiShippingorderGoodsList(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

    /**
     * 新增订单商品
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 结果
     */
    public int insertBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

    /**
     * 修改订单商品
     * 
     * @param busiShippingorderGoodsPc 订单商品
     * @return 结果
     */
    public int updateBusiShippingorderGoods(BusiShippingorderGoodsPc busiShippingorderGoodsPc);

    /**
     * 删除订单商品
     * 
     * @param goodsId 订单商品ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsById(String goodsId);

    /**
     * 批量删除订单商品
     * 
     * @param goodsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderGoodsByIds(String[] goodsIds);

    px pxCount(String classId);
    px selectPx(@Param("classId") String classId, @Param("station") String station, @Param("westAndEast") String westAndEast);
}
