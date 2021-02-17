package com.szhbl.project.client.service.impl;

import java.util.List;

import com.szhbl.common.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.client.mapper.BusiClientsatisfactionMapper;
import com.szhbl.project.client.domain.BusiClientsatisfaction;
import com.szhbl.project.client.service.IBusiClientsatisfactionService;

/**
 * 客户满意度Service业务层处理
 * 
 * @author jhm
 * @date 2020-01-13
 */
@Service
public class BusiClientsatisfactionServiceImpl implements IBusiClientsatisfactionService 
{
    @Autowired
    private BusiClientsatisfactionMapper busiClientsatisfactionMapper;

    /**
     * 查询客户满意度
     * 
     * @param satisfactionId 客户满意度ID
     * @return 客户满意度
     */
    @Override
    public BusiClientsatisfaction selectBusiClientsatisfactionById(String satisfactionId)
    {
        return busiClientsatisfactionMapper.selectBusiClientsatisfactionById(satisfactionId);
    }

    /**
     * 查询客户满意度列表
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 客户满意度
     */
    @Override
    public List<BusiClientsatisfaction> selectBusiClientsatisfactionList(BusiClientsatisfaction busiClientsatisfaction)
    {
        return busiClientsatisfactionMapper.selectBusiClientsatisfactionList(busiClientsatisfaction);
    }

    /**
     * 新增客户满意度
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 结果
     */
    @Override
    public int insertBusiClientsatisfaction(BusiClientsatisfaction busiClientsatisfaction)
    {
        return busiClientsatisfactionMapper.insertBusiClientsatisfaction(busiClientsatisfaction);
    }

    /**
     * 修改客户满意度
     * 
     * @param busiClientsatisfaction 客户满意度
     * @return 结果
     */
    @Override
    public int updateBusiClientsatisfaction(BusiClientsatisfaction busiClientsatisfaction)
    {

        //修改之前先查询表中是否存在order_id,如果存在就更新，否则新增
        List<BusiClientsatisfaction> busiClientsatisfactionList = busiClientsatisfactionMapper.selectBusiClientsatisfactionWithClientId(busiClientsatisfaction.getClientId());
        if(busiClientsatisfactionList.size()==0){
            //新增
            busiClientsatisfaction.setSatisfactionId( KeyUtil.genUniqueKey());
            return  busiClientsatisfactionMapper.insertBusiClientsatisfaction(busiClientsatisfaction );
        }
        return busiClientsatisfactionMapper.updateBusiClientsatisfaction(busiClientsatisfaction);
    }

    /**
     * 批量删除客户满意度
     * 
     * @param satisfactionIds 需要删除的客户满意度ID
     * @return 结果
     */
    @Override
    public int deleteBusiClientsatisfactionByIds(String[] satisfactionIds)
    {
        return busiClientsatisfactionMapper.deleteBusiClientsatisfactionByIds(satisfactionIds);
    }

    /**
     * 删除客户满意度信息
     * 
     * @param satisfactionId 客户满意度ID
     * @return 结果
     */
    @Override
    public int deleteBusiClientsatisfactionById(String satisfactionId)
    {
        return busiClientsatisfactionMapper.deleteBusiClientsatisfactionById(satisfactionId);
    }
}
