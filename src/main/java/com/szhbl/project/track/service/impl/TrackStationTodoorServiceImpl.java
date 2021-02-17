package com.szhbl.project.track.service.impl;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackStationToDoor;
import com.szhbl.project.track.mapper.TrackStationToDoorMapper;
import com.szhbl.project.track.service.ITrackStationToDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 站到门/门到站表Service业务层处理
 *
 * @author szhbl
 *
 */
@Service
public class TrackStationTodoorServiceImpl implements ITrackStationToDoorService
{
    @Autowired
    private TrackStationToDoorMapper trackStationToDoorMapper;

    /**
     * 站到门/门到站表
     *
     * @param tstd 新增数据
     * @return
     */
    @Override
    public AjaxResult addTstd(TrackStationToDoor tstd)  {
        List<TrackStationToDoor> checkList =trackStationToDoorMapper.selectByTstd(tstd);
        if(checkList.size()==0){
            tstd.setCreateTime(new Date());
            int i=  trackStationToDoorMapper.insertTstd(tstd);
            return AjaxResult.success("成功新增"+i+"条数据");
        }else{
            tstd.setUpdateTime(new Date());
            int i=  trackStationToDoorMapper.updateTstd(tstd);
            return AjaxResult.success("成功修改"+i+"条数据");
        }
    }

    /**
     * 站到门/门到站表
     *-
     * @param TrackStationToDoor 查询条件
     * @return--
     * /*+
     */
   /* @Override
    public List<TrackStationToDoor> selectByTa(TrackStationToDoor tstd) {
        return trackStationToDoorMapper.selectByTa(tstd);
    }

    *//**
     * 站到门/门到站修改
     *
     * @param TrackStationToDoor 修改数据
     * @return
     *//*
    @Override
    public AjaxResult updateTa(TrackStationToDoor tstd) {
        List<TrackStationToDoor> checkList =trackStationToDoorMapper.selectByTa(tstd);
        if(checkList.size()==0){
            tstd.setUpdateBy(SecurityUtils.getUsername());
            tstd.setUpdateTime(new Date());
            int i=  trackStationToDoorMapper.updateTa(tstd);
            return AjaxResult.success("成功修改"+i+"条数据");
        }else{
            return AjaxResult.error("中文或英文名字已存在，请重新编辑");
        }
    }

    *//**
     * 站到门/门到站删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteTstd(Long id) {
        TrackStationToDoor tstd =new TrackStationToDoor();
        tstd.setId(id);
        tstd.setDelFlag(1);
        tstd.setUpdateTime(new Date());
        tstd.setUpdateBy(SecurityUtils.getUsername());
        return trackStationToDoorMapper.updateTstd(tstd);
    }
}
