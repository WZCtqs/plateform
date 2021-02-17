package com.szhbl.project.basic.service;

import com.szhbl.project.basic.domain.BusiDict;
import java.util.List;

/**
 * 贸易方式Service接口
 * 
 * @author dps
 * @date 2020-03-31
 */
public interface IBusiDictService 
{
    /**
     * 订单页面贸易方式
     *
     * @param busiDict 贸易方式
     * @return 贸易方式集合
     */
    public List<BusiDict> selectBusiDictListMy(BusiDict busiDict);

    /**
     * 查询贸易方式
     * 
     * @param id 贸易方式ID
     * @return 贸易方式
     */
    public BusiDict selectBusiDictById(Long id);

    /**
     * 查询贸易方式列表
     * 
     * @param busiDict 贸易方式
     * @return 贸易方式集合
     */
    public List<BusiDict> selectBusiDictList(BusiDict busiDict);

    /**
     * 新增贸易方式
     * 
     * @param busiDict 贸易方式
     * @return 结果
     */
    public int insertBusiDict(BusiDict busiDict);

    /**
     * 修改贸易方式
     * 
     * @param busiDict 贸易方式
     * @return 结果
     */
    public int updateBusiDict(BusiDict busiDict);

    /**
     * 批量删除贸易方式
     * 
     * @param ids 需要删除的贸易方式ID
     * @return 结果
     */
    public int deleteBusiDictByIds(Long[] ids);

    /**
     * 删除贸易方式信息
     * 
     * @param id 贸易方式ID
     * @return 结果
     */
    public int deleteBusiDictById(Long id);
}
