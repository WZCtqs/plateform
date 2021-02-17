package com.szhbl.project.cabinarrangement.service;

import com.szhbl.project.cabinarrangement.domain.BusiClassesPc;
import java.util.List;

/**
 * 排舱管理Service接口
 * 
 * @author dps
 * @date 2020-01-14
 */
public interface IBusiClassesPcService
{
    /**
     * 查询排舱管理
     * 
     * @param classId 排舱管理ID
     * @return 排舱管理
     */
    public BusiClassesPc selectBusiClassesById(Long classId);

    /**
     * 查询排舱管理列表
     * 
     * @param busiClassesPc 排舱管理
     * @return 排舱管理集合
     */
    public List<BusiClassesPc> selectBusiClassesList(BusiClassesPc busiClassesPc);

    /**
     * 新增排舱管理
     * 
     * @param busiClassesPc 排舱管理
     * @return 结果
     */
    public int insertBusiClasses(BusiClassesPc busiClassesPc);

    /**
     * 修改排舱管理
     * 
     * @param busiClassesPc 排舱管理
     * @return 结果
     */
    public int updateBusiClasses(BusiClassesPc busiClassesPc);

    /**
     * 批量删除排舱管理
     * 
     * @param classIds 需要删除的排舱管理ID
     * @return 结果
     */
    public int deleteBusiClassesByIds(Long[] classIds);

    /**
     * 删除排舱管理信息
     * 
     * @param classId 排舱管理ID
     * @return 结果
     */
    public int deleteBusiClassesById(Long classId);
}
