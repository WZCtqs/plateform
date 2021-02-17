package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.vo.PxInBoxCheck;

/**
 * 入仓核实单
 *
 * @author hp
 * @date 2020-05-6
 */
public interface DocPxInBoxCheckMapper
{
    /**
     * 查询入仓核实单信息
     * @param orderId
     * @return
     */
    public PxInBoxCheck selectDocInBoxCheckByOrderId(String orderId);

    /**
     * 新增入仓核实单信息
     *
     * @return 结果
     */
    public int insertDocInBoxCheck(PxInBoxCheck pxInBoxCheck);

    /**
     * 删除入仓核实单信息
     *
     * @return 结果
     */
    public int deleteDocInBoxCheckByOrderId(String orderId);
}
