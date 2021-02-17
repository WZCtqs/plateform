package com.szhbl.project.enquiry.service;

import com.szhbl.project.enquiry.domain.ZgRussiaGoingFee;

import java.util.List;

/**
 * 俄线提货费Service接口
 * 
 * @author szhbl
 * @date 2020-07-10
 */
public interface IZgRussiaGoingFeeService 
{
    /**
     * 查询俄线提货费
     * 
     * @param id 俄线提货费ID
     * @return 俄线提货费
     */
    public ZgRussiaGoingFee selectZgRussiaGoingFeeById(Long id);

    /**
     * 查询俄线提货费列表
     *
     * @param zgRussiaGoingFee 俄线提货费
     * @return 俄线提货费集合
     */
    public List<ZgRussiaGoingFee> selectZgRussiaGoingFeeList(ZgRussiaGoingFee zgRussiaGoingFee);

    public List<ZgRussiaGoingFee> selectZgRussiaGoingFeeByCity(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 新增俄线提货费
     *
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    public int insertZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 修改俄线提货费
     * 
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    public int updateZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 批量删除俄线提货费
     * 
     * @param ids 需要删除的俄线提货费ID
     * @return 结果
     */
    public int deleteZgRussiaGoingFeeByIds(Long[] ids);

    /**
     * 删除俄线提货费信息
     * 
     * @param id 俄线提货费ID
     * @return 结果
     */
    public int deleteZgRussiaGoingFeeById(Long id);
}
