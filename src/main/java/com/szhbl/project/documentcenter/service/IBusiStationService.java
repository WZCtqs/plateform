package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.domain.vo.BusiSiteDto;

import java.util.List;

/**
 * 场站地址Service接口
 *
 * @author szhbl
 * @date 2020-01-08
 */
public interface IBusiStationService
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
     * 获取城市站点信息
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

    /**
     * 批量删除场站地址
     *
     * @param stationIds 需要删除的场站地址ID
     * @return 结果
     */
    public int deleteBusiStationByIds(String[] stationIds);

    /**
     * 删除场站地址信息
     *
     * @param stationId 场站地址ID
     * @return 结果
     */
    public int deleteBusiStationById(String stationId);

}
