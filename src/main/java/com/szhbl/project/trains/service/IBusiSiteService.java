package com.szhbl.project.trains.service;

import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.trains.domain.BusiSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 站点Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface IBusiSiteService 
{
    /**
     * 查询站点
     * 
     * @param id 站点ID
     * @return 站点
     */
    public BusiSite selectBusiSiteById(Long id);

    /**
     * 通过名称查询站点
     *
     * @param nameCn 站点名称
     * @return 站点
     */
    public BusiSite selectSiteByName(String nameCn);

    /**
     * 查询站点列表
     *
     * @return 站点集合
     */
    public List<BusiSite> selectBusiSiteList(BusiSite busiSite);

    public String getBusiSiteByName(String name, String type);

    /**
     * 根据选中口岸查询站点列表
     *
     * @return 站点集合
     */
    public List<BusiSite> selectlistByPort(BusiSite busiSite);

    /**
     * 查询线路类别下站点列表
     * @param lineTypeid 线路类别
     * @return 站点集合
     */
    public List<BusiSite> selectBusiSiteListByLineType(String lineTypeid);

    /**
     * 查询线路下站点列表
     * @param id 线路id
     * @return 站点集合
     */
    public List<BusiSite> selectBusiSiteListByLine(String id);

    /**
     * 查询指定站点
     * @param siteCodes 站点编号
     * @return 站点集合
     */
    public List<BusiSite> listBySiteCodes(String siteCodes);

    /**
     * 查询上下货站点
     */
    public List<BusiSite> siteListCollect();

    /**
     * 新增站点
     * 
     * @param busiSite 站点
     * @return 结果
     */
    public int insertBusiSite(BusiSite busiSite);

    /**
     * 修改站点
     * 
     * @param busiSite 站点
     * @return 结果
     */
    public int updateBusiSite(BusiSite busiSite);

    /**
     * 修改站点状态
     *
     * @param id
     * @param state 站点
     * @return 结果
     */
    public int updateSiteStatus(Long id,String state);

    /**
     * 批量删除站点
     * 
     * @param ids 需要删除的站点ID
     * @return 结果
     */
    public int deleteBusiSiteByIds(Long[] ids);

    /**
     * 删除站点信息
     * 
     * @param code 站点编码
     * @return 结果
     */
    public int deleteBusiSiteById(String code);

    //根据名称查询编号
    public String getCodeByName(String nameCn);
}
