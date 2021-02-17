package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.ChargingStandardMapper;
import com.szhbl.project.backclient.domain.ChargingStandard;
import com.szhbl.project.backclient.service.IChargingStandardService;

/**
 * 收费标准Service业务层处理
 * 
 * @author b
 * @date 2020-05-08
 */
@Service
public class ChargingStandardServiceImpl implements IChargingStandardService 
{
    @Autowired
    private ChargingStandardMapper chargingStandardMapper;

    /**
     * 查询收费标准
     * 
     * @param id 收费标准ID
     * @return 收费标准
     */
    @Override
    public ChargingStandard selectChargingStandardById(Integer id)
    {
        return chargingStandardMapper.selectChargingStandardById(id);
    }

    /**
     * 查询收费标准列表
     * 
     * @param chargingStandard 收费标准
     * @return 收费标准
     */
    @Override
    public List<ChargingStandard> selectChargingStandardList(ChargingStandard chargingStandard)
    {
        return chargingStandardMapper.selectChargingStandardList(chargingStandard);
    }

    /**
     * 新增收费标准
     * 
     * @param chargingStandard 收费标准
     * @return 结果
     */
    @Override
    public int insertChargingStandard(ChargingStandard chargingStandard)
    {
        chargingStandard.setCreateTime(DateUtils.getNowDate());
        return chargingStandardMapper.insertChargingStandard(chargingStandard);
    }

    /**
     * 修改收费标准
     * 
     * @param chargingStandard 收费标准
     * @return 结果
     */
    @Override
    public int updateChargingStandard(ChargingStandard chargingStandard)
    {
        chargingStandard.setUpdateTime(DateUtils.getNowDate());
        return chargingStandardMapper.updateChargingStandard(chargingStandard);
    }

    /**
     * 批量删除收费标准
     * 
     * @param ids 需要删除的收费标准ID
     * @return 结果
     */
    @Override
    public int deleteChargingStandardByIds(Integer[] ids)
    {
        return chargingStandardMapper.deleteChargingStandardByIds(ids);
    }

    /**
     * 删除收费标准信息
     * 
     * @param id 收费标准ID
     * @return 结果
     */
    @Override
    public int deleteChargingStandardById(Integer id)
    {
        return chargingStandardMapper.deleteChargingStandardById(id);
    }

    /**
     * 查询是否有同时间段收费标准
     *
     * @param chargingStandard 收费标准
     * @return 收费标准集合
     */
    @Override
    public List<ChargingStandard> selectSameChargingStandard(ChargingStandard chargingStandard) {
        return chargingStandardMapper.selectSameChargingStandard(chargingStandard);
    }
}
