package com.szhbl.project.monitor.service;

import com.szhbl.project.monitor.domain.SysMessage;

import java.util.List;

/**
 * 消息提醒Service接口
 *
 * @author szhbl
 * @date 2020-04-07
 */
public interface ISysMessageService
{
    /**
     * 查询消息提醒
     *
     * @param messageId 消息提醒ID
     * @return 消息提醒
     */
    public SysMessage selectMessageById(String messageId);

    /**
     * 查询消息提醒列表
     *
     * @param message 消息提醒
     * @return 消息提醒集合
     */
    public List<SysMessage> selectMessageList(SysMessage message);

    /**
     * 新增消息提醒
     *
     * @param message 消息提醒
     * @return 结果
     */
    public int insertMessage(SysMessage message);

    /**
     * 修改消息提醒
     *
     * @param message 消息提醒
     * @return 结果
     */
    public int updateMessage(SysMessage message);

    /**
     * 批量删除消息提醒
     *
     * @param messageIds 需要删除的消息提醒ID
     * @return 结果
     */
    public int deleteMessageByIds(String[] messageIds);

    /**
     * 删除消息提醒信息
     *
     * @param messageId 消息提醒ID
     * @return 结果
     */
    public int deleteMessageById(String messageId);

    public List<SysMessage> selectMessageRecords(SysMessage message);

    int countOrderFileKey(String orderNumber, String fileTypeKey);

    int deleteFileMsgLikeOrderNumber(String orderNumber);
}
