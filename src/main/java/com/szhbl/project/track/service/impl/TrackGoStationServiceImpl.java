package com.szhbl.project.track.service.impl;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.system.domain.SysRole;
import com.szhbl.project.track.domain.TrackGoStation;
import com.szhbl.project.track.domain.vo.TrackIntransitMailVo;
import com.szhbl.project.track.mapper.TrackGoStationMapper;
import com.szhbl.project.track.service.ITrackGoStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 去程整柜场站地址Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-08
 */
@Service
public class TrackGoStationServiceImpl implements ITrackGoStationService
{
    @Autowired
    private TrackGoStationMapper trackGoStationMapper;

  /**
     * 去程整柜场站地址
     * 
     * @param file 导入文件
     * @return
     */
    @Override
    public AjaxResult importData(MultipartFile file)  throws Exception {
        ExcelUtil<TrackGoStation> excelUtil = new ExcelUtil<>(TrackGoStation.class);
        List<TrackGoStation> list = excelUtil.importExcel(file.getInputStream());
        //获取班列编号
        List<String> classNumList=new ArrayList<>();
        for(int n=0;n<list.size();n++){
            if(StringUtils.isNotEmpty(list.get(n).getClassNum())&&!classNumList.contains(list.get(n).getClassNum())){
                classNumList.add(list.get(n).getClassNum());
            }
        }
        //删除班列编号的车站地址
        for(int j=0;j<classNumList.size();j++){
            trackGoStationMapper.deleteByClassNum(classNumList.get(j));
        }
        int total=list.size();
        int success=0;
        TrackGoStation tgn=null;
        for(int i=0;i<total;i++){
            TrackGoStation Tgn=list.get(i);
            tgn=new TrackGoStation();
            tgn.setClassNum(Tgn.getClassNum());
            tgn.setDownStation(Tgn.getDownStation());
            List<TrackGoStation> checkTgnList=trackGoStationMapper.selectByTgn(tgn);
            if(checkTgnList.size()==1){
                Tgn.setUpdateTime(new Date());
                Tgn.setId(checkTgnList.get(0).getId());
                int update=trackGoStationMapper.updateTgn(Tgn);
                success+=update;
            }else{
                Tgn.setCreateTime(new Date());
                Tgn.setUpdateTime(new Date());
                int insert= trackGoStationMapper.insertTgn(Tgn);
                success+=insert;
            }
        }
        if(total==success){
            if(total==0){
                return AjaxResult.error("无数据可导入，请重新选择文件");
            }else{
                return  AjaxResult.success("成功导入"+total+"条数据");
            }
        }else{
            return AjaxResult.error((total-success)+"条数据导入失败");
        }
    }

    /**
     * 去程整柜场站地址查询
     *
     * @param Tgn 查询条件
     * @return
     */
    @Override
    public List<TrackGoStation> selectByTgn(TrackGoStation Tgn) {
       return trackGoStationMapper.selectByTgn(Tgn);
    }

    /**
     * 根据id查询去程整柜场站地址数据
     *
     * @param id 查询条件
     * @return AjaxResult
     */
    @Override
    public TrackGoStation selectById(Long id)
    {
        return trackGoStationMapper.selectById(id);
    }

    /**
     *去程整柜场站地址修改
     *
     * @param Tgn 查询条件
     * @return
     */
    @Override
    public int updateTgn(TrackGoStation Tgn) {
        Tgn.setUpdateTime(new Date());
        return trackGoStationMapper.updateTgn(Tgn);
    }

    @Override
    public int insertTgn(TrackGoStation Tgn) {
        Tgn.setUpdateTime(new Date());
        return trackGoStationMapper.insertTgn(Tgn);
    }

    /**
     * 在途邮箱查询
     *
     * @param timVo 查询条件
     * @return
     */
    @Override
    public  List<TrackIntransitMailVo> selectByTimVo(TrackIntransitMailVo timVo) {
        return trackGoStationMapper.selectByTimVo(timVo);
    }

    /**
     *在途邮箱修改
     *
     * @param timVo 查询条件
     * @return
     */
    @Override
    public int updateTimVo(TrackIntransitMailVo timVo) {
        return trackGoStationMapper.updateTimVo(timVo);
    }
}
