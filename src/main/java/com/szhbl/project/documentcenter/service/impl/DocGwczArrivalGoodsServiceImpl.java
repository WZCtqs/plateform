package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.vo.GWCZArrivalGoods;
import com.szhbl.project.documentcenter.mapper.DocGWCZArrivalGoodsMapper;
import com.szhbl.project.documentcenter.service.IDocGwczArrivalGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拼箱出入库表数据
 *
 * @author hp
 * @date 2020-04-13
 */
@Service
public class DocGwczArrivalGoodsServiceImpl implements IDocGwczArrivalGoodsService {
    @Resource
    private DocGWCZArrivalGoodsMapper gwczArrivalGoodsMapper;

    @Override
    public GWCZArrivalGoods selecGwczArrivalGoodsByOrderId(String orderId) {
        return gwczArrivalGoodsMapper.selecGwczArrivalGoodsByOrderId(orderId);
    }

    @Override
    public int insertGwczArrivalGoods(GWCZArrivalGoods arrivalGoods) {
        return gwczArrivalGoodsMapper.insertGwczArrivalGoods(arrivalGoods);
    }

    @Override
    public int deleteByOrderId(String orderId) {
        return gwczArrivalGoodsMapper.deleteByOrderId(orderId);
    }
}
