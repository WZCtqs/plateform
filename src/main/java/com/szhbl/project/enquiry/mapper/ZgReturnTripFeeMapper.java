package com.szhbl.project.enquiry.mapper;

import com.szhbl.project.enquiry.domain.ZgReturnTripFee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 郑欧整柜回程送货费用Mapper接口
 * 
 * @author jhm
 * @date 2020-04-02
 */
@Repository
public interface ZgReturnTripFeeMapper 
{
    /**
     * 查询郑欧整柜回程送货费用
     * 
     * @param id 郑欧整柜回程送货费用ID
     * @return 郑欧整柜回程送货费用
     */
    public ZgReturnTripFee selectZgReturnTripFeeById(Long id);

    /**
     * 查询郑欧整柜回程送货费用列表
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 郑欧整柜回程送货费用集合
     */
    public List<ZgReturnTripFee> selectZgReturnTripFeeList(ZgReturnTripFee zgReturnTripFee);

    /**
     * 新增郑欧整柜回程送货费用
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    public int insertZgReturnTripFee(ZgReturnTripFee zgReturnTripFee);

    /**
     * 修改郑欧整柜回程送货费用
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    public int updateZgReturnTripFee(ZgReturnTripFee zgReturnTripFee);

    /**
     * 修改郑欧整柜回程送货费用
     *
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    public int updateZgReturnTripFeeByParam(ZgReturnTripFee zgReturnTripFee);

    /**
     * 删除郑欧整柜回程送货费用
     * 
     * @param id 郑欧整柜回程送货费用ID
     * @return 结果
     */
    public int deleteZgReturnTripFeeById(Long id);

    /**
     * 批量删除郑欧整柜回程送货费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZgReturnTripFeeByIds(Long[] ids);

    /**
     *
     */
    List<ZgReturnTripFee> selectZgReturnTripFeeWithMap(Map<String,String> map);
}
