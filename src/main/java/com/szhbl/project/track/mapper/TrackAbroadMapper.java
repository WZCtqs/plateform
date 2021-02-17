package com.szhbl.project.track.mapper;

import com.szhbl.project.track.domain.TrackAbroad;
import java.util.List;

/**
 * 运踪_中亚境外Mapper接口
 * 
 * @author lzd
 * @date 2020-03-26
 */
public interface TrackAbroadMapper 
{
    /**
     * 查询运踪_中亚境外
     * 
     * @param id 运踪_中亚境外ID
     * @return 运踪_中亚境外
     */
    public TrackAbroad selectTrackAbroadById(Integer id);

    /**
     * 查询运踪_中亚境外列表
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 运踪_中亚境外集合
     */
    public List<TrackAbroad> selectTrackAbroadList(TrackAbroad trackAbroad);

    /**
     * 新增运踪_中亚境外
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 结果
     */
    public int insertTrackAbroad(TrackAbroad trackAbroad);

    /**
     * 修改运踪_中亚境外
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 结果
     */
    public int updateTrackAbroad(TrackAbroad trackAbroad);

    /**
     * 删除运踪_中亚境外
     * 
     * @param id 运踪_中亚境外ID
     * @return 结果
     */
    public int deleteTrackAbroadById(Integer id);

    /**
     * 批量删除运踪_中亚境外
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackAbroadByIds(Integer[] ids);
}
