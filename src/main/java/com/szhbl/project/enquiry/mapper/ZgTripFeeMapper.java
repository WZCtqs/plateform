package com.szhbl.project.enquiry.mapper;

import com.szhbl.project.enquiry.domain.ZgTripFee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 郑欧线整柜去程费用Mapper接口
 * 
 * @author jhm
 * @date 2020-04-01
 */
@Repository
public interface ZgTripFeeMapper 
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
     * 根据id修改郑欧线整柜去程费用
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    public int updateZgTripFee(ZgTripFee zgTripFee);

    /**
     * 根据提货城市、集货点、箱型修改郑欧线整柜去程费用
     *
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    public int updateZgTripFeeByParam(ZgTripFee zgTripFee);

    /**
     * 删除郑欧线整柜去程费用
     * 
     * @param id 郑欧线整柜去程费用ID
     * @return 结果
     */
    public int deleteZgTripFeeById(Long id);

    /**
     * 批量删除郑欧线整柜去程费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZgTripFeeByIds(Long[] ids);


    List<ZgTripFee> selectZgTripFeeWithMap(Map<String,String> map);
}
