package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.vo.PxYardLoadedIn;
import com.szhbl.project.documentcenter.mapper.DocPxYardLoadedInMapper;
import com.szhbl.project.documentcenter.service.IDocYardLoadedInService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 堆场重箱新站表数据
 *
 * @author hp
 * @date 2020-04-13
 */
@Service
public class DocYardLoadedInServiceImpl implements IDocYardLoadedInService
{
    @Resource
    private DocPxYardLoadedInMapper docPxYardLoadedInMapper;

    @Override
    public List<PxYardLoadedIn> selectDocYardLoadedInByOrderId(String orderId) {
        return docPxYardLoadedInMapper.selectDocYardLoadedInByOrderId(orderId);
    }

    @Override
    public int insertDocYardLoadedIn(PxYardLoadedIn pxYardLoadedIn) {
        return docPxYardLoadedInMapper.insertDocYardLoadedIn(pxYardLoadedIn);
    }

    @Override
    public int deleteDocYardLoadedInByOrderId(String orderId) {
        return docPxYardLoadedInMapper.deleteDocYardLoadedInByOrderId(orderId);
    }

    @Override
    public int deleteDocYardLoadedInByOrderIdAndConno(String order_id, String xianghao) {
        return docPxYardLoadedInMapper.deleteDocYardLoadedInByOrderIdAndConno(order_id, xianghao);
    }
}
