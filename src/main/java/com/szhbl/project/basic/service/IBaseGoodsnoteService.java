package com.szhbl.project.basic.service;

import com.szhbl.project.basic.domain.BaseGoodsnote;
import java.util.List;

/**
 * 特殊单证物品Service接口
 * 
 * @author dps
 * @date 2020-01-15
 */
public interface IBaseGoodsnoteService 
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
     * 批量删除特殊单证物品
     * 
     * @param ids 需要删除的特殊单证物品ID
     * @return 结果
     */
    public int deleteBaseGoodsnoteByIds(Long[] ids);

    /**
     * 删除特殊单证物品信息
     * 
     * @param id 特殊单证物品ID
     * @return 结果
     */
    public int deleteBaseGoodsnoteById(Long id);
}
