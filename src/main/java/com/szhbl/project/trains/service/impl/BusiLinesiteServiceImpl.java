package com.szhbl.project.trains.service.impl;

import java.util.Date;
import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.trains.mapper.BusiLinesiteMapper;
import com.szhbl.project.trains.domain.BusiLinesite;
import com.szhbl.project.trains.service.IBusiLinesiteService;

/**
 * 线路Service业务层处理
 * 
 * @author dps
 * @date 2020-01-11
 */
@Service
public class BusiLinesiteServiceImpl implements IBusiLinesiteService 
{
    @Autowired
    private BusiLinesiteMapper busiLinesiteMapper;

    /**
     * 查询线路
     * 
     * @param id 线路ID
     * @return 线路
     */
    @Override
    public BusiLinesite selectBusiLinesiteById(String id)
    {
        BusiLinesite lineInfo = busiLinesiteMapper.selectBusiLinesiteById(id);
        if(StringUtils.isNotNull(lineInfo)){
            String siteCodes = lineInfo.getSiteCodes();
            if(StringUtils.isNotEmpty(siteCodes)){
                String[] siteCodesArr = siteCodes.split(",");
                lineInfo.setSiteCodesArr(siteCodesArr);
            }
        }
        return lineInfo;
    }

    /**
     * 查询线路列表
     * 
     * @param busiLinesite 线路
     * @return 线路
     */
    @Override
    public List<BusiLinesite> selectBusiLinesiteList(BusiLinesite busiLinesite)
    {
        return busiLinesiteMapper.selectBusiLinesiteList(busiLinesite);
    }

    /**
     * 新增线路
     * 
     * @param busiLinesite 线路
     * @return 结果
     */
    @Override
    public int insertBusiLinesite(BusiLinesite busiLinesite)
    {
        Long id = (new Date().getTime())/1000;
        busiLinesite.setId(String.valueOf(id));
        busiLinesite.setUpdatetime(DateUtils.getNowDate());
        String[] siteCodesArr = busiLinesite.getSiteCodesArr();
        if(StringUtils.isNotNull(siteCodesArr)){
            busiLinesite.setSiteCodes(StringUtils.join(siteCodesArr, ","));
        }
        return busiLinesiteMapper.insertBusiLinesite(busiLinesite);
    }

    /**
     * 修改线路
     * 
     * @param busiLinesite 线路
     * @return 结果
     */
    @Override
    public int updateBusiLinesite(BusiLinesite busiLinesite)
    {
        busiLinesite.setUpdatetime(DateUtils.getNowDate());
        String[] siteCodesArr = busiLinesite.getSiteCodesArr();
        if(StringUtils.isNotNull(siteCodesArr)){
            busiLinesite.setSiteCodes(StringUtils.join(siteCodesArr, ","));
        }
        return busiLinesiteMapper.updateBusiLinesite(busiLinesite);
    }

    /**
     * 修改线路状态
     *
     * @param id
     * @param state 线路站点
     * @return 结果
     */
    @Override
    public int updateLineStatus(String id,String state)
    {
        return busiLinesiteMapper.updateLineStatus(id,state);
    }

    /**
     * 批量删除线路
     * 
     * @param ids 需要删除的线路ID
     * @return 结果
     */
    @Override
    public int deleteBusiLinesiteByIds(String[] ids)
    {
        return busiLinesiteMapper.deleteBusiLinesiteByIds(ids);
    }

    /**
     * 删除线路信息
     * 
     * @param id 线路ID
     * @return 结果
     */
    @Override
    public int deleteBusiLinesiteById(String id)
    {
        return busiLinesiteMapper.deleteBusiLinesiteById(id);
    }

    /**
     * 校验线路中文名称是否唯一
     *
     * @param nameCn 线路名称
     * @return 结果
     */
    @Override
    public String checkNameCnUnique(String nameCn)
    {
        int count = busiLinesiteMapper.checkNameCnUnique(nameCn);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
    /**
     * 校验线路英文名称是否唯一
     *
     * @param nameEn 线路名称
     * @return 结果
     */
    @Override
    public String checkNameEnUnique(String nameEn)
    {
        int count = busiLinesiteMapper.checkNameEnUnique(nameEn);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验线路中文名称是否唯一(更新)
     *
     * @param busiLinesite 线路名称
     * @return 结果
     */
    @Override
    public String checkNameCnUpdUnique(BusiLinesite busiLinesite)
    {
        String Id = busiLinesite.getId();
        BusiLinesite info = busiLinesiteMapper.checkNameCnUpdUnique(busiLinesite.getNameCn());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getId(),Id))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
    /**
     * 校验线路英文名称是否唯一(更新)
     *
     * @param busiLinesite 线路名称
     * @return 结果
     */
    @Override
    public String checkNameEnUpdUnique(BusiLinesite busiLinesite)
    {
        String Id = busiLinesite.getId();
        BusiLinesite info = busiLinesiteMapper.checkNameEnUpdUnique(busiLinesite.getNameEn());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getId(),Id))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
