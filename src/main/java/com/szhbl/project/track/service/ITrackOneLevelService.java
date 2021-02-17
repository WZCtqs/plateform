package com.szhbl.project.track.service;

import com.szhbl.project.track.domain.TrackOneLevel;
import com.szhbl.project.track.domain.vo.*;

import java.util.List;
import java.util.Map;

/**
 * 运踪一级节点Service接口
 * 
 * @author lzd
 * @date 2020-03-23
 */
public interface ITrackOneLevelService 
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
     * 批量删除运踪一级节点
     * 
     * @param ids 需要删除的运踪一级节点ID
     * @return 结果
     */
    public int deleteTrackOneLevelByIds(Long[] ids);

    /**
     * 删除运踪一级节点信息
     * 
     * @param id 运踪一级节点ID
     * @return 结果
     */
    public int deleteTrackOneLevelById(Long id);
    //根据orderid删除
    public int deleteTrackOneLevelByOrderId(String orderId);


    /**
     * 运踪查询
     *
     * @param trackVo 运踪查询vo
     * @return
     */
    public List<TrackQueryVo> selectTrackList(TrackQueryVo trackVo);

    //根据orderId获取一级运踪
    public Map<String, Object> selectTolByOrderId(String orderId, String boxNum);

    //根据orderId查询发货地和收货地
    public TrackOneLevelVo selectAddrssByOrderId(String orderId);

    //根据classId查询班列运踪
    public Map<String, Object> selectTrainListByClassId(String classId,String orderId,String boxNum);

    //邮件发送记录查询
    public List<EmailRecordsVo> selectEmailRecords(String orderNum);

    //根据sort和orderId查询一级节点id
    public Long selectOneId(TrackOneLevel trackOneLevel);
}
