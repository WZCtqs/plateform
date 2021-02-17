package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.domain.vo.BusiSiteDto;

import java.util.List;

/**
 * 场站地址Mapper接口
 *
 * @author szhbl
 * @date 2020-01-08
 */
public interface BusiStationMapper
{
    /**
     * 查询场站地址
     *
     * @param stationId 场站地址ID
     * @return 场站地址
     */
    public BusiStation selectBusiStationById(String stationId);

    /**
     * 查询场站地址列表
     *
     * @param busiStation 场站地址
     * @return 场站地址集合
     */
    public List<BusiStation> selectBusiStationList(BusiStation busiStation);

    /**
     * 查询上货站列表
     * @return
     */
    public List<BusiSiteDto> selectBusiSiteList();

    /**
     * 新增场站地址
     *
     * @param busiStation 场站地址
     * @return 结果
     */
    public int insertBusiStation(BusiStation busiStation);

    /**
     * 修改场站地址
     *
     * @param busiStation 场站地址
     * @return 结果
     */
    public int updateBusiStation(BusiStation busiStation);
    //查询该堆场关联托书
    public List<String> orderIdList(String stationId);

    /**
     * 删除场站地址
     *
     * @param stationId 场站地址ID
     * @return 结果
     */
    public int deleteBusiStationById(String stationId);

    /**
     * 批量删除场站地址
     *
     * @param stationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiStationByIds(String[] stationIds);

    /**
     * 更新场站地址是否启用状态
     *
     * @param stationId 需要更新的数据ID
     * @param isenabled 状态
     * @return 结果
     */
    public int changeStationEnable(String stationId, String isenabled);
}
