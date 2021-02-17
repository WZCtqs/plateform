package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.LoopImageMapper;
import com.szhbl.project.backclient.domain.LoopImage;
import com.szhbl.project.backclient.service.ILoopImageService;

/**
 * 轮播图Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@Service
public class LoopImageServiceImpl implements ILoopImageService 
{
    @Autowired
    private LoopImageMapper loopImageMapper;

    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    @Override
    public LoopImage selectLoopImageById(Long id)
    {
        return loopImageMapper.selectLoopImageById(id);
    }

    /**
     * 查询轮播图列表
     * 
     * @param loopImage 轮播图
     * @return 轮播图
     */
    @Override
    public List<LoopImage> selectLoopImageList(LoopImage loopImage)
    {
        return loopImageMapper.selectLoopImageList(loopImage);
    }

    /**
     * 新增轮播图
     * 
     * @param loopImage 轮播图
     * @return 结果
     */
    @Override
    public int insertLoopImage(LoopImage loopImage)
    {
        loopImage.setCreateTime(DateUtils.getNowDate());
        return loopImageMapper.insertLoopImage(loopImage);
    }

    /**
     * 修改轮播图
     * 
     * @param loopImage 轮播图
     * @return 结果
     */
    @Override
    public int updateLoopImage(LoopImage loopImage)
    {
        loopImage.setUpdateTime(DateUtils.getNowDate());
        return loopImageMapper.updateLoopImage(loopImage);
    }

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的轮播图ID
     * @return 结果
     */
    @Override
    public int deleteLoopImageByIds(Long[] ids)
    {
        return loopImageMapper.deleteLoopImageByIds(ids);
    }

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    @Override
    public int deleteLoopImageById(Long id)
    {
        return loopImageMapper.deleteLoopImageById(id);
    }
}
