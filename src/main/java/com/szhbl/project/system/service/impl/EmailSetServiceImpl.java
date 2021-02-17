package com.szhbl.project.system.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.system.mapper.EmailSetMapper;
import com.szhbl.project.system.domain.EmailSet;
import com.szhbl.project.system.service.IEmailSetService;

/**
 * 邮件配置Service业务层处理
 * 
 * @author lzd
 * @date 2020-04-17
 */
@Service
public class EmailSetServiceImpl implements IEmailSetService 
{
    @Autowired
    private EmailSetMapper emailSetMapper;

    /**
     * 查询邮件配置
     * 
     * @param id 邮件配置ID
     * @return 邮件配置
     */
    @Override
    public EmailSet selectEmailSetById(Long id)
    {
        return emailSetMapper.selectEmailSetById(id);
    }

    /**
     * 查询邮件配置列表
     * 
     * @param emailSet 邮件配置
     * @return 邮件配置
     */
    @Override
    public List<EmailSet> selectEmailSetList(EmailSet emailSet)
    {
        return emailSetMapper.selectEmailSetList(emailSet);
    }

    /**
     * 新增邮件配置
     * 
     * @param emailSet 邮件配置
     * @return 结果
     */
    @Override
    public int insertEmailSet(EmailSet emailSet)
    {
        emailSet.setCreateTime(DateUtils.getNowDate());
        return emailSetMapper.insertEmailSet(emailSet);
    }

    /**
     * 修改邮件配置
     * 
     * @param emailSet 邮件配置
     * @return 结果
     */
    @Override
    public int updateEmailSet(EmailSet emailSet)
    {
        emailSet.setUpdateTime(DateUtils.getNowDate());
        return emailSetMapper.updateEmailSet(emailSet);
    }

    /**
     * 批量删除邮件配置
     * 
     * @param ids 需要删除的邮件配置ID
     * @return 结果
     */
    @Override
    public int deleteEmailSetByIds(Long[] ids)
    {
        return emailSetMapper.deleteEmailSetByIds(ids);
    }

    /**
     * 删除邮件配置信息
     * 
     * @param id 邮件配置ID
     * @return 结果
     */
    @Override
    public int deleteEmailSetById(Long id)
    {
        return emailSetMapper.deleteEmailSetById(id);
    }
}
