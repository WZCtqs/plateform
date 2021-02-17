package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.DocOrderUnpackingagent;
import com.szhbl.project.documentcenter.mapper.DocOrderUnpackingagentMapper;
import com.szhbl.project.documentcenter.service.IDocOrderUnpackingagentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 拆箱代理数据Service业务层处理
 *
 * @author szhbl
 * @date 2020-06-08
 */
@Service
public class DocOrderUnpackingagentServiceImpl implements IDocOrderUnpackingagentService
{
    @Autowired
    private DocOrderUnpackingagentMapper docOrderUnpackingagentMapper;

    /**
     * 查询拆箱代理数据列表
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 拆箱代理数据
     */
    @Override
    public List<DocOrderUnpackingagent> selectDocOrderUnpackingagentList(DocOrderUnpackingagent docOrderUnpackingagent) {
        return docOrderUnpackingagentMapper.selectDocOrderUnpackingagentList(docOrderUnpackingagent);
    }

    @Override
    public DocOrderUnpackingagent selectOrderUnpackingagentByOrderId(String orderId) {
        return docOrderUnpackingagentMapper.selectOrderUnpackingagentByOrderId(orderId);
    }

    /**
     * 新增拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    @Override
    public int insertDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent) {
        return docOrderUnpackingagentMapper.insertDocOrderUnpackingagent(docOrderUnpackingagent);
    }

    @Override
    public int insertDocOrderUnpackingagentMatch(List<DocOrderUnpackingagent> docOrderLists) {
        return docOrderUnpackingagentMapper.insertDocOrderUnpackingagentMatch(docOrderLists);
    }

    /**
     * 修改拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    @Override
    public int updateDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent)
    {
        return docOrderUnpackingagentMapper.updateDocOrderUnpackingagent(docOrderUnpackingagent);
    }

    @Override
    public int deleteDocOrderUnpackingagentByOrderId(String orderId) {
        return docOrderUnpackingagentMapper.deleteDocOrderUnpackingagentByOrderId(orderId);
    }
}
