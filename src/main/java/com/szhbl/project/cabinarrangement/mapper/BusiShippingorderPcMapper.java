package com.szhbl.project.cabinarrangement.mapper;

import com.szhbl.project.cabinarrangement.domain.BusiShippingorderPc;
import com.szhbl.project.cabinarrangement.vo.Box;
import com.szhbl.project.cabinarrangement.vo.SmallBox;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-15
 */
@Repository
public interface BusiShippingorderPcMapper
{
    /**
     * 查询订单
     * 
     * @param orderId 订单ID
     * @return 订单
     */
    public BusiShippingorderPc selectBusiShippingorderById(String orderId);

    /**
     * 查询订单列表
     * 
     * @param busiShippingorderPc 订单
     * @return 订单集合
     */
    public List<BusiShippingorderPc> selectBusiShippingorderList(BusiShippingorderPc busiShippingorderPc);

    /**
     * 新增订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    public int insertBusiShippingorder(BusiShippingorderPc busiShippingorderPc);

    /**
     * 修改订单
     * 
     * @param busiShippingorderPc 订单
     * @return 结果
     */
    public int updateBusiShippingorder(BusiShippingorderPc busiShippingorderPc);

    /**
     * 删除订单
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteBusiShippingorderById(String orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiShippingorderByIds(String[] orderIds);

    Integer zgCount(String classId);

    //查询班列东西向
    public String getClassesFlag(String classId);

    List<String> selectStation(@Param("classId") String classId,@Param("westAndEast") String westAndEast);

    List<Box> selectZg(@Param("classId") String classId,@Param("station") String station,@Param("westAndEast") String westAndEast);

    List<SmallBox> smallBox(String classId);
}
