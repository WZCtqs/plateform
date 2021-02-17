package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.BusiZyOrder;
import java.util.List;

/**
 * 排舱托书信息Service接口
 * 
 * @author dps
 * @date 2020-08-20
 */
public interface IBusiZyOrderService 
{
    /**
     * 查询排舱托书信息
     * 
     * @param id 排舱托书信息ID
     * @return 排舱托书信息
     */
    public BusiZyOrder selectBusiZyOrderById(Long id);

    /**
     * 查询排舱托书信息列表
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 排舱托书信息集合
     */
    public List<BusiZyOrder> selectBusiZyOrderList(BusiZyOrder busiZyOrder);

    /**
     * 新增排舱托书信息
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 结果
     */
    public int insertBusiZyOrder(BusiZyOrder busiZyOrder);

    /**
     * 修改排舱托书信息
     * 
     * @param busiZyOrder 排舱托书信息
     * @return 结果
     */
    public int updateBusiZyOrder(BusiZyOrder busiZyOrder);

    /**
     * 批量删除排舱托书信息
     * 
     * @param ids 需要删除的排舱托书信息ID
     * @return 结果
     */
    public int deleteBusiZyOrderByIds(Long[] ids);

    /**
     * 删除排舱托书信息信息
     * 
     * @param id 排舱托书信息ID
     * @return 结果
     */
    public int deleteBusiZyOrderById(Long id);
}
