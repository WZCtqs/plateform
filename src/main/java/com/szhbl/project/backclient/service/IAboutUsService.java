package com.szhbl.project.backclient.service;

import com.szhbl.project.backclient.domain.AboutUs;
import java.util.List;

/**
 * 关于我们Service接口
 * 
 * @author szhbl
 * @date 2020-01-14
 */
public interface IAboutUsService 
{
    /**
     * 查询关于我们
     * 
     * @param id 关于我们ID
     * @return 关于我们
     */
    public AboutUs selectAboutUsById(Long id);

    /**
     * 查询关于我们列表
     * 
     * @param aboutUs 关于我们
     * @return 关于我们集合
     */
    public List<AboutUs> selectAboutUsList(AboutUs aboutUs);

    /**
     * 新增关于我们
     * 
     * @param aboutUs 关于我们
     * @return 结果
     */
    public int insertAboutUs(AboutUs aboutUs);

    /**
     * 修改关于我们
     * 
     * @param aboutUs 关于我们
     * @return 结果
     */
    public int updateAboutUs(AboutUs aboutUs);

    /**
     * 批量删除关于我们
     * 
     * @param ids 需要删除的关于我们ID
     * @return 结果
     */
    public int deleteAboutUsByIds(Long[] ids);

    /**
     * 删除关于我们信息
     * 
     * @param id 关于我们ID
     * @return 结果
     */
    public int deleteAboutUsById(Long id);
}
