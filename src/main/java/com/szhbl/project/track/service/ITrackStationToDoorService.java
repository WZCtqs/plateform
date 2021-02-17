package com.szhbl.project.track.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackStationToDoor;

/**
 * 站到门/门到站Service接口
 *
 * @author szhbl
 *
 */
public interface ITrackStationToDoorService
{
    /**
     *站到门/门到站表数据新增
     *
     * @param tstd 新增数据
     * @return AjaxResult
     */
    public AjaxResult addTstd(TrackStationToDoor tstd);

    /**
     * 站到门/门到站数据查询
     *
     * @param tstd 查询条件
     * @return List<TrackStationToDoor>
     *//*
    public List<TrackAddress>  selectByTa(TrackStationToDoor tstd);

    *//**
     * 站到门/门到站表数据修改
     *
     * @param tstd 修改条件
     * @return AjaxResult
     *//*
    public AjaxResult updateTa(TrackStationToDoor tstd);

    *//**
     * 站到门/门到站表数据删除
     *
     * @param id 删除条件
     * @return int
     */
    public int deleteTstd(Long id);

}
