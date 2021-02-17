package com.szhbl.project.basic.mapper;

import com.szhbl.project.basic.domain.BusiMoneyrete;
import java.util.List;

/**
 * 汇率Mapper接口
 * 
 * @author dps
 * @date 2020-01-15
 */
public interface BusiMoneyreteMapper 
{
    /**
     * 查询汇率
     * 
     * @param moneyrateId 汇率ID
     * @return 汇率
     */
    public BusiMoneyrete selectBusiMoneyreteById(String moneyrateId);

    /**
     * 查询汇率列表
     * 
     * @param busiMoneyrete 汇率
     * @return 汇率集合
     */
    public List<BusiMoneyrete> selectBusiMoneyreteList(BusiMoneyrete busiMoneyrete);

    /**
     * 新增汇率
     * 
     * @param busiMoneyrete 汇率
     * @return 结果
     */
    public int insertBusiMoneyrete(BusiMoneyrete busiMoneyrete);

    /**
     * 修改汇率
     * 
     * @param busiMoneyrete 汇率
     * @return 结果
     */
    public int updateBusiMoneyrete(BusiMoneyrete busiMoneyrete);

    /**
     * 删除汇率
     * 
     * @param moneyrateId 汇率ID
     * @return 结果
     */
    public int deleteBusiMoneyreteById(String moneyrateId);

    /**
     * 批量删除汇率
     * 
     * @param moneyrateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiMoneyreteByIds(String[] moneyrateIds);
}
