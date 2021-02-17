package com.szhbl.project.basic.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.basic.mapper.BusiDictMapper;
import com.szhbl.project.basic.domain.BusiDict;
import com.szhbl.project.basic.service.IBusiDictService;

/**
 * 贸易方式Service业务层处理
 * 
 * @author dps
 * @date 2020-03-31
 */
@Service
public class BusiDictServiceImpl implements IBusiDictService 
{
    @Autowired
    private BusiDictMapper busiDictMapper;

    /**
     * 订单页面贸易方式
     *
     * @param busiDict 贸易方式
     * @return 贸易方式
     */
    @Override
    public List<BusiDict> selectBusiDictListMy(BusiDict busiDict)
    {
        return busiDictMapper.selectBusiDictListMy(busiDict);
    }

    /**
     * 查询贸易方式
     * 
     * @param id 贸易方式ID
     * @return 贸易方式
     */
    @Override
    public BusiDict selectBusiDictById(Long id)
    {
        return busiDictMapper.selectBusiDictById(id);
    }

    /**
     * 查询贸易方式列表
     * 
     * @param busiDict 贸易方式
     * @return 贸易方式
     */
    @Override
    public List<BusiDict> selectBusiDictList(BusiDict busiDict)
    {
        return busiDictMapper.selectBusiDictList(busiDict);
    }

    /**
     * 新增贸易方式
     * 
     * @param busiDict 贸易方式
     * @return 结果
     */
    @Override
    public int insertBusiDict(BusiDict busiDict)
    {
        busiDict.setCreateTime(DateUtils.getNowDate());
        return busiDictMapper.insertBusiDict(busiDict);
    }

    /**
     * 修改贸易方式
     * 
     * @param busiDict 贸易方式
     * @return 结果
     */
    @Override
    public int updateBusiDict(BusiDict busiDict)
    {
        busiDict.setUpdateTime(DateUtils.getNowDate());
        return busiDictMapper.updateBusiDict(busiDict);
    }

    /**
     * 批量删除贸易方式
     * 
     * @param ids 需要删除的贸易方式ID
     * @return 结果
     */
    @Override
    public int deleteBusiDictByIds(Long[] ids)
    {
        return busiDictMapper.deleteBusiDictByIds(ids);
    }

    /**
     * 删除贸易方式信息
     * 
     * @param id 贸易方式ID
     * @return 结果
     */
    @Override
    public int deleteBusiDictById(Long id)
    {
        return busiDictMapper.deleteBusiDictById(id);
    }
}
