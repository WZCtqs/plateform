package com.szhbl.project.track.service.impl;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackAddress;
import com.szhbl.project.track.mapper.TrackAddressMapper;
import com.szhbl.project.track.service.ITrackAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 位置地名表Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-08
 */
@Service
public class TrackAddressServiceImpl implements ITrackAddressService
{
    @Autowired
    private TrackAddressMapper trackAddressMapper;

  /**
     * 位置地名表
     * 
     * @param trackAddress 新增数据
     * @return
     */
    @Override
    public AjaxResult addTa(TrackAddress trackAddress)  {
        trackAddress.setSelectType(0);
        List<TrackAddress> checkList =trackAddressMapper.selectByTa(trackAddress);
        if(checkList.size()==0){
            //trackAddress.setCreateBy(SecurityUtils.getUsername());
            trackAddress.setCreateTime(new Date());
            int i=  trackAddressMapper.addTa(trackAddress);
            return AjaxResult.success("成功新增"+i+"条数据");
        }else{
            return AjaxResult.error("中文或英文名字已存在，请重新添加");
        }
    }

    /**
     * 位置地名表
     *
     * @param trackAddress 查询条件
     * @return
     */
    @Override
    public List<TrackAddress> selectByTa(TrackAddress trackAddress) {
       return trackAddressMapper.selectByTa(trackAddress);
    }

    /**
     * 根据id查询位置地名
     *
     * @param id 查询条件
     * @return AjaxResult
     */
    @Override
    public TrackAddress selectById(Long id)
    {
        return trackAddressMapper.selectById(id);
    }

    /**
     * 根据中文名查询位置地名
     *
     * @param name 查询条件
     * @return AjaxResult
     */
    @Override
    public AjaxResult selectByName(String name)
    {
        if(name.matches("[\u4E00-\u9FA5]+")){//全是中文
            TrackAddress trackAddress= trackAddressMapper.selectByName(name);
            if(trackAddress==null){
                return AjaxResult.error("未查询到此地名对应英文名");
            }else{
                return AjaxResult.success("成功查询到此地名对应英文名");
            }
        }
        else{
            return AjaxResult.success("此地名不全为中文");
        }
    }


    /**
     * 位置地名修改
     *
     * @param trackAddress 修改数据
     * @return
     */
    @Override
    public AjaxResult updateTa(TrackAddress trackAddress) {
        trackAddress.setSelectType(0);
        List<TrackAddress> checkList =trackAddressMapper.selectByTa(trackAddress);
        if(checkList.size()==0){
            trackAddress.setUpdateBy(SecurityUtils.getUsername());
            trackAddress.setUpdateTime(new Date());
            int i=  trackAddressMapper.updateTa(trackAddress);
            return AjaxResult.success("成功修改"+i+"条数据");
        }else{
            return AjaxResult.error("中文或英文名字已存在，请重新编辑");
        }
    }

    /**
     * 位置地名删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteTa(Long[] ids) {
        TrackAddress trackAddress=null;
        for(int i=0;i<ids.length;i++){
            trackAddress =new TrackAddress();
            trackAddress.setId(ids[i]);
            trackAddress.setDelFlag(1);
            trackAddress.setUpdateTime(new Date());
            trackAddress.setUpdateBy(SecurityUtils.getUsername());
            trackAddressMapper.updateTa(trackAddress);
        }
        return ids.length;
    }
}
