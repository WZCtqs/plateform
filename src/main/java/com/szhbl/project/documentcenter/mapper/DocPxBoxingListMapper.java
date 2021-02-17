package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.vo.PxBoxingList;
import org.apache.ibatis.annotations.Param;

/**
 * 入仓核实单
 *
 * @author hp
 * @date 2020-05-6
 */
public interface DocPxBoxingListMapper
{
    /**
     * 查询入仓核实单信息
     * @param orderId
     * @return
     */
    public PxBoxingList selectDocBoxingListByOrderId(String orderId);

    /**
     * 新增入仓核实单信息
     *
     * @return 结果
     */
    public int insertDocBoxingList(PxBoxingList pxBoxingList);

    /**
     * 删除入仓核实单信息
     *
     * @return 结果
     */
    public int deleteDocBoxingListByOrderId(String orderId);

    int deleteByOrderIdXiangHao(@Param("orderId") String orderId, @Param("xianghao") String xianghao);
}
