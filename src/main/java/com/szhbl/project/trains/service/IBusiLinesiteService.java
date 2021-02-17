package com.szhbl.project.trains.service;

import com.szhbl.project.trains.domain.BusiLinesite;

import java.util.List;

/**
 * 线路Service接口
 * 
 * @author dps
 * @date 2020-01-11
 */
public interface IBusiLinesiteService 
{
    /**
     * 查询线路
     * 
     * @param id 线路ID
     * @return 线路
     */
    public BusiLinesite selectBusiLinesiteById(String id);

    /**
     * 查询线路列表
     * 
     * @param busiLinesite 线路
     * @return 线路集合
     */
    public List<BusiLinesite> selectBusiLinesiteList(BusiLinesite busiLinesite);

    /**
     * 新增线路
     * 
     * @param busiLinesite 线路
     * @return 结果
     */
    public int insertBusiLinesite(BusiLinesite busiLinesite);

    /**
     * 修改线路
     * 
     * @param busiLinesite 线路
     * @return 结果
     */
    public int updateBusiLinesite(BusiLinesite busiLinesite);

    /**
     * 修改线路状态
     *
     * @param id
     * @param state 线路站点
     * @return 结果
     */
    public int updateLineStatus(String id,String state);

    /**
     * 批量删除线路
     * 
     * @param ids 需要删除的线路ID
     * @return 结果
     */
    public int deleteBusiLinesiteByIds(String[] ids);

    /**
     * 删除线路信息
     * 
     * @param id 线路ID
     * @return 结果
     */
    public int deleteBusiLinesiteById(String id);

    /**
     * 校验线路中文名称是否唯一
     *
     * @param nameCn 用户名称
     * @return 结果
     */
    public String checkNameCnUnique(String nameCn);
    /**
     * 校验线路英文名称是否唯一
     *
     * @param nameEn 用户名称
     * @return 结果
     */
    public String checkNameEnUnique(String nameEn);

    /**
     * 校验线路中文名称是否唯一(更新)
     *
     * @param busiLinesite 用户名称
     * @return 结果
     */
    public String checkNameCnUpdUnique(BusiLinesite busiLinesite);
    /**
     * 校验线路英文名称是否唯一（更新）
     *
     * @param busiLinesite 用户名称
     * @return 结果
     */
    public String checkNameEnUpdUnique(BusiLinesite busiLinesite);
}
