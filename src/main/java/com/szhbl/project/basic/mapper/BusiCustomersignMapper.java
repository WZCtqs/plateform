package com.szhbl.project.basic.mapper;

import com.szhbl.project.basic.domain.BusiCustomersign;
import java.util.List;

/**
 * 客户标记Mapper接口
 * 
 * @author dps
 * @date 2020-03-16
 */
public interface BusiCustomersignMapper 
{
    /**
     * 查询客户标记
     * 
     * @param signId 客户标记ID
     * @return 客户标记
     */
    public BusiCustomersign selectBusiCustomersignById(String signId);

    /**
     * 查询客户标记列表
     * 
     * @param busiCustomersign 客户标记
     * @return 客户标记集合
     */
    public List<BusiCustomersign> selectBusiCustomersignList(BusiCustomersign busiCustomersign);
    public List<String> selectListName();

    /**
     * 新增客户标记
     * 
     * @param busiCustomersign 客户标记
     * @return 结果
     */
    public int insertBusiCustomersign(BusiCustomersign busiCustomersign);

    /**
     * 修改客户标记
     * 
     * @param busiCustomersign 客户标记
     * @return 结果
     */
    public int updateBusiCustomersign(BusiCustomersign busiCustomersign);

    /**
     * 删除客户标记
     * 
     * @param signId 客户标记ID
     * @return 结果
     */
    public int deleteBusiCustomersignById(String signId);

    /**
     * 批量删除客户标记
     * 
     * @param signIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiCustomersignByIds(String[] signIds);
}
