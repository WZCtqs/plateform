package com.szhbl.project.track.mapper;

import com.szhbl.project.track.domain.TrackIcon;
import com.szhbl.project.track.domain.vo.RegionVo;

import java.util.List;

/**
 * 运踪图标Mapper接口
 * 
 * @author szhbl
 * @date 2020-03-20
 */
public interface TrackIconMapper 
{
    public List<RegionVo> selectName();
    public int  updateRegion(RegionVo shijiName);
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
     * 删除运踪图标
     * 
     * @param id 运踪图标ID
     * @return 结果
     */
    public int deleteTrackIconById(Long id);

    /**
     * 批量删除运踪图标
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackIconByIds(Long[] ids);
}
