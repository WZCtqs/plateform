package com.szhbl.project.basic.service.impl;

import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.basic.mapper.BusiMoneyreteMapper;
import com.szhbl.project.basic.domain.BusiMoneyrete;
import com.szhbl.project.basic.service.IBusiMoneyreteService;

/**
 * 汇率Service业务层处理
 * 
 * @author dps
 * @date 2020-01-15
 */
@Service
public class BusiMoneyreteServiceImpl implements IBusiMoneyreteService 
{
    @Autowired
    private BusiMoneyreteMapper busiMoneyreteMapper;

    /**
     * 查询汇率
     * 
     * @param moneyrateId 汇率ID
     * @return 汇率
     */
    @Override
    public BusiMoneyrete selectBusiMoneyreteById(String moneyrateId)
    {
        return busiMoneyreteMapper.selectBusiMoneyreteById(moneyrateId);
    }

    /**
     * 查询汇率列表
     * 
     * @param busiMoneyrete 汇率
     * @return 汇率
     */
    @Override
    public List<BusiMoneyrete> selectBusiMoneyreteList(BusiMoneyrete busiMoneyrete)
    {
        return busiMoneyreteMapper.selectBusiMoneyreteList(busiMoneyrete);
    }

    /**
     * 新增汇率
     * 
     * @param busiMoneyrete 汇率
     * @return 结果
     */
    @Override
    public int insertBusiMoneyrete(BusiMoneyrete busiMoneyrete)
    {
        busiMoneyrete.setOperationtime(DateUtils.getNowDate());
        busiMoneyrete.setMoneyrateId(IdUtils.randomUUID());
        return busiMoneyreteMapper.insertBusiMoneyrete(busiMoneyrete);
    }

    /**
     * 修改汇率
     * 
     * @param busiMoneyrete 汇率
     * @return 结果
     */
    @Override
    public int updateBusiMoneyrete(BusiMoneyrete busiMoneyrete)
    {
        return busiMoneyreteMapper.updateBusiMoneyrete(busiMoneyrete);
    }

    /**
     * 批量删除汇率
     * 
     * @param moneyrateIds 需要删除的汇率ID
     * @return 结果
     */
    @Override
    public int deleteBusiMoneyreteByIds(String[] moneyrateIds)
    {
        return busiMoneyreteMapper.deleteBusiMoneyreteByIds(moneyrateIds);
    }

    /**
     * 删除汇率信息
     * 
     * @param moneyrateId 汇率ID
     * @return 结果
     */
    @Override
    public int deleteBusiMoneyreteById(String moneyrateId)
    {
        return busiMoneyreteMapper.deleteBusiMoneyreteById(moneyrateId);
    }
}
