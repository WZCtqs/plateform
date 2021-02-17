package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrderUnpackingagent;

import java.util.List;

/**
 * 拆箱代理数据Service接口
 *
 * @author szhbl
 * @date 2020-06-08
 */
public interface IDocOrderUnpackingagentService {

    /**
     * 查询拆箱代理数据列表
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 拆箱代理数据集合
     */
    public List<DocOrderUnpackingagent> selectDocOrderUnpackingagentList(DocOrderUnpackingagent docOrderUnpackingagent);


    public DocOrderUnpackingagent selectOrderUnpackingagentByOrderId(String orderId);

    /**
     * 新增拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    public int insertDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent);

    public int insertDocOrderUnpackingagentMatch(List<DocOrderUnpackingagent> docOrderLists);


    /**
     * 修改拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    public int updateDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent);

    /**
     * 删除拆箱代理数据信息
     *
     * @param id 拆箱代理数据ID
     * @return 结果
     */
    public int deleteDocOrderUnpackingagentByOrderId(String orderId);
}
