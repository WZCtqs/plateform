package com.szhbl.project.track.mapper;


import com.szhbl.project.track.domain.TrackRookieSite;

import java.util.List;

/**
 * 菜鸟站点表Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-08
 */
public interface TrackRookieSiteMapper
{
    /**
     * 更新菜鸟站点表
     *
     * @param Trs 菜鸟站点实体类
     * @return
     */
    public int updateTrs(TrackRookieSite Trs);

    /**
     * 新增菜鸟站点
     *
     * @param Trs 菜鸟站点实体类
     * @return
     */
    public int addTrs(TrackRookieSite Trs);

    /**
     * 查询菜鸟站点
     *
     * @param Trs 菜鸟站点实体类
     * @return
     */
    public List<TrackRookieSite> selectByTrs(TrackRookieSite Trs);

    /**
     * 查询菜鸟站点
     *
     * @param id
     * @return
     */
    public TrackRookieSite  selectById(Long id);

}
