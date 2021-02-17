package com.szhbl.project.backclient.service;

import com.szhbl.project.backclient.domain.ChargingStandard;
import java.util.List;

/**
 * 收费标准Service接口
 * 
 * @author b
 * @date 2020-05-08
 */
public interface IChargingStandardService 
{
    /**
     * 查询收费标准
     * 
     * @param id 收费标准ID
     * @return 收费标准
     */
    public ChargingStandard selectChargingStandardById(Integer id);

    /**
     * 查询收费标准列表
     * 
     * @param chargingStandard 收费标准
     * @return 收费标准集合
     */
    public List<ChargingStandard> selectChargingStandardList(ChargingStandard chargingStandard);

    /**
     * 新增收费标准
     * 
     * @param chargingStandard 收费标准
     * @return 结果
     */
    public int insertChargingStandard(ChargingStandard chargingStandard);

    /**
     * 修改收费标准
     * 
     * @param chargingStandard 收费标准
     * @return 结果
     */
    public int updateChargingStandard(ChargingStandard chargingStandard);

    /**
     * 批量删除收费标准
     * 
     * @param ids 需要删除的收费标准ID
     * @return 结果
     */
    public int deleteChargingStandardByIds(Integer[] ids);

    /**
     * 删除收费标准信息
     * 
     * @param id 收费标准ID
     * @return 结果
     */
    public int deleteChargingStandardById(Integer id);

    /**
     * 查询是否有同时间段收费标准
     *
     * @param chargingStandard 收费标准
     * @return 收费标准集合
     */
    List<ChargingStandard> selectSameChargingStandard(ChargingStandard chargingStandard);
}
