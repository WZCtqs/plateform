package com.szhbl.project.basic.service;

import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.basic.domain.boxfee.BoxfeeP;
import com.szhbl.project.basic.domain.boxfee.BoxfeeT;

import java.util.List;

/**
 * 提还箱地和费用规则Service接口
 * 
 * @author dps
 * @date 2020-01-15
 */
public interface IBusiBoxfeeService 
{
    /**
     * 查询提还箱地和费用规则
     * 
     * @param boxId 提还箱地和费用规则ID
     * @return 提还箱地和费用规则
     */
    public BusiBoxfee selectBusiBoxfeeById(String boxId);

    /**
     * 查询提还箱地和费用规则列表
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 提还箱地和费用规则集合
     */
    public List<BusiBoxfee> selectBusiBoxfeeList(BusiBoxfee busiBoxfee);

    /**
     * 新增提还箱地和费用规则
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 结果
     */
    public int insertBusiBoxfee(BusiBoxfee busiBoxfee);
    public int insertBusiBoxfeeT(BoxfeeT busiBoxfee);
    public int insertBusiBoxfeeP(BoxfeeP busiBoxfee);

    /**
     * 修改提还箱地和费用规则
     * 
     * @param busiBoxfee 提还箱地和费用规则
     * @return 结果
     */
    public int updateBusiBoxfee(BusiBoxfee busiBoxfee);

    /**
     * 批量删除提还箱地和费用规则
     * 
     * @param boxIds 需要删除的提还箱地和费用规则ID
     * @return 结果
     */
    public int deleteBusiBoxfeeByIds(String[] boxIds);

    /**
     * 删除提还箱地和费用规则信息
     * 
     * @param boxId 提还箱地和费用规则ID
     * @return 结果
     */
    public int deleteBusiBoxfeeById(String boxId);

    /**
     * 查询国内还箱地及还箱平衡费
     *
     * @param containerType
     * @return
     */
    List<BusiBoxfee> getAddressList(String containerType);
    /**
     * 查询国内提箱地及提箱平衡费
     *
     * @param containerType
     * @return
     */
    List<BusiBoxfee> getPickUpList(String containerType,String bookingTimeFlag);
}
