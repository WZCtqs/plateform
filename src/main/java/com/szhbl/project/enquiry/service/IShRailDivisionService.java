package com.szhbl.project.enquiry.service;

import com.szhbl.project.enquiry.domain.ShRailDivision;
import java.util.List;
import java.util.Map;

/**
 * 散货铁路运费Service接口
 * 
 * @author jhm
 * @date 2020-03-14
 */
public interface IShRailDivisionService 
{
    /**
     * 查询散货铁路运费
     * 
     * @param id 散货铁路运费ID
     * @return 散货铁路运费
     */
    public ShRailDivision selectShRailDivisionById(Long id);

    /**
     * 查询散货铁路运费列表
     * 
     * @param shRailDivision 散货铁路运费
     * @return 散货铁路运费集合
     */
    public List<ShRailDivision> selectShRailDivisionList(ShRailDivision shRailDivision);

    /**
     * 新增散货铁路运费
     * 
     * @param shRailDivision 散货铁路运费
     * @return 结果
     */
    public int insertShRailDivision(ShRailDivision shRailDivision);

    /**
     * 修改散货铁路运费
     * 
     * @param shRailDivision 散货铁路运费
     * @return 结果
     */
    public int updateShRailDivision(ShRailDivision shRailDivision);

    /**
     * 批量删除散货铁路运费
     * 
     * @param ids 需要删除的散货铁路运费ID
     * @return 结果
     */
    public int deleteShRailDivisionByIds(Long[] ids);

    /**
     * 删除散货铁路运费信息
     * 
     * @param id 散货铁路运费ID
     * @return 结果
     */
    public int deleteShRailDivisionById(Long id);


    List<ShRailDivision> selectShRailDivisionListWithSH(ShRailDivision shRailDivision);

    List<ShRailDivision> selectShRailDivisionWithMap(Map<String,String> map);
}
