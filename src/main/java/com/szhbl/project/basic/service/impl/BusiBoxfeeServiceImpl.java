package com.szhbl.project.basic.service.impl;

import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.project.basic.domain.boxfee.BoxfeeP;
import com.szhbl.project.basic.domain.boxfee.BoxfeeT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.basic.mapper.BusiBoxfeeMapper;
import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.basic.service.IBusiBoxfeeService;

/**
 * 提还箱地和费用规则Service业务层处理
 * 
 * @author dps
 * @date 2020-01-15
 */
@Service
public class BusiBoxfeeServiceImpl implements IBusiBoxfeeService 
{
    @Autowired
    private BusiBoxfeeMapper busiBoxfeeMapper;

    /**
     * 查询提还箱地和费用规则
     * 
     * @param boxId 提还箱地和费用规则ID
     * @return 提还箱地和费用规则
     */
    @Override
    public BusiBoxfee selectBusiBoxfeeById(String boxId)
    {
        return busiBoxfeeMapper.selectBusiBoxfeeById(boxId);
    }

    /**
     * 查询提还箱地和费用规则列表
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 提还箱地和费用规则
     */
    @Override
    public List<BusiBoxfee> selectBusiBoxfeeList(BusiBoxfee busiBoxfee)
    {
        return busiBoxfeeMapper.selectBusiBoxfeeList(busiBoxfee);
    }

    /**
     * 新增提还箱地和费用规则
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 结果
     */
    @Override
    public int insertBusiBoxfee(BusiBoxfee busiBoxfee)
    {
        busiBoxfee.setCreatedate(DateUtils.getNowDate());
        busiBoxfee.setBoxId(IdUtils.randomUUID());
        return busiBoxfeeMapper.insertBusiBoxfee(busiBoxfee);
    }
    public int insertBusiBoxfeeT(BoxfeeT busiBoxfee)
    {
        busiBoxfee.setCreatedate(DateUtils.getNowDate());
        busiBoxfee.setBoxId(IdUtils.randomUUID());
        return busiBoxfeeMapper.insertBusiBoxfeeT(busiBoxfee);
    }
    public int insertBusiBoxfeeP(BoxfeeP busiBoxfee)
    {
        busiBoxfee.setCreatedate(DateUtils.getNowDate());
        busiBoxfee.setBoxId(IdUtils.randomUUID());
        return busiBoxfeeMapper.insertBusiBoxfeeP(busiBoxfee);
    }

    /**
     * 修改提还箱地和费用规则
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 结果
     */
    @Override
    public int updateBusiBoxfee(BusiBoxfee busiBoxfee)
    {
        return busiBoxfeeMapper.updateBusiBoxfee(busiBoxfee);
    }

    /**
     * 批量删除提还箱地和费用规则
     * 
     * @param boxIds 需要删除的提还箱地和费用规则ID
     * @return 结果
     */
    @Override
    public int deleteBusiBoxfeeByIds(String[] boxIds)
    {
        return busiBoxfeeMapper.deleteBusiBoxfeeByIds(boxIds);
    }

    /**
     * 删除提还箱地和费用规则信息
     * 
     * @param boxId 提还箱地和费用规则ID
     * @return 结果
     */
    @Override
    public int deleteBusiBoxfeeById(String boxId)
    {
        return busiBoxfeeMapper.deleteBusiBoxfeeById(boxId);
    }

    /**
     * 查询国内还箱地及还箱平衡费
     *
     * @param containerType
     * @return
     */
    @Override
    public List<BusiBoxfee> getAddressList(String containerType) {
        return busiBoxfeeMapper.getAddressList(containerType);
    }
    /**
     * 查询国内提箱地及提箱平衡费
     *
     * @param containerType
     * @return
     */
    @Override
    public List<BusiBoxfee> getPickUpList(String containerType,String bookingTimeFlag) {
        return busiBoxfeeMapper.getPickUpList(containerType,bookingTimeFlag);
    }
}
