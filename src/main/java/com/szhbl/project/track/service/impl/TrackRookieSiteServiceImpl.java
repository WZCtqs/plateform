package com.szhbl.project.track.service.impl;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackRookieSite;
import com.szhbl.project.track.mapper.TrackRookieSiteMapper;
import com.szhbl.project.track.service.ITrackRookieSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 菜鸟站点表Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-08
 */
@Service
public class TrackRookieSiteServiceImpl implements ITrackRookieSiteService
{
    @Autowired
    private TrackRookieSiteMapper trackRookieSiteMapper;

  /**
     * 菜鸟站点表
     * 
     * @param Trs 新增数据
     * @return
     */
    @Override
    public AjaxResult addTrs(TrackRookieSite Trs)  {
        List<TrackRookieSite> checkList =trackRookieSiteMapper.selectByTrs(Trs);
        if(checkList.size()==0){
            Trs.setCreateBy(SecurityUtils.getUsername());
            Trs.setCreateTime(new Date());
            Trs.setUpdateBy(SecurityUtils.getUsername());
            Trs.setUpdateTime(new Date());
            int i=  trackRookieSiteMapper.addTrs(Trs);
            return AjaxResult.success("成功新增"+i+"条数据");
        }else{
            return AjaxResult.error("站点已存在，请重新添加");
        }
    }

    /**
     * 菜鸟站点表
     *
     * @param Trs 查询条件
     * @return
     */
    @Override
    public List<TrackRookieSite> selectByTrs(TrackRookieSite Trs) {
       return trackRookieSiteMapper.selectByTrs(Trs);
    }

 /**
     * 菜鸟站点表
     *
     * @param id 查询条件
     * @return
     */
    @Override
    public TrackRookieSite  selectById(Long id){
       return trackRookieSiteMapper.selectById(id);
    }

    /**
     * 菜鸟站点修改
     *
     * @param Trs 修改数据
     * @return
     */
    @Override
    public AjaxResult updateTrs(TrackRookieSite Trs) {
        List<TrackRookieSite> checkList =trackRookieSiteMapper.selectByTrs(Trs);
        if(checkList.size()==0){
            Trs.setUpdateBy(SecurityUtils.getUsername());
            Trs.setUpdateTime(new Date());
            int i=  trackRookieSiteMapper.updateTrs(Trs);
            return AjaxResult.success("成功修改"+i+"条数据");
        }else{
            return AjaxResult.error("站点已存在，请重新编辑");
        }
    }

    /**
     * 菜鸟站点删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteTa(String id) {
        String ids[]=id.split(",");
        TrackRookieSite Trs =null;
        for(int i=0;i<ids.length;i++){
            Trs =new TrackRookieSite();
            Trs.setId(Long.valueOf(ids[i]));
            Trs.setDelFlag(1);
            Trs.setUpdateTime(new Date());
            Trs.setUpdateBy(SecurityUtils.getUsername());
            trackRookieSiteMapper.updateTrs(Trs);
        }
        return ids.length;
    }
}
