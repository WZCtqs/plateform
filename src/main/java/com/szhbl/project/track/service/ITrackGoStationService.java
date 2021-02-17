package com.szhbl.project.track.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackGoStation;
import com.szhbl.project.track.domain.vo.TrackIntransitMailVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 去程整柜场站地址Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface ITrackGoStationService
{
   /**
     * 去程整柜场站地址数据导入
     * 
     * @param file 导入文件
     * @return AjaxResult
     */
    public AjaxResult importData(MultipartFile file)  throws Exception;

    /**
     * 去程整柜场站地址数据查询
     *
     * @param Tgn 查询条件
     * @return AjaxResult
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
     * 去程整柜场站地址数据修改
     *
     * @param Tgn 修改条件
     * @return AjaxResult
     */
    public int updateTgn(TrackGoStation Tgn);

    public int insertTgn(TrackGoStation Tgn);

    /**
     * 在途邮箱查询
     *
     * @param timVo 查询条件
     * @return AjaxResult
     */
    List<TrackIntransitMailVo> selectByTimVo(TrackIntransitMailVo timVo);

    /**
     * 在途邮箱数据修改
     *
     * @param timVo 修改条件
     * @return AjaxResult
     */
    public int updateTimVo(TrackIntransitMailVo timVo);
}
