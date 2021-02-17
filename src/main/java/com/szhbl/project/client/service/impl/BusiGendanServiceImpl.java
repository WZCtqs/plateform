package com.szhbl.project.client.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.client.mapper.BusiGendanMapper;
import com.szhbl.project.client.domain.BusiGendan;
import com.szhbl.project.client.service.IBusiGendanService;

/**
 * 客户跟单员Service业务层处理
 * 
 * @author szhbl
 * @date 2020-06-16
 */
@Service
public class BusiGendanServiceImpl implements IBusiGendanService 
{
    @Autowired
    private BusiGendanMapper busiGendanMapper;

    /**
     * 查询客户跟单员
     * 
     * @param id 客户跟单员ID
     * @return 客户跟单员
     */
    @Override
    public BusiGendan selectBusiGendanById(Long id)
    {
        return busiGendanMapper.selectBusiGendanById(id);
    }

    /**
     * 查询客户跟单员列表
     * 
     * @param busiGendan 客户跟单员
     * @return 客户跟单员
     */
    @Override
    public List<BusiGendan> selectBusiGendanList(BusiGendan busiGendan)
    {
        return busiGendanMapper.selectBusiGendanList(busiGendan);
    }

    /**
     * 新增客户跟单员
     * 
     * @param busiGendan 客户跟单员
     * @return 结果
     */
    @Override
    public int insertBusiGendan(BusiGendan busiGendan)
    {
        busiGendan.setCreateTime(DateUtils.getNowDate());
        return busiGendanMapper.insertBusiGendan(busiGendan);
    }

    /**
     * 修改客户跟单员
     * 
     * @param busiGendan 客户跟单员
     * @return 结果
     */
    @Override
    public int updateBusiGendanByUserId(BusiGendan busiGendan)
    {
        busiGendan.setUpdateTime(DateUtils.getNowDate());
        return busiGendanMapper.updateBusiGendanByUserId(busiGendan);
    }

    /**
     * 批量删除客户跟单员
     * 
     * @param ids 需要删除的客户跟单员ID
     * @return 结果
     */
    @Override
    public int deleteBusiGendanByIds(Long[] ids)
    {
        return busiGendanMapper.deleteBusiGendanByIds(ids);
    }

    /**
     * 删除客户跟单员信息
     * 
     * @param id 客户跟单员ID
     * @return 结果
     */
    @Override
    public int deleteBusiGendanByUserId(String id)
    {
        return busiGendanMapper.deleteBusiGendanByUserId(id);
    }
}
