package com.szhbl.project.enquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.enquiry.domain.ZgReturnTripFee;
import com.szhbl.project.enquiry.mapper.ZgReturnTripFeeMapper;
import com.szhbl.project.enquiry.service.IZgReturnTripFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 郑欧整柜回程送货费用Service业务层处理
 * 
 * @author jhm
 * @date 2020-04-02
 */
@Service
public class ZgReturnTripFeeServiceImpl implements IZgReturnTripFeeService 
{
    @Autowired
    private ZgReturnTripFeeMapper zgReturnTripFeeMapper;

    /**
     * 查询郑欧整柜回程送货费用
     * 
     * @param id 郑欧整柜回程送货费用ID
     * @return 郑欧整柜回程送货费用
     */
    @Override
    public ZgReturnTripFee selectZgReturnTripFeeById(Long id)
    {
        return zgReturnTripFeeMapper.selectZgReturnTripFeeById(id);
    }

    /**
     * 查询郑欧整柜回程送货费用列表
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 郑欧整柜回程送货费用
     */
    @Override
    public List<ZgReturnTripFee> selectZgReturnTripFeeList(ZgReturnTripFee zgReturnTripFee)
    {
        return zgReturnTripFeeMapper.selectZgReturnTripFeeList(zgReturnTripFee);
    }

    /**
     * 新增郑欧整柜回程送货费用
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    @Override
    public int insertZgReturnTripFee(ZgReturnTripFee zgReturnTripFee)
    {
        return zgReturnTripFeeMapper.insertZgReturnTripFee(zgReturnTripFee);
    }

    /**
     * 修改郑欧整柜回程送货费用
     * 
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    @Override
    public int updateZgReturnTripFee(ZgReturnTripFee zgReturnTripFee)
    {
        return zgReturnTripFeeMapper.updateZgReturnTripFee(zgReturnTripFee);
    }

    /**
     * 批量删除郑欧整柜回程送货费用
     * 
     * @param ids 需要删除的郑欧整柜回程送货费用ID
     * @return 结果
     */
    @Override
    public int deleteZgReturnTripFeeByIds(Long[] ids)
    {
        return zgReturnTripFeeMapper.deleteZgReturnTripFeeByIds(ids);
    }

    /**
     * 删除郑欧整柜回程送货费用信息
     * 
     * @param id 郑欧整柜回程送货费用ID
     * @return 结果
     */
    @Override
    public int deleteZgReturnTripFeeById(Long id)
    {
        return zgReturnTripFeeMapper.deleteZgReturnTripFeeById(id);
    }

    /**
     * 新增或更新郑欧整柜回程送货费用
     *
     * @param zgReturnTripFee 郑欧整柜回程送货费用
     * @return 结果
     */
    @Override
    public int insertOrUpdateZgReturnTripFee(ZgReturnTripFee zgReturnTripFee) {
        Map<String, String> param = new HashMap();
        // 查询收货地
        param.put("receiptPlace", zgReturnTripFee.getReceiptPlace());
        param.put("endTime", DateUtils.parseDateToStr("yyyy-MM-dd",zgReturnTripFee.getValidEndDate()));
        param.put("startTime", DateUtils.parseDateToStr("yyyy-MM-dd",zgReturnTripFee.getValidStartDate()));
        List<ZgReturnTripFee> zgReturnTripFees = zgReturnTripFeeMapper.selectZgReturnTripFeeWithMap(param);
        if (CollectionUtils.isEmpty(zgReturnTripFees)) {
            // 没有就新增
            return zgReturnTripFeeMapper.insertZgReturnTripFee(zgReturnTripFee);
        } else {
            // 有就更新
            return zgReturnTripFeeMapper.updateZgReturnTripFeeByParam(zgReturnTripFee);
        }
    }
}
