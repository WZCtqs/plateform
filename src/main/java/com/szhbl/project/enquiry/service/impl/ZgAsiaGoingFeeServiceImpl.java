package com.szhbl.project.enquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.enquiry.domain.ZgAsiaGoingFee;
import com.szhbl.project.enquiry.mapper.ZgAsiaGoingFeeMapper;
import com.szhbl.project.enquiry.service.IZgAsiaGoingFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 郑亚、郑越去程整柜提货费Service业务层处理
 *
 * @author szhbl
 * @date 2020-09-03
 */
@Service
public class ZgAsiaGoingFeeServiceImpl implements IZgAsiaGoingFeeService {
    @Autowired
    private ZgAsiaGoingFeeMapper zgAsiaGoingFeeMapper;

    /**
     * 查询郑亚、郑越去程整柜提货费
     *
     * @param id 郑亚、郑越去程整柜提货费ID
     * @return 郑亚、郑越去程整柜提货费
     */
    @Override
    public ZgAsiaGoingFee selectZgAsiaGoingFeeById(Long id) {
        return zgAsiaGoingFeeMapper.selectZgAsiaGoingFeeById(id);
    }

    /**
     * 查询郑亚、郑越去程整柜提货费列表
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 郑亚、郑越去程整柜提货费
     */
    @Override
    public List<ZgAsiaGoingFee> selectZgAsiaGoingFeeList(ZgAsiaGoingFee zgAsiaGoingFee) {
        return zgAsiaGoingFeeMapper.selectZgAsiaGoingFeeList(zgAsiaGoingFee);
    }

    /**
     * 新增郑亚、郑越去程整柜提货费
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 结果
     */
    @Override
    public int insertZgAsiaGoingFee(ZgAsiaGoingFee zgAsiaGoingFee) {
        zgAsiaGoingFee.setCreateTime(DateUtils.getNowDate());

        zgAsiaGoingFee.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(zgAsiaGoingFee.getPickUnit())) {
            zgAsiaGoingFee.setPickUnit("$");
        }
        //判断是否有相同数据存在
        zgAsiaGoingFee.setPickUpCity(zgAsiaGoingFee.getPickUpCity().trim().replace("\n", "").replaceAll("[a-zA-Z]", ""));
        zgAsiaGoingFee.setCargoCollectionPoint(zgAsiaGoingFee.getCargoCollectionPoint().trim().replace("\n", ""));
        zgAsiaGoingFee.setValidStartDate(zgAsiaGoingFee.getValidStartDate());
        zgAsiaGoingFee.setValidEndDate(zgAsiaGoingFee.getValidEndDate());
        List<ZgAsiaGoingFee> zgAsiaGoingFeeList = zgAsiaGoingFeeMapper.selectZgAsiaGoingFeeByCity(zgAsiaGoingFee);
        if (zgAsiaGoingFeeList.size() == 0) {
            return zgAsiaGoingFeeMapper.insertZgAsiaGoingFee(zgAsiaGoingFee);
        } else {
            zgAsiaGoingFee.setId(zgAsiaGoingFeeList.get(0).getId());
            return zgAsiaGoingFeeMapper.updateZgAsiaGoingFee(zgAsiaGoingFee);
        }
    }

    /**
     * 修改郑亚、郑越去程整柜提货费
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 结果
     */
    @Override
    public int updateZgAsiaGoingFee(ZgAsiaGoingFee zgAsiaGoingFee) {
        return zgAsiaGoingFeeMapper.updateZgAsiaGoingFee(zgAsiaGoingFee);
    }

    /**
     * 批量删除郑亚、郑越去程整柜提货费
     *
     * @param ids 需要删除的郑亚、郑越去程整柜提货费ID
     * @return 结果
     */
    @Override
    public int deleteZgAsiaGoingFeeByIds(Long[] ids) {
        return zgAsiaGoingFeeMapper.deleteZgAsiaGoingFeeByIds(ids);
    }

    /**
     * 删除郑亚、郑越去程整柜提货费信息
     *
     * @param id 郑亚、郑越去程整柜提货费ID
     * @return 结果
     */
    @Override
    public int deleteZgAsiaGoingFeeById(Long id) {
        return zgAsiaGoingFeeMapper.deleteZgAsiaGoingFeeById(id);
    }

    @Override
    public List<ZgAsiaGoingFee> selectZgAsiaGoingFeeByCity(ZgAsiaGoingFee zgAsiaGoingFee) {
        return zgAsiaGoingFeeMapper.selectZgAsiaGoingFeeByCity(zgAsiaGoingFee);
    }
}
