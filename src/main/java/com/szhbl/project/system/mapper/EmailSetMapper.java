package com.szhbl.project.system.mapper;

import com.szhbl.project.system.domain.EmailSet;
import java.util.List;

/**
 * 邮件配置Mapper接口
 * 
 * @author lzd
 * @date 2020-04-17
 */
public interface EmailSetMapper 
{
    /**
     * 查询邮件配置
     * 
     * @param id 邮件配置ID
     * @return 邮件配置
     */
    public EmailSet selectEmailSetById(Long id);

    /**
     * 查询邮件配置列表
     * 
     * @param emailSet 邮件配置
     * @return 邮件配置集合
     */
    public List<EmailSet> selectEmailSetList(EmailSet emailSet);

    /**
     * 新增邮件配置
     * 
     * @param emailSet 邮件配置
     * @return 结果
     */
    public int insertEmailSet(EmailSet emailSet);

    /**
     * 修改邮件配置
     * 
     * @param emailSet 邮件配置
     * @return 结果
     */
    public int updateEmailSet(EmailSet emailSet);

    /**
     * 删除邮件配置
     * 
     * @param id 邮件配置ID
     * @return 结果
     */
    public int deleteEmailSetById(Long id);

    /**
     * 批量删除邮件配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmailSetByIds(Long[] ids);


    public EmailSet selectEmailSet(EmailSet emailSet);
}
