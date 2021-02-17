package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.BusiClassessh;
import java.util.List;

/**
 * 排舱管理Service接口
 * 
 * @author b
 * @date 2020-03-27
 */
public interface IBusiClassesshService 
{
    /**
     * 查询排舱管理
     * 
     * @param classId 排舱管理ID
     * @return 排舱管理
     */
    public BusiClassessh selectBusiClassesshById(String classId);

    /**
     * 查询排舱管理列表
     * 
     * @param busiClassessh 排舱管理
     * @return 排舱管理集合
     */
    public List<BusiClassessh> selectBusiClassesshList(BusiClassessh busiClassessh);

    /**
     * 新增排舱管理
     * 
     * @param busiClassessh 排舱管理
     * @return 结果
     */
    public int insertBusiClassessh(BusiClassessh busiClassessh);

    /**
     * 修改排舱管理
     * 
     * @param busiClassessh 排舱管理
     * @return 结果
     */
    public int updateBusiClassessh(BusiClassessh busiClassessh);

    /**
     * 批量删除排舱管理
     * 
     * @param classIds 需要删除的排舱管理ID
     * @return 结果
     */
    public int deleteBusiClassesshByIds(String[] classIds);

    /**
     * 删除排舱管理信息
     * 
     * @param classId 排舱管理ID
     * @return 结果
     */
    public int deleteBusiClassesshById(String classId);
}
