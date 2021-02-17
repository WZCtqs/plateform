package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.TrackGoodsStatusshMapper;
import com.szhbl.project.customerservice.domain.TrackGoodsStatussh;
import com.szhbl.project.customerservice.service.ITrackGoodsStatusshService;

/**
 * 运踪_货物状态--以舱位号为单位，标记是否发车Service业务层处理
 *
 * @author b
 * @date 2020-03-28
 */
@Service
public class TrackGoodsStatusshServiceImpl implements ITrackGoodsStatusshService
{
    @Autowired
    private TrackGoodsStatusshMapper trackGoodsStatusshMapper;

    /**
     * 查询运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param id 运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 运踪_货物状态--以舱位号为单位，标记是否发车
     */
    @Override
    public TrackGoodsStatussh selectTrackGoodsStatusshById(Integer id)
    {
        return trackGoodsStatusshMapper.selectTrackGoodsStatusshById(id);
    }

    /**
     * 查询运踪_货物状态--以舱位号为单位，标记是否发车列表
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 运踪_货物状态--以舱位号为单位，标记是否发车
     */
    @Override
    public List<TrackGoodsStatussh> selectTrackGoodsStatusshList(TrackGoodsStatussh trackGoodsStatussh)
    {
        return trackGoodsStatusshMapper.selectTrackGoodsStatusshList(trackGoodsStatussh);
    }

    /**
     * 新增运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 结果
     */
    @Override
    public int insertTrackGoodsStatussh(TrackGoodsStatussh trackGoodsStatussh)
    {
        trackGoodsStatussh.setCreateTime(DateUtils.getNowDate());
        return trackGoodsStatusshMapper.insertTrackGoodsStatussh(trackGoodsStatussh);
    }

    /**
     * 修改运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param trackGoodsStatussh 运踪_货物状态--以舱位号为单位，标记是否发车
     * @return 结果
     */
    @Override
    public int updateTrackGoodsStatussh(TrackGoodsStatussh trackGoodsStatussh)
    {
        trackGoodsStatussh.setUpdateTime(DateUtils.getNowDate());
        return trackGoodsStatusshMapper.updateTrackGoodsStatussh(trackGoodsStatussh);
    }

    /**
     * 批量删除运踪_货物状态--以舱位号为单位，标记是否发车
     *
     * @param ids 需要删除的运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 结果
     */
    @Override
    public int deleteTrackGoodsStatusshByIds(Integer[] ids)
    {
        return trackGoodsStatusshMapper.deleteTrackGoodsStatusshByIds(ids);
    }

    /**
     * 删除运踪_货物状态--以舱位号为单位，标记是否发车信息
     *
     * @param id 运踪_货物状态--以舱位号为单位，标记是否发车ID
     * @return 结果
     */
    @Override
    public int deleteTrackGoodsStatusshById(Integer id)
    {
        return trackGoodsStatusshMapper.deleteTrackGoodsStatusshById(id);
    }

    @Override
    public List<String> selectBoxNum(String orderId) {
        return trackGoodsStatusshMapper.selectBoxNum(orderId);
    }

    @Override
    public List<String> findClassDate(String actualClassId){
        return trackGoodsStatusshMapper.findClassDate(actualClassId);
    }
}
