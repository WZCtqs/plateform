package com.szhbl.project.track.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackAddress;

import java.util.List;

/**
 * 位置地名表Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface ITrackAddressService
{
   /**
     *位置地名表数据新增
     * 
     * @param Ta 新增数据
     * @return AjaxResult
     */
    public AjaxResult addTa(TrackAddress Ta);

    /**
     * 位置地名数据查询
     *
     * @param Ta 查询条件
     * @return List<TrackAddress>
     */
    public List<TrackAddress>  selectByTa(TrackAddress Ta);

    /**
     * 根据id查询位置地名数据
     *
     * @param id 查询条件
     * @return AjaxResult
     */
    public TrackAddress selectById(Long id);

    /**
     * 根据中文名查询位置地名
     *
     * @param name 查询条件
     * @return AjaxResult
     */
    public AjaxResult selectByName(String name);


    /**
     * 位置地名表数据修改
     *
     * @param Ta 修改条件
     * @return AjaxResult
     */
    public AjaxResult updateTa(TrackAddress Ta);

    /**
     * 位置地名表数据删除
     *
     * @param ids 删除条件
     * @return int
     */
    public int deleteTa( Long[] ids);

}
