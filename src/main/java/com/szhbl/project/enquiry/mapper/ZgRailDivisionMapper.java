package com.szhbl.project.enquiry.mapper;

import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.dto.ZgRailDivisionDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 整柜铁路运费Mapper接口
 * 
 * @author jhm
 * @date 2020-03-13
 */
@Repository
public interface ZgRailDivisionMapper 
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
     * 删除整柜铁路运费
     * 
     * @param id 整柜铁路运费ID
     * @return 结果
     */
    public int deleteZgRailDivisionById(Long id);

    /**
     * 批量删除整柜铁路运费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZgRailDivisionByIds(Long[] ids);

    /**
     * 批量插入整柜散货费用
     * @param zgRailDivisionDtoList
     * @return
     */
    public int insertMoreZgRailDivision(List<ZgRailDivisionDto> zgRailDivisionDtoList);

     List<ZgRailDivision> selectZgRailDivisionWithMap(Map<String,String> map);

    List<ZgRailDivision> selectZgRailwayExpiration(Map<String,String> map);

     List<ZgRailDivision> selectZgRailDivisionListWithZG(ZgRailDivision zgRailDivision);

    List<ZgRailDivision> selectZynZgRailDivisionWithMap(Map<String, String> map);
}
