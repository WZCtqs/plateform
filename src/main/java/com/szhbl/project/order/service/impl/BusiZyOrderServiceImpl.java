package com.szhbl.project.order.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiZyOrderMapper;
import com.szhbl.project.order.domain.BusiZyOrder;
import com.szhbl.project.order.service.IBusiZyOrderService;

/**
 * 排舱托书信息Service业务层处理
 * 
 * @author dps
 * @date 2020-08-20
 */
@Service
public class BusiZyOrderServiceImpl implements IBusiZyOrderService 
{
    @Autowired
    private BusiZyOrderMapper busiZyOrderMapper;

    /**
     * 查询排舱托书信息
     * 
     * @param id 排舱托书信息ID
     * @return 排舱托书信息
     */
    @Override
    public BusiZyOrder selectBusiZyOrderById(Long id)
    {
        return busiZyOrderMapper.selectBusiZyOrderById(id);
    }

    /**
     * 查询排舱托书信息列表
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 排舱托书信息
     */
    @Override
    public List<BusiZyOrder> selectBusiZyOrderList(BusiZyOrder busiZyOrder)
    {
        return busiZyOrderMapper.selectBusiZyOrderList(busiZyOrder);
    }

    /**
     * 新增排舱托书信息
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 结果
     */
    @Override
    public int insertBusiZyOrder(BusiZyOrder busiZyOrder)
    {
        busiZyOrder.setCreateTime(DateUtils.getNowDate());
        return busiZyOrderMapper.insertBusiZyOrder(busiZyOrder);
    }

    /**
     * 修改排舱托书信息
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 结果
     */
    @Override
    public int updateBusiZyOrder(BusiZyOrder busiZyOrder)
    {
        return busiZyOrderMapper.updateBusiZyOrder(busiZyOrder);
    }

    /**
     * 批量删除排舱托书信息
     * 
     * @param ids 需要删除的排舱托书信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiZyOrderByIds(Long[] ids)
    {
        return busiZyOrderMapper.deleteBusiZyOrderByIds(ids);
    }

    /**
     * 删除排舱托书信息信息
     * 
     * @param id 排舱托书信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiZyOrderById(Long id)
    {
        return busiZyOrderMapper.deleteBusiZyOrderById(id);
    }
}
