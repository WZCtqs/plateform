package com.szhbl.project.trains.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.trains.domain.BusiLinesite;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.mapper.BusiLinesiteMapper;
import com.szhbl.project.trains.mapper.BusiSiteMapper;
import com.szhbl.project.trains.service.IBusiSiteService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 站点Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-10
 */
@Service
public class BusiSiteServiceImpl implements IBusiSiteService {
    @Autowired
    private BusiSiteMapper busiSiteMapper;

    @Autowired
    private BusiLinesiteMapper busiLinesiteMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询站点
     *
     * @param id 站点ID
     * @return 站点
     */
    @Override
    public BusiSite selectBusiSiteById(Long id) {
        return busiSiteMapper.selectBusiSiteById(id);
    }

    /**
     * 通过名称查询站点
     *
     * @param nameCn 站点名称
     * @return 站点
     */
    @Override
    public BusiSite selectSiteByName(String nameCn)
    {
        return busiSiteMapper.selectSiteByName(nameCn);
    }

    /**
     * 查询站点列表
     *
     * @return 站点
     */
    @Override
    public List<BusiSite> selectBusiSiteList(BusiSite busiSite) {
        return busiSiteMapper.selectBusiSiteList(busiSite);
    }

    @Override
    public String getBusiSiteByName(String name, String language) {
        String resultName = (String) redisTemplate.opsForValue().get("busiSite:" + language + ":" + name);
        if (resultName == null) {
            BusiSite site = busiSiteMapper.getBusiSiteByName(name, language);
            if (site != null) {
                if ("en".equals(language)) {
                    resultName = site.getNameCn();
                    redisTemplate.opsForValue().set("busiSite:" + language + ":" + name, resultName, 24, TimeUnit.HOURS);
                } else {
                    resultName = site.getNameEn();
                    redisTemplate.opsForValue().set("busiSite:" + language + ":" + name, resultName, 24, TimeUnit.HOURS);
                }
                return resultName;
            } else {
                return name;
            }
        } else {
            return resultName;
        }
    }

    /**
     * 根据选中口岸查询站点列表
     *
     * @return 站点
     */
    @Override
    public List<BusiSite> selectlistByPort(BusiSite busiSite) {
        return busiSiteMapper.selectlistByPort(busiSite);
    }

    /**
     * 查询线路类别下站点列表
     *
     * @return 站点
     */
    @Override
    public List<BusiSite> selectBusiSiteListByLineType(String lineTypeid)
    {
        return busiSiteMapper.selectBusiSiteListByLineType(lineTypeid);
    }

    /**
     * 查询线路下站点列表
     *
     * @return 站点
     */
    @Override
    public List<BusiSite> selectBusiSiteListByLine(String id)
    {
        BusiLinesite line = busiLinesiteMapper.selectBusiLinesiteById(id);
        String[] arr = new String[0];
        if(StringUtils.isNotNull(line)){
            String siteCodes = line.getSiteCodes();
            arr = siteCodes.split(",");
            return busiSiteMapper.selectBusiSiteListByLine(arr);
        }
        return null;
    }

    /**
     * 查询指定站点
     *
     * @return 站点
     */
    @Override
    public List<BusiSite> listBySiteCodes(String siteCodes){
        List<BusiSite> list = new ArrayList();;
        String[] arr = new String[0];
        if(StringUtils.isNotEmpty(siteCodes)){
            arr = siteCodes.split(",");
            list = busiSiteMapper.listBySiteCodes(arr);
        }
        return list;
    }

    /**
     * 查询上下货站点
     */
    @Override
    public List<BusiSite> siteListCollect(){
        return busiSiteMapper.siteListCollect();
    }

    /**
     * 新增站点
     * 
     * @param busiSite 站点
     * @return 结果
     */
    @Override
    public int insertBusiSite(BusiSite busiSite)
    {
        busiSite.setUpdatetime(DateUtils.getNowDate());
        return busiSiteMapper.insertBusiSite(busiSite);
    }

    /**
     * 修改站点
     * 
     * @param busiSite 站点
     * @return 结果
     */
    @Override
    public int updateBusiSite(BusiSite busiSite)
    {
        busiSite.setUpdatetime(DateUtils.getNowDate());
        return busiSiteMapper.updateBusiSite(busiSite);
    }

    /**
     * 修改站点状态
     *
     * @param id
     * @param state 站点
     * @return 结果
     */
    @Override
    public int updateSiteStatus(Long id,String state)
    {
        return busiSiteMapper.updateSiteStatus(id,state);
    }

    /**
     * 批量删除站点
     * 
     * @param ids 需要删除的站点ID
     * @return 结果
     */
    @Override
    public int deleteBusiSiteByIds(Long[] ids)
    {
        return busiSiteMapper.deleteBusiSiteByIds(ids);
    }

    /**
     * 删除站点信息
     * 
     * @param code 站点ID
     * @return 结果
     */
    @Override
    public int deleteBusiSiteById(String code)
    {
        return busiSiteMapper.deleteBusiSiteById(code);
    }

    //根据名称查询编号
    @Override
    public String getCodeByName(String nameCn){
        return busiSiteMapper.getCodeByName(nameCn);
    }
}
