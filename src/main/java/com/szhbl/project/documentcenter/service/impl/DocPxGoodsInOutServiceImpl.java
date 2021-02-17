package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.vo.PxGoodsInOut;
import com.szhbl.project.documentcenter.mapper.DocPxGoodsInOutMapper;
import com.szhbl.project.documentcenter.service.IDocPxGoodsInOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拼箱出入库表数据
 *
 * @author hp
 * @date 2020-04-13
 */
@Service
public class DocPxGoodsInOutServiceImpl implements IDocPxGoodsInOutService
{
    @Resource
    private DocPxGoodsInOutMapper docPxGoodsInOutMapper;

    @Override
    public PxGoodsInOut selectDocPxGoodsInOutByOrderId(String orderId) {
        return docPxGoodsInOutMapper.selectDocPxGoodsInOutByOrderId(orderId);
    }

    @Override
    public int insertDocPxGoodsInOut(PxGoodsInOut pxPxGoodsInOut) {
        return docPxGoodsInOutMapper.insertDocPxGoodsInOut(pxPxGoodsInOut);
    }

    @Override
    public int deleteDocPxGoodsInOutByOrderId(String orderId) {
        return docPxGoodsInOutMapper.deleteDocPxGoodsInOutByOrderId(orderId);
    }
}
