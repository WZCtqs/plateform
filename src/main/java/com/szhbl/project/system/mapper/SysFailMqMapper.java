package com.szhbl.project.system.mapper;

import com.szhbl.project.system.domain.SysFailMq;
import java.util.List;

/**
 * 消息队列发送失败消息Mapper接口
 * 
 * @author szhbl
 * @date 2020-12-08
 */
public interface SysFailMqMapper 
{
    /**
     * 查询消息队列发送失败消息
     * 
     * @param id 消息队列发送失败消息ID
     * @return 消息队列发送失败消息
     */
    public SysFailMq selectSysFailMqById(Long id);

    /**
     * 查询消息队列发送失败消息列表
     * 
     * @param sysFailMq 消息队列发送失败消息
     * @return 消息队列发送失败消息集合
     */
    public List<SysFailMq> selectSysFailMqList(SysFailMq sysFailMq);

    /**
     * 新增消息队列发送失败消息
     * 
     * @param sysFailMq 消息队列发送失败消息
     * @return 结果
     */
    public int insertSysFailMq(SysFailMq sysFailMq);

    /**
     * 修改消息队列发送失败消息
     * 
     * @param sysFailMq 消息队列发送失败消息
     * @return 结果
     */
    public int updateSysFailMq(SysFailMq sysFailMq);

    /**
     * 删除消息队列发送失败消息
     * 
     * @param id 消息队列发送失败消息ID
     * @return 结果
     */
    public int deleteSysFailMqById(Long id);

    /**
     * 批量删除消息队列发送失败消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFailMqByIds(Long[] ids);
}
