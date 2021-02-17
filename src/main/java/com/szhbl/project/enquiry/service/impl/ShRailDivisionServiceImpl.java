package com.szhbl.project.enquiry.service.impl;

import java.util.List;
import java.util.Map;

import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.enquiry.mapper.ShRailDivisionMapper;
import com.szhbl.project.enquiry.domain.ShRailDivision;
import com.szhbl.project.enquiry.service.IShRailDivisionService;

/**
 * 散货铁路运费Service业务层处理
 * 
 * @author jhm
 * @date 2020-03-14
 */
@Service
public class ShRailDivisionServiceImpl implements IShRailDivisionService 
{
    @Autowired
    private ShRailDivisionMapper shRailDivisionMapper;

    /**
     * 查询散货铁路运费
     * 
     * @param id 散货铁路运费ID
     * @return 散货铁路运费
     */
    @Override
    public ShRailDivision selectShRailDivisionById(Long id)
    {
        return shRailDivisionMapper.selectShRailDivisionById(id);
    }

    /**
     * 查询散货铁路运费列表
     * 
     * @param shRailDivision 散货铁路运费
     * @return 散货铁路运费
     */
    @Override
    public List<ShRailDivision> selectShRailDivisionList(ShRailDivision shRailDivision)
    {
        return shRailDivisionMapper.selectShRailDivisionList(shRailDivision);
    }

    /**
     * 新增散货铁路运费
     * 
     * @param shRailDivision 散货铁路运费
     * @return 结果
     */
    @Override
    public int insertShRailDivision(ShRailDivision shRailDivision)
    {
        shRailDivision.setCreateTime(DateUtils.getNowDate());
        return shRailDivisionMapper.insertShRailDivision(shRailDivision);
    }

    /**
     * 修改散货铁路运费
     * 
     * @param shRailDivision 散货铁路运费
     * @return 结果
     */
    @Override
    public int updateShRailDivision(ShRailDivision shRailDivision)
    {
        shRailDivision.setUpdateTime(DateUtils.getNowDate());
        return shRailDivisionMapper.updateShRailDivision(shRailDivision);
    }

    /**
     * 批量删除散货铁路运费
     * 
     * @param ids 需要删除的散货铁路运费ID
     * @return 结果
     */
    @Override
    public int deleteShRailDivisionByIds(Long[] ids)
    {
        return shRailDivisionMapper.deleteShRailDivisionByIds(ids);
    }

    /**
     * 删除散货铁路运费信息
     * 
     * @param id 散货铁路运费ID
     * @return 结果
     */
    @Override
    public int deleteShRailDivisionById(Long id)
    {
        return shRailDivisionMapper.deleteShRailDivisionById(id);
    }

    @Override
    public List<ShRailDivision> selectShRailDivisionListWithSH(ShRailDivision shRailDivision) {
        return shRailDivisionMapper.selectShRailDivisionListWithSH(shRailDivision);
    }

    @Override
    public List<ShRailDivision> selectShRailDivisionWithMap(Map<String, String> map) {
        return shRailDivisionMapper.selectShRailDivisionWithMap(map);
    }
}
