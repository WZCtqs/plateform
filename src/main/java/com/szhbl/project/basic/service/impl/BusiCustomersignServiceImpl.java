package com.szhbl.project.basic.service.impl;

import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.basic.mapper.BusiCustomersignMapper;
import com.szhbl.project.basic.domain.BusiCustomersign;
import com.szhbl.project.basic.service.IBusiCustomersignService;

/**
 * 客户标记Service业务层处理
 * 
 * @author dps
 * @date 2020-03-16
 */
@Service
public class BusiCustomersignServiceImpl implements IBusiCustomersignService 
{
    @Autowired
    private BusiCustomersignMapper busiCustomersignMapper;

    /**
     * 查询客户标记
     * 
     * @param signId 客户标记ID
     * @return 客户标记
     */
    @Override
    public BusiCustomersign selectBusiCustomersignById(String signId)
    {
        return busiCustomersignMapper.selectBusiCustomersignById(signId);
    }

    /**
     * 查询客户标记列表
     * 
     * @param busiCustomersign 客户标记
     * @return 客户标记
     */
    @Override
    public List<BusiCustomersign> selectBusiCustomersignList(BusiCustomersign busiCustomersign)
    {
        return busiCustomersignMapper.selectBusiCustomersignList(busiCustomersign);
    }
    @Override
    public List<String> selectListName()
    {
        return busiCustomersignMapper.selectListName();
    }

    /**
     * 新增客户标记
     * 
     * @param busiCustomersign 客户标记
     * @return 结果
     */
    @Override
    public int insertBusiCustomersign(BusiCustomersign busiCustomersign)
    {
        busiCustomersign.setSignId(IdUtils.randomUUID());
        busiCustomersign.setCreatedate(DateUtils.getNowDate());
        return busiCustomersignMapper.insertBusiCustomersign(busiCustomersign);
    }

    /**
     * 修改客户标记
     * 
     * @param busiCustomersign 客户标记
     * @return 结果
     */
    @Override
    public int updateBusiCustomersign(BusiCustomersign busiCustomersign)
    {
        return busiCustomersignMapper.updateBusiCustomersign(busiCustomersign);
    }

    /**
     * 批量删除客户标记
     * 
     * @param signIds 需要删除的客户标记ID
     * @return 结果
     */
    @Override
    public int deleteBusiCustomersignByIds(String[] signIds)
    {
        return busiCustomersignMapper.deleteBusiCustomersignByIds(signIds);
    }

    /**
     * 删除客户标记信息
     * 
     * @param signId 客户标记ID
     * @return 结果
     */
    @Override
    public int deleteBusiCustomersignById(String signId)
    {
        return busiCustomersignMapper.deleteBusiCustomersignById(signId);
    }
}
