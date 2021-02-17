package com.szhbl.project.client.mapper;

import com.szhbl.project.client.domain.BusiClientsatisfaction;
import java.util.List;

/**
 * 客户满意度Mapper接口
 * 
 * @author jhm
 * @date 2020-01-13
 */
public interface BusiClientsatisfactionMapper 
{
    /**
     * 查询客户满意度
     * 
     * @param satisfactionId 客户满意度ID
     * @return 客户满意度
     */
    public BusiClientsatisfaction selectBusiClientsatisfactionById(String satisfactionId);

    /**
     * 查询客户满意度列表
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 客户满意度集合
     */
    public List<BusiClientsatisfaction> selectBusiClientsatisfactionList(BusiClientsatisfaction busiClientsatisfaction);

    /**
     * 新增客户满意度
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 结果
     */
    public int insertBusiClientsatisfaction(BusiClientsatisfaction busiClientsatisfaction);

    /**
     * 修改客户满意度
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 结果
     */
    public int updateBusiClientsatisfaction(BusiClientsatisfaction busiClientsatisfaction);

    /**
     * 删除客户满意度
     * 
     * @param satisfactionId 客户满意度ID
     * @return 结果
     */
    public int deleteBusiClientsatisfactionById(String satisfactionId);

    /**
     * 批量删除客户满意度
     * 
     * @param satisfactionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiClientsatisfactionByIds(String[] satisfactionIds);

    List<BusiClientsatisfaction> selectBusiClientsatisfactionWithClientId(String clientId);
}
