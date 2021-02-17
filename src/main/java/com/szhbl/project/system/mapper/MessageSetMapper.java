package com.szhbl.project.system.mapper;

import com.szhbl.project.system.domain.MessageSet;
import java.util.List;

/**
 * 消息设置Mapper接口
 * 
 * @author lzd
 * @date 2020-04-17
 */
public interface MessageSetMapper 
{
    /**
     * 查询消息设置
     * 
     * @param id 消息设置ID
     * @return 消息设置
     */
    public MessageSet selectMessageSetById(Long id);

    /**
     * 查询消息设置列表
     * 
     * @param messageSet 消息设置
     * @return 消息设置集合
     */
    public List<MessageSet> selectMessageSetList(MessageSet messageSet);

    /**
     * 新增消息设置
     * 
     * @param messageSet 消息设置
     * @return 结果
     */
    public int insertMessageSet(MessageSet messageSet);

    /**
     * 修改消息设置
     * 
     * @param messageSet 消息设置
     * @return 结果
     */
    public int updateMessageSet(MessageSet messageSet);

    /**
     * 删除消息设置
     * 
     * @param id 消息设置ID
     * @return 结果
     */
    public int deleteMessageSetById(Long id);

    /**
     * 批量删除消息设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMessageSetByIds(Long[] ids);
}
