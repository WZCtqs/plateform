package com.szhbl.project.cabinarrangement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.cabinarrangement.mapper.BusiClassesPcMapper;
import com.szhbl.project.cabinarrangement.domain.BusiClassesPc;
import com.szhbl.project.cabinarrangement.service.IBusiClassesPcService;

/**
 * 排舱管理Service业务层处理
 * 
 * @author dps
 * @date 2020-01-14
 */
@Service
public class BusiClassesPcServiceImpl implements IBusiClassesPcService
{
    @Autowired
    private BusiClassesPcMapper busiClassesPcMapper;

    /**
     * 查询排舱管理
     * 
     * @param classId 排舱管理ID
     * @return 排舱管理
     */
    @Override
    public BusiClassesPc selectBusiClassesById(Long classId)
    {
        return busiClassesPcMapper.selectBusiClassesById(classId);
    }

    /**
     * 查询排舱管理列表
     * 
     * @param busiClassesPc 排舱管理
     * @return 排舱管理
     */
    @Override
    public List<BusiClassesPc> selectBusiClassesList(BusiClassesPc busiClassesPc)
    {
        return busiClassesPcMapper.selectBusiClassesList(busiClassesPc);
    }

    /**
     * 新增排舱管理
     * 
     * @param busiClassesPc 排舱管理
     * @return 结果
     */
    @Override
    public int insertBusiClasses(BusiClassesPc busiClassesPc)
    {
        return busiClassesPcMapper.insertBusiClasses(busiClassesPc);
    }

    /**
     * 修改排舱管理
     * 
     * @param busiClassesPc 排舱管理
     * @return 结果
     */
    @Override
    public int updateBusiClasses(BusiClassesPc busiClassesPc)
    {
        return busiClassesPcMapper.updateBusiClasses(busiClassesPc);
    }

    /**
     * 批量删除排舱管理
     * 
     * @param classIds 需要删除的排舱管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesByIds(Long[] classIds)
    {
        return busiClassesPcMapper.deleteBusiClassesByIds(classIds);
    }

    /**
     * 删除排舱管理信息
     * 
     * @param classId 排舱管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesById(Long classId)
    {
        return busiClassesPcMapper.deleteBusiClassesById(classId);
    }
}
