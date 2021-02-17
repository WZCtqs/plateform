package com.szhbl.project.client.mapper;

import com.szhbl.project.client.domain.BusiGendan;
import java.util.List;

/**
 * 客户跟单员Mapper接口
 * 
 * @author szhbl
 * @date 2020-06-16
 */
public interface BusiGendanMapper 
{
    /**
     * 查询客户跟单员
     * 
     * @param id 客户跟单员ID
     * @return 客户跟单员
     */
    public BusiGendan selectBusiGendanById(Long id);

    /**
     * 查询客户跟单员列表
     * 
     * @param busiGendan 客户跟单员
     * @return 客户跟单员集合
     */
    public List<BusiGendan> selectBusiGendanList(BusiGendan busiGendan);

    /**
     * 新增客户跟单员
     * 
     * @param busiGendan 客户跟单员
     * @return 结果
     */
    public int insertBusiGendan(BusiGendan busiGendan);

    /**
     * 修改客户跟单员
     * 
     * @param busiGendan 客户跟单员
     * @return 结果
     */
    public int updateBusiGendanByUserId(BusiGendan busiGendan);

    /**
     * 删除客户跟单员
     * 
     * @param userid 客户跟单员ID
     * @return 结果
     */
    public int deleteBusiGendanByUserId(String userid);

    /**
     * 批量删除客户跟单员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiGendanByIds(Long[] ids);
}
