package com.szhbl.project.track.service;

import com.szhbl.project.track.domain.TrackTrain;
import com.szhbl.project.track.domain.vo.TrackTrainVo;

import java.util.List;
import java.util.Map;

/**
 * 运踪_班列站到站Service接口
 *
 * @author szhbl
 * @date 2020-03-16
 */
public interface ITrackTrainService
{
    /**
     * 查询运踪_班列站到站
     *
     * @param id 运踪_班列站到站ID
     * @return 运踪_班列站到站
     */
    public TrackTrain selectTrackTrainById(Integer id);

    /**
     * 查询运踪_班列站到站列表
     *
     * @param trackTrain 运踪_班列站到站
     * @return 运踪_班列站到站集合
     */
    public List<TrackTrain> selectTrackTrainList(TrackTrain trackTrain);

    //查询导出列表
    public List<TrackTrain> selectExportTrainList(TrackTrain trackTrain);

    /**
     * 查询运踪_班列表
     *
     * @param trainVo 运踪_班列表
     * @return 运踪_班列表集合
     */
    public List<TrackTrainVo> selectTrainsList(TrackTrainVo trainVo);

    /**
     * 新增运踪_班列站到站
     *
     * @param trackTrain 运踪_班列站到站
     * @return 结果
     */
    public int insertTrackTrain(TrackTrain trackTrain);

    /**
     * 修改运踪_班列站到站
     *
     * @param trackTrain 运踪_班列站到站
     * @return 结果
     */
    public int updateTrackTrain(TrackTrain trackTrain);

    /**
     * 批量删除运踪_班列站到站
     *
     * @param ids 需要删除的运踪_班列站到站ID
     * @return 结果
     */
    public int deleteTrackTrainByIds(Integer[] ids);

    /**
     * 删除运踪_班列站到站信息
     *
     * @param id 运踪_班列站到站ID
     * @return 结果
     */
    public int deleteTrackTrainById(Integer id);

    //新增保存发送全部或者发送VIP
    public int addAndSend(TrackTrain trackTrain);

    //编辑保存发送全部或者发送VIP
    public int editAndSend(TrackTrain trackTrain);

    /**
     * 查询关务运踪_班列站到站列表
     */
    public Map<String, Object> selectGwTrackTrainList(String orderNum);
}
