package com.szhbl.project.enquiry.service;

import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.dto.ZgRailDivisionDto;

import java.util.List;
import java.util.Map;

/**
 * 整柜铁路运费Service接口
 * 
 * @author jhm
 * @date 2020-03-13
 */
public interface IZgRailDivisionService 
{
    /**
     * 查询整柜铁路运费
     * 
     * @param id 整柜铁路运费ID
     * @return 整柜铁路运费
     */
    public ZgRailDivision selectZgRailDivisionById(Long id);

    /**
     * 查询整柜铁路运费列表
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 整柜铁路运费集合
     */
    public List<ZgRailDivision> selectZgRailDivisionList(ZgRailDivision zgRailDivision);

    /**
     * 新增整柜铁路运费
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 结果
     */
    public int insertZgRailDivision(ZgRailDivision zgRailDivision);

    /**
     * 修改整柜铁路运费
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 结果
     */
    public int updateZgRailDivision(ZgRailDivision zgRailDivision);

    /**
     * 批量删除整柜铁路运费
     * 
     * @param ids 需要删除的整柜铁路运费ID
     * @return 结果
     */
    public int deleteZgRailDivisionByIds(Long[] ids);

    /**
     * 删除整柜铁路运费信息
     * 
     * @param id 整柜铁路运费ID
     * @return 结果
     */
    public int deleteZgRailDivisionById(Long id);

    public int insertMoreZgRailDivision(List<ZgRailDivisionDto> zgRailDivisionDtoList);


    List<ZgRailDivision> selectZgRailDivisionWithMap(Map<String,String> map);

    List<ZgRailDivision>selectZgRailDivisionListWithZG(ZgRailDivision zgRailDivision);
}
