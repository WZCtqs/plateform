package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.vo.PxYardLoadedIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入仓核实单
 *
 * @author hp
 * @date 2020-05-6
 */
public interface DocPxYardLoadedInMapper {
    /**
     * 查询入仓核实单信息
     *
     * @param order_id
     * @return
     */
    public List<PxYardLoadedIn> selectDocYardLoadedInByOrderId(String order_id);

    /**
     * 新增入仓核实单信息
     *
     * @return 结果
     */
    public int insertDocYardLoadedIn(PxYardLoadedIn pxYardLoadedIn);

    /**
     * 删除入仓核实单信息
     *
     * @return 结果
     */
    public int deleteDocYardLoadedInByOrderId(String order_id);

    int deleteDocYardLoadedInByOrderIdAndConno(@Param("order_id") String order_id, @Param("xianghao")String xianghao);
}
