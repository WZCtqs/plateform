package com.szhbl.project.system.service.impl;

import com.szhbl.project.system.domain.SysFailMq;
import com.szhbl.project.system.mapper.SysFailMqMapper;
import com.szhbl.project.system.service.ISysFailMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息队列发送失败消息Service业务层处理
 *
 * @author szhbl
 * @date 2020-12-08
 */
@Service
public class SysFailMqServiceImpl implements ISysFailMqService {
    @Autowired
    private SysFailMqMapper sysFailMqMapper;

    /**
     * 查询消息队列发送失败消息
     *
     * @param id 消息队列发送失败消息ID
     * @return 消息队列发送失败消息
     */
    @Override
    public SysFailMq selectSysFailMqById(Long id) {
        return sysFailMqMapper.selectSysFailMqById(id);
    }

    /**
     * 查询消息队列发送失败消息列表
     *
     * @param sysFailMq 消息队列发送失败消息
     * @return 消息队列发送失败消息
     */
    @Override
    public List<SysFailMq> selectSysFailMqList(SysFailMq sysFailMq) {
        return sysFailMqMapper.selectSysFailMqList(sysFailMq);
    }

    /**
     * 新增消息队列发送失败消息
     *
     * @param sysFailMq 消息队列发送失败消息
     * @return 结果
     */
    @Override
    public int insertSysFailMq(SysFailMq sysFailMq) {
        return sysFailMqMapper.insertSysFailMq(sysFailMq);
    }

    /**
     * 修改消息队列发送失败消息
     *
     * @param sysFailMq 消息队列发送失败消息
     * @return 结果
     */
    @Override
    public int updateSysFailMq(SysFailMq sysFailMq) {
        return sysFailMqMapper.updateSysFailMq(sysFailMq);
    }

    /**
     * 批量删除消息队列发送失败消息
     *
     * @param ids 需要删除的消息队列发送失败消息ID
     * @return 结果
     */
    @Override
    public int deleteSysFailMqByIds(Long[] ids) {
        return sysFailMqMapper.deleteSysFailMqByIds(ids);
    }

    /**
     * 删除消息队列发送失败消息信息
     *
     * @param id 消息队列发送失败消息ID
     * @return 结果
     */
    @Override
    public int deleteSysFailMqById(Long id) {
        return sysFailMqMapper.deleteSysFailMqById(id);
    }
}
