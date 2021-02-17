package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.TrackGoodsStatussh;
import java.util.List;

/**
 * 运踪_货物状态--以舱位号为单位，标记是否发车Service接口
 *
 * @author b
 * @date 2020-03-28
 */
public interface ITrackGoodsStatusshService
{
    /**
     * 查询运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param id 运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 运踪_货物状态--以舱位号为单位，标记是否发车
     */
    public TrackGoodsStatussh selectTrackGoodsStatusshById(Integer id);

    /**
     * 查询运踪_货物状态--以舱位号为单位，标记是否发车列表
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 运踪_货物状态--以舱位号为单位，标记是否发车集合
     */
    public List<TrackGoodsStatussh> selectTrackGoodsStatusshList(TrackGoodsStatussh trackGoodsStatussh);

    /**
     * 新增运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 结果
     */
    public int insertTrackGoodsStatussh(TrackGoodsStatussh trackGoodsStatussh);

    /**
     * 修改运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 结果
     */
    public int updateTrackGoodsStatussh(TrackGoodsStatussh trackGoodsStatussh);

    /**
     * 批量删除运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param ids 需要删除的运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 结果
     */
    public int deleteTrackGoodsStatusshByIds(Integer[] ids);

    /**
     * 删除运踪_货物状态--以舱位号为单位，标记是否发车信息
     *
     * @param id 运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 结果
     */
    public int deleteTrackGoodsStatusshById(Integer id);

    List<String> selectBoxNum(String orderId );

    List<String> findClassDate(String actualClassId);
}
