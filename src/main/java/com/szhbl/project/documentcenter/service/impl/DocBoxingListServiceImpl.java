package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.vo.PxBoxingList;
import com.szhbl.project.documentcenter.mapper.DocPxBoxingListMapper;
import com.szhbl.project.documentcenter.service.IDocBoxingListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 装柜清单数据
 *
 * @author hp
 * @date 2020-04-13
 */
@Service
public class DocBoxingListServiceImpl implements IDocBoxingListService
{
    @Resource
    private DocPxBoxingListMapper docPxBoxingListMapper;

    @Override
    public PxBoxingList selectDocBoxingListByOrderId(String orderId) {
        return docPxBoxingListMapper.selectDocBoxingListByOrderId(orderId);
    }

    @Override
    public int insertDocBoxingList(PxBoxingList pxBoxingList) {
        return docPxBoxingListMapper.insertDocBoxingList(pxBoxingList);
    }

    @Override
    public int deleteDocBoxingListByOrderId(String orderId) {
        return docPxBoxingListMapper.deleteDocBoxingListByOrderId(orderId);
    }

    @Override
    public int deleteByOrderIdXiangHao(String orderId, String xianghao) {
        return docPxBoxingListMapper.deleteByOrderIdXiangHao(orderId, xianghao);
    }
}
