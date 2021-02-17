package com.szhbl.project.backclient.mapper;

import com.szhbl.project.backclient.domain.LoopImage;
import java.util.List;

/**
 * 轮播图Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public interface LoopImageMapper 
{
    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    public LoopImage selectLoopImageById(Long id);

    /**
     * 查询轮播图列表
     * 
     * @param loopImage 轮播图
     * @return 轮播图集合
     */
    public List<LoopImage> selectLoopImageList(LoopImage loopImage);

    /**
     * 新增轮播图
     * 
     * @param loopImage 轮播图
     * @return 结果
     */
    public int insertLoopImage(LoopImage loopImage);

    /**
     * 修改轮播图
     * 
     * @param loopImage 轮播图
     * @return 结果
     */
    public int updateLoopImage(LoopImage loopImage);

    /**
     * 删除轮播图
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    public int deleteLoopImageById(Long id);

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLoopImageByIds(Long[] ids);
}
