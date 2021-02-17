package com.szhbl.project.track.mapper;

import com.szhbl.project.documentcenter.domain.vo.TrackAbroadTimeVO;
import com.szhbl.project.track.domain.TrackTwoLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运踪二级节点Mapper接口
 *
 * @author lzd
 * @date 2020-03-23
 */
public interface TrackTwoLevelMapper {
    /**
     * 查询运踪二级节点
     *
     * @param id 运踪二级节点ID
     * @return 运踪二级节点
     */
    public TrackTwoLevel selectTrackTwoLevelById(Long id);

    /**
     * 查询运踪二级节点列表
     *
     * @param trackTwoLevel 运踪二级节点
     * @return 运踪二级节点集合
     */
    public List<TrackTwoLevel> selectTrackTwoLevelList(TrackTwoLevel trackTwoLevel);

    /**
     * 新增运踪二级节点
     *
     * @param trackTwoLevel 运踪二级节点
     * @return 结果
     */
    public int insertTrackTwoLevel(TrackTwoLevel trackTwoLevel);

    /**
     * 修改运踪二级节点
     *
     * @param trackTwoLevel 运踪二级节点
     * @return 结果
     */
    public int updateTrackTwoLevel(TrackTwoLevel trackTwoLevel);

    /**
     * 删除运踪二级节点
     *
     * @param id 运踪二级节点ID
     * @return 结果
     */
    public int deleteTrackTwoLevelById(Long id);

    //根据orderid删除
    public int deleteTrackTwoLevelByOrderId(String orderId);

    /**
     * 批量删除运踪二级节点
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackTwoLevelByIds(Long[] ids);

    //根据orderId获取所有二级节点，传参数orderId
    public List<TrackTwoLevel> detailByOrderId(String orderId);

    //根据orderid和sort获取二级节点id
    public Long selectTwoId(TrackTwoLevel trackTwoLevel);

    TrackTwoLevel selectTrackTwoLevelByOrderIdAndName(@Param("orderId") String orderId, @Param("nameZh") String nameZh);

    List<TrackAbroadTimeVO> selectAbroadTimeByOrderId(String orderId);
}
