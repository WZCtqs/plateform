package com.szhbl.project.trains.mapper;

import com.szhbl.project.trains.domain.BusiLinesite;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 线路Mapper接口
 * 
 * @author dps
 * @date 2020-01-11
 */
@Repository
public interface BusiLinesiteMapper 
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
     * 修改线路站点状态
     *
     * @param id
     * @param state 线路站点
     * @return 结果
     */
    public int updateLineStatus(String id,String state);

    /**
     * 删除线路
     * 
     * @param id 线路ID
     * @return 结果
     */
    public int deleteBusiLinesiteById(String id);

    /**
     * 批量删除线路
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiLinesiteByIds(String[] ids);

    /**
     * 校验线路中文名称是否唯一
     *
     * @param nameCn 线路名称
     * @return 结果
     */
    public int checkNameCnUnique(String nameCn);
    /**
     * 校验线路英文名称是否唯一
     *
     * @param nameEn 线路名称
     * @return 结果
     */
    public int checkNameEnUnique(String nameEn);

    /**
     * 校验线路中文名称是否唯一(更新)
     *
     * @param nameCn 线路名称
     * @return 结果
     */
    public BusiLinesite checkNameCnUpdUnique(String nameCn);
    /**
     * 校验线路英文名称是否唯一（更新）
     *
     * @param nameEn 线路名称
     * @return 结果
     */
    public BusiLinesite checkNameEnUpdUnique(String nameEn);
}
