package com.szhbl.project.backclient.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.backclient.domain.AboutUs;
import com.szhbl.project.backclient.mapper.AboutUsMapper;
import com.szhbl.project.backclient.service.IAboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关于我们Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-14
 */
@Service
public class AboutUsServiceImpl implements IAboutUsService 
{
    @Autowired
    private AboutUsMapper aboutUsMapper;

    /**
     * 查询关于我们
     * 
     * @param id 关于我们ID
     * @return 关于我们
     */
    @Override
    public AboutUs selectAboutUsById(Long id)
    {
        return aboutUsMapper.selectAboutUsById(id);
    }

    /**
     * 查询关于我们列表
     * 
     * @param aboutUs 关于我们
     * @return 关于我们
     */
    @Override
    public List<AboutUs> selectAboutUsList(AboutUs aboutUs)
    {
        return aboutUsMapper.selectAboutUsList(aboutUs);
    }

    /**
     * 新增关于我们
     * 
     * @param aboutUs 关于我们
     * @return 结果
     */
    @Override
    public int insertAboutUs(AboutUs aboutUs)
    {
        aboutUs.setCreateTime(DateUtils.getNowDate());
        return aboutUsMapper.insertAboutUs(aboutUs);
    }

    /**
     * 修改关于我们
     * 
     * @param aboutUs 关于我们
     * @return 结果
     */
    @Override
    public int updateAboutUs(AboutUs aboutUs)
    {
        aboutUs.setUpdateTime(DateUtils.getNowDate());
        return aboutUsMapper.updateAboutUs(aboutUs);
    }

    /**
     * 批量删除关于我们
     * 
     * @param ids 需要删除的关于我们ID
     * @return 结果
     */
    @Override
    public int deleteAboutUsByIds(Long[] ids)
    {
        return aboutUsMapper.deleteAboutUsByIds(ids);
    }

    /**
     * 删除关于我们信息
     * 
     * @param id 关于我们ID
     * @return 结果
     */
    @Override
    public int deleteAboutUsById(Long id)
    {
        return aboutUsMapper.deleteAboutUsById(id);
    }
}
