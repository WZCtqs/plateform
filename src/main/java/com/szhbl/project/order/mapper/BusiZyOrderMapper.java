package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiZyOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 排舱托书信息Mapper接口
 * 
 * @author dps
 * @date 2020-08-20
 */
@Repository
public interface BusiZyOrderMapper 
{
    /**
     * 查询排舱托书信息
     * 
     * @param id 排舱托书信息ID
     * @return 排舱托书信息
     */
    public BusiZyOrder selectBusiZyOrderById(Long id);

    /**
     * 查询排舱托书信息(通过托书id)
     */
    public BusiZyOrder selectBusiZyOrderByOrderId(String orderId);

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
     * 删除排舱托书信息
     * 
     * @param id 排舱托书信息ID
     * @return 结果
     */
    public int deleteBusiZyOrderById(Long id);

    /**
     * 批量删除排舱托书信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiZyOrderByIds(Long[] ids);
}
