package com.szhbl.project.monitor.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.mapper.SysMessageMapper;
import com.szhbl.project.monitor.service.ISysMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息提醒Service业务层处理
 *
 * @author szhbl
 * @date 2020-04-07
 */
@Service
public class SysMessageServiceImpl implements ISysMessageService
{
    @Resource
    private SysMessageMapper messageMapper;

    /**
     * 查询消息提醒
     *
     * @param messageId 消息提醒ID
     * @return 消息提醒
     */
    @Override
    public SysMessage selectMessageById(String messageId)
    {
        return messageMapper.selectMessageById(messageId);
    }

    /**
     * 查询消息提醒列表
     *
     * @param message 消息提醒
     * @return 消息提醒
     */
    @Override
    public List<SysMessage> selectMessageList(SysMessage message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 新增消息提醒
     *
     * @param message 消息提醒
     * @return 结果
     */
    @Override
    public int insertMessage(SysMessage message)
    {
        message.setCreateTime(DateUtils.getNowDate());
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改消息提醒
     *
     * @param message 消息提醒
     * @return 结果
     */
    @Override
    public int updateMessage(SysMessage message)
    {
        return messageMapper.updateMessage(message);
    }
    /**
     * 批量删除消息提醒
     *
     * @param messageIds 需要删除的消息提醒ID
     * @return 结果
     */
    @Override
    public int deleteMessageByIds(String[] messageIds)
    {
        return messageMapper.deleteMessageByIds(messageIds);
    }

    /**
     * 删除消息提醒信息
     *
     * @param messageId 消息提醒ID
     * @return 结果
     */
    @Override
    public int deleteMessageById(String messageId) {
        return messageMapper.deleteMessageById(messageId);
    }

    @Override
    public List<SysMessage> selectMessageRecords(SysMessage message) {
        return messageMapper.selectMessageRecords(message);
    }

    @Override
    public int countOrderFileKey(String orderNumber, String fileTypeKey) {
        return messageMapper.countOrderFileKey(orderNumber, fileTypeKey);
    }

    @Override
    public int deleteFileMsgLikeOrderNumber(String orderNumber) {
        return messageMapper.deleteFileMsgLikeOrderNumber(orderNumber);
    }
}
