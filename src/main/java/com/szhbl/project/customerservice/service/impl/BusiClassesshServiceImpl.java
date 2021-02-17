package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.BusiClassesshMapper;
import com.szhbl.project.customerservice.domain.BusiClassessh;
import com.szhbl.project.customerservice.service.IBusiClassesshService;

/**
 * 排舱管理Service业务层处理
 * 
 * @author b
 * @date 2020-03-27
 */
@Service
public class BusiClassesshServiceImpl implements IBusiClassesshService 
{
    @Autowired
    private BusiClassesshMapper busiClassesshMapper;

    /**
     * 查询排舱管理
     * 
     * @param classId 排舱管理ID
     * @return 排舱管理
     */
    @Override
    public BusiClassessh selectBusiClassesshById(String classId)
    {
        return busiClassesshMapper.selectBusiClassesshById(classId);
    }

    /**
     * 查询排舱管理列表
     * 
     * @param busiClassessh 排舱管理
     * @return 排舱管理
     */
    @Override
    public List<BusiClassessh> selectBusiClassesshList(BusiClassessh busiClassessh)
    {
        return busiClassesshMapper.selectBusiClassesshList(busiClassessh);
    }

    /**
     * 新增排舱管理
     * 
     * @param busiClassessh 排舱管理
     * @return 结果
     */
    @Override
    public int insertBusiClassessh(BusiClassessh busiClassessh)
    {
        return busiClassesshMapper.insertBusiClassessh(busiClassessh);
    }

    /**
     * 修改排舱管理
     * 
     * @param busiClassessh 排舱管理
     * @return 结果
     */
    @Override
    public int updateBusiClassessh(BusiClassessh busiClassessh)
    {
        return busiClassesshMapper.updateBusiClassessh(busiClassessh);
    }

    /**
     * 批量删除排舱管理
     * 
     * @param classIds 需要删除的排舱管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesshByIds(String[] classIds)
    {
        return busiClassesshMapper.deleteBusiClassesshByIds(classIds);
    }

    /**
     * 删除排舱管理信息
     * 
     * @param classId 排舱管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesshById(String classId)
    {
        return busiClassesshMapper.deleteBusiClassesshById(classId);
    }
}
