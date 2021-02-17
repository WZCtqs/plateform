package com.szhbl.project.system.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.system.mapper.MessageSetMapper;
import com.szhbl.project.system.domain.MessageSet;
import com.szhbl.project.system.service.IMessageSetService;

/**
 * 消息设置Service业务层处理
 * 
 * @author lzd
 * @date 2020-04-17
 */
@Service
public class MessageSetServiceImpl implements IMessageSetService 
{
    @Autowired
    private MessageSetMapper messageSetMapper;

    /**
     * 查询消息设置
     * 
     * @param id 消息设置ID
     * @return 消息设置
     */
    @Override
    public MessageSet selectMessageSetById(Long id)
    {
        return messageSetMapper.selectMessageSetById(id);
    }

    /**
     * 查询消息设置列表
     * 
     * @param messageSet 消息设置
     * @return 消息设置
     */
    @Override
    public List<MessageSet> selectMessageSetList(MessageSet messageSet)
    {
        return messageSetMapper.selectMessageSetList(messageSet);
    }

    /**
     * 新增消息设置
     * 
     * @param messageSet 消息设置
     * @return 结果
     */
    @Override
    public int insertMessageSet(MessageSet messageSet)
    {
        messageSet.setCreateTime(DateUtils.getNowDate());
        return messageSetMapper.insertMessageSet(messageSet);
    }

    /**
     * 修改消息设置
     * 
     * @param messageSet 消息设置
     * @return 结果
     */
    @Override
    public int updateMessageSet(MessageSet messageSet)
    {
        messageSet.setUpdateTime(DateUtils.getNowDate());
        return messageSetMapper.updateMessageSet(messageSet);
    }

    /**
     * 批量删除消息设置
     * 
     * @param ids 需要删除的消息设置ID
     * @return 结果
     */
    @Override
    public int deleteMessageSetByIds(Long[] ids)
    {
        return messageSetMapper.deleteMessageSetByIds(ids);
    }

    /**
     * 删除消息设置信息
     * 
     * @param id 消息设置ID
     * @return 结果
     */
    @Override
    public int deleteMessageSetById(Long id)
    {
        return messageSetMapper.deleteMessageSetById(id);
    }
}
