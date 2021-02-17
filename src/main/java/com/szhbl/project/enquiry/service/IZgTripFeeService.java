package com.szhbl.project.enquiry.service;

import com.szhbl.project.enquiry.domain.ZgTripFee;
import java.util.List;

/**
 * 郑欧线整柜去程费用Service接口
 * 
 * @author jhm
 * @date 2020-04-01
 */
public interface IZgTripFeeService 
{
    /**
     * 查询郑欧线整柜去程费用
     * 
     * @param id 郑欧线整柜去程费用ID
     * @return 郑欧线整柜去程费用
     */
    public ZgTripFee selectZgTripFeeById(Long id);

    /**
     * 查询郑欧线整柜去程费用列表
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 郑欧线整柜去程费用集合
     */
    public List<ZgTripFee> selectZgTripFeeList(ZgTripFee zgTripFee);

    /**
     * 新增郑欧线整柜去程费用
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    public int insertZgTripFee(ZgTripFee zgTripFee);

    /**
     * 修改郑欧线整柜去程费用
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    public int updateZgTripFee(ZgTripFee zgTripFee);

    /**
     * 批量删除郑欧线整柜去程费用
     * 
     * @param ids 需要删除的郑欧线整柜去程费用ID
     * @return 结果
     */
    public int deleteZgTripFeeByIds(Long[] ids);

    /**
     * 删除郑欧线整柜去程费用信息
     * 
     * @param id 郑欧线整柜去程费用ID
     * @return 结果
     */
    public int deleteZgTripFeeById(Long id);

    /**
     * 新增或更新郑欧线整柜去程费用
     *
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    int insertOrUpdateZgTripFee(ZgTripFee zgTripFee);
}
