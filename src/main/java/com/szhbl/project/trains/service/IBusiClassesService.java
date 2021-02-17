package com.szhbl.project.trains.service;

import com.szhbl.project.trains.domain.BusiClasses;

import java.util.Date;
import java.util.List;

/**
 * 班列Service接口
 * 
 * @author dps
 * @date 2020-01-13
 */
public interface IBusiClassesService 
{
    /**
     * 查询班列
     * 
     * @param classId 班列ID
     * @return 班列
     */
    public BusiClasses selectBusiClassesById(String classId);

    /**
     * 根据班列编号查询班列
     * 
     * @param classBh 班列编号
     * @return 班列
     */
    public BusiClasses selectBusiClassesByBh(String classBh);

    /**
     * 查询班列列表
     * 
     * @param busiClasses 班列
     * @return 班列集合
     */
    public List<BusiClasses> selectBusiClassesList(BusiClasses busiClasses);

    /**
     * 新增班列
     * 
     * @param busiClasses 班列
     * @return 结果
     */
    public int insertBusiClasses(BusiClasses busiClasses);

    /**
     * 修改班列
     * 
     * @param busiClasses 班列
     * @return 结果
     */
    public int updateBusiClasses(BusiClasses busiClasses);

    /**
     * 批量删除班列
     * 
     * @param classIds 需要删除的班列ID
     * @return 结果
     */
    public int deleteBusiClassesByIds(String[] classIds);

    /**
     * 删除班列信息
     * 
     * @param classId 班列ID
     * @return 结果
     */
    public int deleteBusiClassesById(String classId);

    /**
     * 校验班列编号是否唯一
     *
     * @param classBh 班列编号
     * @return 结果
     */
    public String checkclassBhUnique(String classBh);

    /**
     * 校验班列编号是否唯一(更新)
     *
     * @param busiClasses 班列编号
     * @return 结果
     */
    public String checkclassBhUniqueUpd(BusiClasses busiClasses);

    /**
     * 修改是否可修改状态
     *
     * @param classId
     * @param pxentryState 线路站点
     * @return 结果
     */
    public int updatePxStatus(String classId,Long pxentryState);

    /**
     * 查看是否有模板下班列
     *
     * @param classTId 模板id
     * @return 结果
     */
    public boolean hasChildByTemId(String classTId);

    /**
     * 更新班列状态
     * @param nowDate
     */
    public void updateClassState(Date nowDate);
}
