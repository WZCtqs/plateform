package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.DocOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单—单证（订单所需单证）Mapper接口
 *
 * @author hp
 * @date 2020-04-13
 */
public interface DocOrderMapper
{
    /**
     * 查询订单—单证（订单所需单证）
     *
     * @param orderId 订单—单证（订单所需单证）ID
     * @return 订单—单证（订单所需单证）
     */
    public List<DocOrder> selectDocOrderByOrderId(String orderId);

    /**
     * 查询订单—单证（订单所需单证）列表
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 订单—单证（订单所需单证）集合
     */
    public List<DocOrder> selectDocOrderList(DocOrder docOrder);

    public DocOrder selectByOrderidTypeKey(@Param("orderId") String orderId, @Param("fileTypeKey") String fileTypeKey);

    public int updateByorderidTypeKey(DocOrder docOrder);

    public int updateActualSupply(DocOrder docOrder);

    /**
     * 新增订单—单证（订单所需单证）
     *
     * @param docOrder 订单—单证（订单所需单证）
     * @return 结果
     */
    public int insertDocOrder(DocOrder docOrder);

    public int insertDocOrderMatch(@Param("docOrderList") List<DocOrder> docOrderList);

    /**
     * 删除订单—单证（订单所需单证）
     *
     * @param orderId 订单—单证（订单所需单证）ID
     * @return 结果
     */
    public int deleteDocOrderByOrderId(String orderId);
}
