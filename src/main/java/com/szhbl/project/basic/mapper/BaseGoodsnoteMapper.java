package com.szhbl.project.basic.mapper;

import com.szhbl.project.basic.domain.BaseGoodsnote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 特殊单证物品Mapper接口
 * 
 * @author dps
 * @date 2020-01-15
 */
@Repository
public interface BaseGoodsnoteMapper 
{
    /**
     * 查询特殊单证物品
     * 
     * @param id 特殊单证物品ID
     * @return 特殊单证物品
     */
    public BaseGoodsnote selectBaseGoodsnoteById(Long id);

    /**
     * 查询特殊单证物品列表
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 特殊单证物品集合
     */
    public List<BaseGoodsnote> selectBaseGoodsnoteList(BaseGoodsnote baseGoodsnote);

    /**
     * 查询特殊单证物品列表(根据有色金属备注)
     *
     * @param inhs 特殊单证物品
     * @return 特殊单证物品集合
     */
    public BaseGoodsnote selectBaseGoodsnoteListByHs(String inhs);

    /**
     * 查询特殊单证物品列表(根据名称)
     */
    public List<BaseGoodsnote> selectBaseGoodsnoteListByName(@Param("eastandwest") String eastandwest,@Param("goodsname") String goodsname);

    /**
     * 查询特殊单证物品列表(根据编码)
     */
    public List<BaseGoodsnote> selectBaseGoodsnoteListByOrderHs(@Param("eastandwest")String eastandwest,@Param("inhs")String inhs);

    /**
     * 新增特殊单证物品
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 结果
     */
    public int insertBaseGoodsnote(BaseGoodsnote baseGoodsnote);

    /**
     * 修改特殊单证物品
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 结果
     */
    public int updateBaseGoodsnote(BaseGoodsnote baseGoodsnote);

    /**
     * 删除特殊单证物品
     * 
     * @param id 特殊单证物品ID
     * @return 结果
     */
    public int deleteBaseGoodsnoteById(Long id);

    /**
     * 批量删除特殊单证物品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBaseGoodsnoteByIds(Long[] ids);
}
