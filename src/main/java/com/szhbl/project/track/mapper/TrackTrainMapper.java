package com.szhbl.project.track.mapper;

import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.track.domain.TrackTrain;
import com.szhbl.project.track.domain.vo.GwTrackTrainVo;
import com.szhbl.project.track.domain.vo.TrackTrainVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运踪_班列站到站Mapper接口
 *
 * @author szhbl
 * @date 2020-03-16
 */
public interface TrackTrainMapper
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
     * 删除运踪_班列站到站
     *
     * @param id 运踪_班列站到站ID
     * @return 结果
     */
    public int deleteTrackTrainById(Integer id);

    /**
     * 批量删除运踪_班列站到站
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackTrainByIds(Integer[] ids);


    /**
     * 查询运踪_班列表
     *
     * @param trainVo 运踪_班列表
     * @return 运踪_班列表集合
     */
    public List<TrackTrainVo> selectTrainsList(TrackTrainVo trainVo);

    /**
     * 更新班列表运踪时间
     *
     * @param trackTrain 运踪_班列表
     * @return 运踪_班列表集合
     */
    public int updateClass(TrackTrain trackTrain);

    //获取需要发送邮件的信息
    public List<TrackTrain> getSendMessage(TrackTrain trackTrain);

    //获取需要发送邮件的信息
    public String[] getBoxNum(@Param("orderNum") String orderNum,@Param("actualClassDate")String actualClassDate);

    //查询班列下的仓位
    public List<ShippingOrder> selectShippingOrderByClassId(String classId);

    //获取班列第一条数据
    public Integer getFirstId(String classId);

    //修改班列运踪当天状态
    public Integer updateTrackState(String classId);

    //自动更新每天班列运踪状态
    public Integer autoUpdateTrackState();

    //查询班列预计到站时间
    public String getArriveTime(String classId);

    //查询关务班列运踪
    public List<GwTrackTrainVo> selectGwTrackTrainList(@Param("classId")String classId, @Param("actualDate")String actualDate,
                                                       @Param("batchTime")String batchTime,@Param("abnormalTime")String abnormalTime);

}
