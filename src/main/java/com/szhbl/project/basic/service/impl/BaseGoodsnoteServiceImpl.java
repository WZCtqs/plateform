package com.szhbl.project.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.basic.mapper.BaseGoodsnoteMapper;
import com.szhbl.project.basic.domain.BaseGoodsnote;
import com.szhbl.project.basic.service.IBaseGoodsnoteService;

/**
 * 特殊单证物品Service业务层处理
 * 
 * @author dps
 * @date 2020-01-15
 */
@Service
public class BaseGoodsnoteServiceImpl implements IBaseGoodsnoteService 
{
    @Autowired
    private BaseGoodsnoteMapper baseGoodsnoteMapper;

    /**
     * 查询特殊单证物品
     * 
     * @param id 特殊单证物品ID
     * @return 特殊单证物品
     */
    @Override
    public BaseGoodsnote selectBaseGoodsnoteById(Long id)
    {
        return baseGoodsnoteMapper.selectBaseGoodsnoteById(id);
    }

    /**
     * 查询特殊单证物品列表
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 特殊单证物品
     */
    @Override
    public List<BaseGoodsnote> selectBaseGoodsnoteList(BaseGoodsnote baseGoodsnote)
    {
        return baseGoodsnoteMapper.selectBaseGoodsnoteList(baseGoodsnote);
    }

    /**
     * 新增特殊单证物品
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 结果
     */
    @Override
    public int insertBaseGoodsnote(BaseGoodsnote baseGoodsnote)
    {
        return baseGoodsnoteMapper.insertBaseGoodsnote(baseGoodsnote);
    }

    /**
     * 修改特殊单证物品
     * 
     * @param baseGoodsnote 特殊单证物品
     * @return 结果
     */
    @Override
    public int updateBaseGoodsnote(BaseGoodsnote baseGoodsnote)
    {
        return baseGoodsnoteMapper.updateBaseGoodsnote(baseGoodsnote);
    }

    /**
     * 批量删除特殊单证物品
     * 
     * @param ids 需要删除的特殊单证物品ID
     * @return 结果
     */
    @Override
    public int deleteBaseGoodsnoteByIds(Long[] ids)
    {
        return baseGoodsnoteMapper.deleteBaseGoodsnoteByIds(ids);
    }

    /**
     * 删除特殊单证物品信息
     * 
     * @param id 特殊单证物品ID
     * @return 结果
     */
    @Override
    public int deleteBaseGoodsnoteById(Long id)
    {
        return baseGoodsnoteMapper.deleteBaseGoodsnoteById(id);
    }
}
