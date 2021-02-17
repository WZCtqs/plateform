package com.szhbl.project.track.service;

import com.szhbl.project.track.domain.TrackIcon;
import java.util.List;

/**
 * 运踪图标Service接口
 * 
 * @author szhbl
 * @date 2020-03-20
 */
public interface ITrackIconService 
{
    /**
     * 查询运踪图标
     *
     * @return 运踪图标
     */
    //public void inquery();

    /**
     * 查询运踪图标
     * 
     * @param id 运踪图标ID
     * @return 运踪图标
     */
    public TrackIcon selectTrackIconById(Long id);

    /**
     * 查询运踪图标列表
     * 
     * @param trackIcon 运踪图标
     * @return 运踪图标集合
     */
    public List<TrackIcon> selectTrackIconList(TrackIcon trackIcon);

    /**
     * 新增运踪图标
     * 
     * @param trackIcon 运踪图标
     * @return 结果
     */
    public int insertTrackIcon(TrackIcon trackIcon);

    /**
     * 修改运踪图标
     * 
     * @param trackIcon 运踪图标
     * @return 结果
     */
    public int updateTrackIcon(TrackIcon trackIcon);

    /**
     * 批量删除运踪图标
     * 
     * @param ids 需要删除的运踪图标ID
     * @return 结果
     */
    public int deleteTrackIconByIds(Long[] ids);

    /**
     * 删除运踪图标信息
     * 
     * @param id 运踪图标ID
     * @return 结果
     */
    public int deleteTrackIconById(Long id);
}
