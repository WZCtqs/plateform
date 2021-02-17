package com.szhbl.project.monitor.mapper;

import com.szhbl.project.monitor.domain.SysMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息提醒Mapper接口
 *
 * @author szhbl
 * @date 2020-04-07
 */
public interface SysMessageMapper {
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
     * 删除消息提醒
     *
     * @param messageId 消息提醒ID
     * @return 结果
     */
    public int deleteMessageById(String messageId);

    /**
     * 批量删除消息提醒
     *
     * @param messageIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMessageByIds(String[] messageIds);

    public List<SysMessage> selectMessageRecords(SysMessage message);

    int countOrderFileKey(@Param("orderNumber") String orderNumber, @Param("fileTypeKey") String fileTypeKey);

    int deleteFileMsgLikeOrderNumber(String orderNumber);
}
