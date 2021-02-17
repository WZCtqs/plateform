package com.szhbl.project.track.mapper;


import com.szhbl.project.track.domain.TrackAddress;

import java.util.List;

/**
 * 位置地名表Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-08
 */
public interface TrackAddressMapper
{
    /**
     * 更新位置地名表
     *
     * @param trackAddress 位置地名实体类
     * @return
     */
    public int updateTa(TrackAddress trackAddress);

    /**
     * 新增位置地名
     *
     * @param trackAddress 位置地名实体类
     * @return
     */
    public int addTa(TrackAddress trackAddress);

    /**
     * 查询位置地名
     *
     * @param trackAddress 位置地名实体类
     * @return
     */
    public List<TrackAddress> selectByTa(TrackAddress trackAddress);

    /**
     * 根据id查询位置地名
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
    public TrackAddress selectByName(String name);

}
