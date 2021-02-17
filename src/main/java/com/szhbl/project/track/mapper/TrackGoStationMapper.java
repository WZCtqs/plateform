package com.szhbl.project.track.mapper;


import com.szhbl.project.track.domain.TrackGoStation;
import com.szhbl.project.track.domain.vo.TrackIntransitMailVo;

import java.util.List;

/**
 * 去程整柜场站地址Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-08
 */
public interface TrackGoStationMapper
{
    /**
     * 更新去程整柜场站地址
     *
     * @param Tgn 去程整柜场站地址实体类
     * @return
     */
    public int updateTgn(TrackGoStation Tgn);

    /**
     * 新增去程整柜场站地址
     *
     * @param Tgn 去程整柜场站地址实体类
     * @return
     */
    public int insertTgn(TrackGoStation Tgn);

    /**
     * 查询去程整柜场站地址
     *
     * @param Tgn 去程整柜场站地址实体类
     * @return
     */
    public List<TrackGoStation> selectByTgn(TrackGoStation Tgn);

    /**
     * 根据id查询去程整柜场站地址数据
     *
     * @param id 查询条件
     * @return AjaxResult
     */
    public TrackGoStation selectById(Long id);

    /**
     * 查询在途邮箱
     *
     * @param timVo
     * @return
     */
    public List<TrackIntransitMailVo> selectByTimVo(TrackIntransitMailVo timVo);

    /**
     * 更新在途邮箱
     *
     * @param timVo
     * @return
     */
    public int updateTimVo(TrackIntransitMailVo timVo);


    //根据班列编号删除数据
    public int deleteByClassNum(String classNum);

}
