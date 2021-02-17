package com.szhbl.project.track.mapper;

import com.szhbl.project.track.domain.TrackRunTimeCensus;
import com.szhbl.project.track.domain.vo.BusiSiteVo;
import com.szhbl.project.track.domain.vo.TrackRunTimeCensusVo;

import java.util.List;

/**
 * 运踪_班列运行时间统计Mapper接口
 * 
 * @author lzd
 * @date 2020-04-08
 */
public interface TrackRunTimeCensusMapper 
{
    /**
     * 查询运踪_班列运行时间统计
     * 
     * @param id 运踪_班列运行时间统计ID
     * @return 运踪_班列运行时间统计
     */
    public TrackRunTimeCensus selectTrackRunTimeCensusById(Long id);

    /**
     * 查询运踪_班列运行时间统计列表
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 运踪_班列运行时间统计集合
     */
    public List<TrackRunTimeCensus> selectTrackRunTimeCensusList(TrackRunTimeCensus trackRunTimeCensus);

    /**
     * 新增运踪_班列运行时间统计
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 结果
     */
    public int insertTrackRunTimeCensus(TrackRunTimeCensus trackRunTimeCensus);

    /**
     * 修改运踪_班列运行时间统计
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 结果
     */
    public int updateTrackRunTimeCensus(TrackRunTimeCensus trackRunTimeCensus);

    /**
     * 删除运踪_班列运行时间统计
     * 
     * @param id 运踪_班列运行时间统计ID
     * @return 结果
     */
    public int deleteTrackRunTimeCensusById(Long id);

    /**
     * 批量删除运踪_班列运行时间统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackRunTimeCensusByIds(Long[] ids);

    /**
     * 从班列表查询运行统计时间
     *
     * @param trtcVo 运踪_班列运行时间Vo
     * @return 运踪_班列运行时间统计Vo集合
     */
    public List<TrackRunTimeCensusVo> selectTrtcVoList(TrackRunTimeCensusVo trtcVo);

    /**
     * 根据班列id查询运行统计时间详情
     *
     * @param classId 班列id
     * @return 班列运行时间统计详情
     */
    public TrackRunTimeCensus selectTrtcByClassId(String classId);

    /**
     * 根据编号查询中英文名字
     *
     * @param code 班列id
     * @return 班列运行时间统计详情
     */
    public BusiSiteVo selectBusiSiteVo(String code);
}
