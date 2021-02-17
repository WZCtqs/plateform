package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderUnpackingagent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拆箱代理数据Mapper接口
 *
 * @author szhbl
 * @date 2020-06-08
 */
public interface DocOrderUnpackingagentMapper
{

    /**
     * 查询拆箱代理数据列表
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 拆箱代理数据集合
     */
    public List<DocOrderUnpackingagent> selectDocOrderUnpackingagentList(DocOrderUnpackingagent docOrderUnpackingagent);

    /**
     * 新增拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    public int insertDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent);

    public int insertDocOrderUnpackingagentMatch(@Param("docOrderList") List<DocOrderUnpackingagent> docOrderList);

    /**
     * 修改拆箱代理数据
     *
     * @param docOrderUnpackingagent 拆箱代理数据
     * @return 结果
     */
    public int updateDocOrderUnpackingagent(DocOrderUnpackingagent docOrderUnpackingagent);

    /**
     * 删除拆箱代理数据
     *
     * @param id 拆箱代理数据ID
     * @return 结果
     */
    public int deleteDocOrderUnpackingagentByOrderId(String orderId);

    DocOrderUnpackingagent selectOrderUnpackingagentByOrderId(String orderId);
}
