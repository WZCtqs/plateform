package com.szhbl.project.enquiry.service.impl;

import com.szhbl.project.enquiry.domain.ZgTripFee;
import com.szhbl.project.enquiry.mapper.ZgTripFeeMapper;
import com.szhbl.project.enquiry.service.IZgTripFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 郑欧线整柜去程费用Service业务层处理
 *
 * @author jhm
 * @date 2020-04-01
 */
@Service
public class ZgTripFeeServiceImpl implements IZgTripFeeService {
    @Autowired
    private ZgTripFeeMapper zgTripFeeMapper;

    /**
     * 查询郑欧线整柜去程费用
     * 
     * @param id 郑欧线整柜去程费用ID
     * @return 郑欧线整柜去程费用
     */
    @Override
    public ZgTripFee selectZgTripFeeById(Long id)
    {
        return zgTripFeeMapper.selectZgTripFeeById(id);
    }

    /**
     * 查询郑欧线整柜去程费用列表
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 郑欧线整柜去程费用
     */
    @Override
    public List<ZgTripFee> selectZgTripFeeList(ZgTripFee zgTripFee)
    {
        return zgTripFeeMapper.selectZgTripFeeList(zgTripFee);
    }

    /**
     * 新增郑欧线整柜去程费用
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    @Override
    public int insertZgTripFee(ZgTripFee zgTripFee)
    {
        return zgTripFeeMapper.insertZgTripFee(zgTripFee);
    }

    /**
     * 修改郑欧线整柜去程费用
     * 
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    @Override
    public int updateZgTripFee(ZgTripFee zgTripFee)
    {
        return zgTripFeeMapper.updateZgTripFee(zgTripFee);
    }

    /**
     * 批量删除郑欧线整柜去程费用
     * 
     * @param ids 需要删除的郑欧线整柜去程费用ID
     * @return 结果
     */
    @Override
    public int deleteZgTripFeeByIds(Long[] ids)
    {
        return zgTripFeeMapper.deleteZgTripFeeByIds(ids);
    }

    /**
     * 删除郑欧线整柜去程费用信息
     * 
     * @param id 郑欧线整柜去程费用ID
     * @return 结果
     */
    @Override
    public int deleteZgTripFeeById(Long id)
    {
        return zgTripFeeMapper.deleteZgTripFeeById(id);
    }

    /**
     * 新增或更新郑欧线整柜去程费用
     *
     * @param zgTripFee 郑欧线整柜去程费用
     * @return 结果
     */
    @Override
    public int insertOrUpdateZgTripFee(ZgTripFee zgTripFee) {
        ZgTripFee param = new ZgTripFee();
        param.setPickUpCity(zgTripFee.getPickUpCity());
        param.setCargoCollectionPoint(zgTripFee.getCargoCollectionPoint());
        param.setContainerType(zgTripFee.getContainerType());
        param.setValidStartDate(zgTripFee.getValidStartDate());
        param.setValidEndDate(zgTripFee.getValidEndDate());
        List<ZgTripFee> zgTripFees = zgTripFeeMapper.selectZgTripFeeList(param);
        if (CollectionUtils.isEmpty(zgTripFees)) {
            // 没有就新增
            return zgTripFeeMapper.insertZgTripFee(zgTripFee);
        } else {
            // 有就更新
            return zgTripFeeMapper.updateZgTripFeeByParam(zgTripFee);
        }
    }
}
