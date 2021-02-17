package com.szhbl.project.track.mapper;

import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.TrackOneLevel;
import com.szhbl.project.track.domain.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运踪一级节点Mapper接口
 * 
 * @author lzd
 * @date 2020-03-23
 */
public interface TrackOneLevelMapper 
{
    /**
     * 查询运踪一级节点
     * 
     * @param id 运踪一级节点ID
     * @return 运踪一级节点
     */
    public TrackOneLevel selectTrackOneLevelById(Long id);

    /**
     * 查询运踪一级节点列表
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 运踪一级节点集合
     */
    public List<TrackOneLevel> selectTrackOneLevelList(TrackOneLevel trackOneLevel);

    /**
     * 新增运踪一级节点
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 结果
     */
    public int insertTrackOneLevel(TrackOneLevel trackOneLevel);

    /**
     * 修改运踪一级节点
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 结果
     */
    public int updateTrackOneLevel(TrackOneLevel trackOneLevel);

    /**
     * 删除运踪一级节点
     * 
     * @param id 运踪一级节点ID
     * @return 结果
     */
    public int deleteTrackOneLevelById(Long id);
    //根据orderid删除
    public int deleteTrackOneLevelByOrderId(String orderId);

    /**
     * 批量删除运踪一级节点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackOneLevelByIds(Long[] ids);


    /**
     * 运踪查询
     *
     * @param trackVo 运踪查询vo
     * @return
     */
    public List<TrackQueryVo> selectTrackList(TrackQueryVo trackVo);

    //根据orderId获取一级运踪
    public List<TrackOneLevel> selectTolByOrderId(String orderId);

    //根据orderId查询发货地和收货地
    public TrackOneLevelVo selectAddrssByOrderId(String orderId);

    //根据classId查询班列运踪
    public List<TrackTrainResultVo> selectTrainListByClassId(@Param("classId")String classId,@Param("actualDate")String actualDate,
                                                             @Param("batchTime")String batchTime,@Param("abnormalTime")String abnormalTime);

    //根据货物状态表查询班列运踪
    public List<TrackTrainResultVo> selectTrainListByTgs(TrackGoodsStatus tgs);

    //根据orderNum查询邮件发送记录
    public List<EmailRecordsVo> selectEmailRecords(@Param("orderNum")String orderNum);

    //根据sort和orderId查询一级节点id
    public Long selectOneId(TrackOneLevel trackOneLevel);

    //根据订单id获取最新运踪
    public TrackOneLevel getMaxTol(String orderId);

    //更新运踪
    public int updateOrder(TrackOneLevel trackOneLevel);
    public List<String>  getorderid();
    public List<Long>  selectOneIdList(TrackOneLevel trackOneLevel);
}
