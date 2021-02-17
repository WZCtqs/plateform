package com.szhbl.project.basic.mapper;

import com.szhbl.project.basic.domain.BusiBoxfee;
import com.szhbl.project.basic.domain.boxfee.BoxfeeP;
import com.szhbl.project.basic.domain.boxfee.BoxfeeT;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 提还箱地和费用规则Mapper接口
 * 
 * @author dps
 * @date 2020-01-15
 */
@Repository
public interface BusiBoxfeeMapper 
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
     * 删除提还箱地和费用规则
     * 
     * @param boxId 提还箱地和费用规则ID
     * @return 结果
     */
    public int deleteBusiBoxfeeById(String boxId);

    /**
     * 批量删除提还箱地和费用规则
     * 
     * @param boxIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBoxfeeByIds(String[] boxIds);

    public List<BusiBoxfee> selectBusiBoxfeeWithMap(Map<String,String> map);

    List<BusiBoxfee> selectBoxFeeWithMap(Map<String, String> map);

    List<BusiBoxfee> getAddressList(String containerType);
    // getPickUpList
    List<BusiBoxfee> getPickUpList(@Param("containerType") String containerType,@Param("bookingTimeFlag") String bookingTimeFlag);

    /**
     * 查询包含当月次月询价
     * @param map
     * @return
     */
    List<BusiBoxfee> selectBoxFeeWithMap2(Map<String, String> map);

    /**
     * 查询对应英文名
     * @param address
     * @return
     */
    List<BusiBoxfee> selectBoxFeeByAddress(String address);
}
