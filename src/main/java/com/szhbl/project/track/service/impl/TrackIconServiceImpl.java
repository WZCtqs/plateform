package com.szhbl.project.track.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.framework.config.BaiDuMapUtils;
import com.szhbl.framework.web.domain.server.Sys;
import com.szhbl.project.track.domain.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackIconMapper;
import com.szhbl.project.track.domain.TrackIcon;
import com.szhbl.project.track.service.ITrackIconService;

/**
 * 运踪图标Service业务层处理
 * 
 * @author szhbl
 * @date 2020-03-20
 */
@Service
public class TrackIconServiceImpl implements ITrackIconService 
{
    @Autowired
    private TrackIconMapper trackIconMapper;

    /**
     * 查询运踪图标
     * @return 运踪图标
     */
  /*  @Override
    public void inquery()
    {
        List<RegionVo> shijiNameList=trackIconMapper.selectName();
        for(RegionVo shijiName :shijiNameList ){
            String zzDistance=BaiDuMapUtils.getDistance(shijiName.getMername().replaceAll(",","")+"火车站","郑州国际陆港");
            String whDistance=BaiDuMapUtils.getDistance(shijiName.getMername().replaceAll(",","")+"火车站","武汉市火车站");
            if(!"0米".equals(zzDistance)&&!"0米".equals(whDistance)){
                if(zzDistance.endsWith("米")){
                    zzDistance=new BigDecimal(zzDistance.replaceAll("米","")).divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"公里";
                }
                if(whDistance.endsWith("米")){
                    whDistance=new BigDecimal(whDistance.replaceAll("米","")).divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"公里";
                }
                shijiName.setZzDistance(zzDistance);
                System.out.println("zzDistance"+zzDistance);
                shijiName.setWhDistance(whDistance);
                System.out.println("whDistance"+whDistance);
                shijiName.setZzFees(new BigDecimal(zzDistance.replaceAll("公里","")).multiply(new BigDecimal(1.5)).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                System.out.println("ZzFees"+shijiName.getZzFees());
                shijiName.setWhFees(new BigDecimal(whDistance.replaceAll("公里","")).multiply(new BigDecimal(1.5)).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                System.out.println("WhFees"+shijiName.getWhFees());
                int i=trackIconMapper.updateRegion(shijiName);
                System.out.println(i);
            }
        }
    }*/


    /**
     * 查询运踪图标
     * 
     * @param id 运踪图标ID
     * @return 运踪图标
     */
    @Override
    public TrackIcon selectTrackIconById(Long id)
    {
        return trackIconMapper.selectTrackIconById(id);
    }

    /**
     * 查询运踪图标列表
     * 
     * @param trackIcon 运踪图标
     * @return 运踪图标
     */
    @Override
    public List<TrackIcon> selectTrackIconList(TrackIcon trackIcon)
    {
        return trackIconMapper.selectTrackIconList(trackIcon);
    }

    /**
     * 新增运踪图标
     * 
     * @param trackIcon 运踪图标
     * @return 结果
     */
    @Override
    public int insertTrackIcon(TrackIcon trackIcon)
    {
        trackIcon.setCreateTime(DateUtils.getNowDate());
        return trackIconMapper.insertTrackIcon(trackIcon);
    }

    /**
     * 修改运踪图标
     * 
     * @param trackIcon 运踪图标
     * @return 结果
     */
    @Override
    public int updateTrackIcon(TrackIcon trackIcon)
    {
        trackIcon.setUpdateTime(DateUtils.getNowDate());
        return trackIconMapper.updateTrackIcon(trackIcon);
    }

    /**
     * 批量删除运踪图标
     * 
     * @param ids 需要删除的运踪图标ID
     * @return 结果
     */
    @Override
    public int deleteTrackIconByIds(Long[] ids)
    {
        return trackIconMapper.deleteTrackIconByIds(ids);
    }

    /**
     * 删除运踪图标信息
     * 
     * @param id 运踪图标ID
     * @return 结果
     */
    @Override
    public int deleteTrackIconById(Long id)
    {
        return trackIconMapper.deleteTrackIconById(id);
    }
}
