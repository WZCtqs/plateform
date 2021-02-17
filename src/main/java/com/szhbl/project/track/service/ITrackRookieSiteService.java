package com.szhbl.project.track.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackRookieSite;

import java.util.List;

/**
 * 菜鸟站点表Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface ITrackRookieSiteService
{
   /**
     *菜鸟站点表数据新增
     * 
     * @param Trs 新增数据
     * @return AjaxResult
     */
    public AjaxResult addTrs(TrackRookieSite Trs);

    /**
     * 菜鸟站点数据查询
     *
     * @param Trs 查询条件
     * @return List<TrackRookieSite>
     */
    public List<TrackRookieSite>  selectByTrs(TrackRookieSite Trs);

    /**
     * 菜鸟站点数据查询
     *
     * @param id 查询条件
     * @return List<TrackRookieSite>
     */
    public TrackRookieSite  selectById(Long id);

    /**
     * 菜鸟站点表数据修改
     *
     * @param Trs 修改条件
     * @return AjaxResult
     */
    public AjaxResult updateTrs(TrackRookieSite Trs);

    /**
     * 菜鸟站点表数据删除
     *
     * @param id 删除条件
     * @return int
     */
    public int deleteTa(String id);

}
