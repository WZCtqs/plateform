package com.szhbl.project.documentcenter.service;


import com.szhbl.project.documentcenter.domain.vo.PxYardLoadedIn;

import java.util.List;

/**
 * 堆场重箱新站表数据
 *
 * @author hp
 * @date 2020-04-13
 */
public interface IDocYardLoadedInService
{
    /**
     * 查询堆场重箱新站表
     *
     * @param order_id
     * @return
     */
    public List<PxYardLoadedIn> selectDocYardLoadedInByOrderId(String order_id);

    /**
     * 新增堆场重箱新站表
     *
     * @return 结果
     */
    public int insertDocYardLoadedIn(PxYardLoadedIn pxYardLoadedIn);

    /**
     * 删除堆场重箱新站表
     *
     * @return 结果
     */
    public int deleteDocYardLoadedInByOrderId(String order_id);

    int deleteDocYardLoadedInByOrderIdAndConno(String order_id, String xianghao);
}
