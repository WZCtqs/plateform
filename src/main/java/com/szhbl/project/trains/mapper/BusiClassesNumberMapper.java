package com.szhbl.project.trains.mapper;

import com.szhbl.project.trains.domain.BusiClassesNumber;

import java.util.List;

/**
 * 班列号Mapper接口
 * 
 * @author dps
 * @date 2020-03-13
 */
public interface BusiClassesNumberMapper 
{
    /**
     * 查询班列号
     * 
     * @param classTId 班列号ID
     * @return 班列号
     */
    public BusiClassesNumber selectBusiClassesNumberById(String classTId);

    /**
     * 查询班列号列表
     * 
     * @param busiClassesNumber 班列号
     * @return 班列号集合
     */
    public List<BusiClassesNumber> selectBusiClassesNumberList(BusiClassesNumber busiClassesNumber);

    /**
     * 新增班列号
     * 
     * @param busiClassesNumber 班列号
     * @return 结果
     */
    public int insertBusiClassesNumber(BusiClassesNumber busiClassesNumber);

    /**
     * 修改班列号
     * 
     * @param busiClassesNumber 班列号
     * @return 结果
     */
    public int updateBusiClassesNumber(BusiClassesNumber busiClassesNumber);

    /**
     * 删除班列号
     * 
     * @param classTId 班列号ID
     * @return 结果
     */
    public int deleteBusiClassesNumberById(String classTId);

    /**
     * 批量删除班列号
     * 
     * @param classTIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiClassesNumberByIds(String[] classTIds);

    /**
     * 校验班列号是否唯一
     *
     * @param classTNumber 班列号
     * @return 结果
     */
    public int checkNumberUnique(String classTNumber);

    /**
     * 校验班列号是否唯一(更新)
     *
     * @param classTNumber 班列号
     * @return 结果
     */
    public BusiClassesNumber checkNumberUpdUnique(String classTNumber);
}
