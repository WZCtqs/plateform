package com.szhbl.project.enquiry.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.enquiry.domain.ZgRussiaGoingFee;
import com.szhbl.project.enquiry.mapper.ZgRussiaGoingFeeMapper;
import com.szhbl.project.enquiry.service.IZgRussiaGoingFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 俄线提货费Service业务层处理
 *
 * @author szhbl
 * @date 2020-07-10
 */
@Service
public class ZgRussiaGoingFeeServiceImpl implements IZgRussiaGoingFeeService {
    @Autowired
    private ZgRussiaGoingFeeMapper zgRussiaGoingFeeMapper;
    /**
     * 查询俄线提货费
     *
     * @param id 俄线提货费ID
     * @return 俄线提货费
     */
    @Override
    public ZgRussiaGoingFee selectZgRussiaGoingFeeById(Long id) {
        return zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeById(id);
    }

    /**
     * 查询俄线提货费列表
     *
     * @param zgRussiaGoingFee 俄线提货费
     * @return 俄线提货费
     */
    @Override
    public List<ZgRussiaGoingFee> selectZgRussiaGoingFeeList(ZgRussiaGoingFee zgRussiaGoingFee) {
        return zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeList(zgRussiaGoingFee);
    }

    @Override
    public List<ZgRussiaGoingFee> selectZgRussiaGoingFeeByCity(ZgRussiaGoingFee zgRussiaGoingFee) {
        return zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeByCity(zgRussiaGoingFee);
    }

    /**
     * 新增俄线提货费
     *
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    @Override
    public int insertZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee) {
        zgRussiaGoingFee.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(zgRussiaGoingFee.getPickUnit())) {
            zgRussiaGoingFee.setPickUnit("$");
        }
        //判断是否有相同数据存在
        zgRussiaGoingFee.setPickUpCity(zgRussiaGoingFee.getPickUpCity().trim().replace("\n", "").replaceAll("[a-zA-Z]", ""));
        zgRussiaGoingFee.setCargoCollectionPoint(zgRussiaGoingFee.getCargoCollectionPoint().trim().replace("\n", ""));
        zgRussiaGoingFee.setValidStartDate(zgRussiaGoingFee.getValidStartDate());
        zgRussiaGoingFee.setValidEndDate(zgRussiaGoingFee.getValidEndDate());
        List<ZgRussiaGoingFee> zgRussiaGoingFeeList = zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeByCity(zgRussiaGoingFee);
        if (zgRussiaGoingFeeList.size() == 0) {
            return zgRussiaGoingFeeMapper.insertZgRussiaGoingFee(zgRussiaGoingFee);
        } else {
            zgRussiaGoingFee.setId(zgRussiaGoingFeeList.get(0).getId());
            return zgRussiaGoingFeeMapper.updateZgRussiaGoingFee(zgRussiaGoingFee);
        }
    }

    /**
     * 修改俄线提货费
     *
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    @Override
    public int updateZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee) {
        zgRussiaGoingFee.setCreateTime(DateUtils.getNowDate());
        return zgRussiaGoingFeeMapper.updateZgRussiaGoingFee(zgRussiaGoingFee);
    }

    /**
     * 批量删除俄线提货费
     *
     * @param ids 需要删除的俄线提货费ID
     * @return 结果
     */
    @Override
    public int deleteZgRussiaGoingFeeByIds(Long[] ids) {
        return zgRussiaGoingFeeMapper.deleteZgRussiaGoingFeeByIds(ids);
    }

    /**
     * 删除俄线提货费信息
     *
     * @param id 俄线提货费ID
     * @return 结果
     */
    @Override
    public int deleteZgRussiaGoingFeeById(Long id) {
        return zgRussiaGoingFeeMapper.deleteZgRussiaGoingFeeById(id);
    }
}
