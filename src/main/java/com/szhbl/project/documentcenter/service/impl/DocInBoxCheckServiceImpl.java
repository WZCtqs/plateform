package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.vo.PxInBoxCheck;
import com.szhbl.project.documentcenter.mapper.DocPxInBoxCheckMapper;
import com.szhbl.project.documentcenter.service.IDocInBoxCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单—单证（订单所需单证）Service业务层处理
 *
 * @author hp
 * @date 2020-04-13
 */
@Service
public class DocInBoxCheckServiceImpl implements IDocInBoxCheckService
{
    @Resource
    private DocPxInBoxCheckMapper docPxInBoxCheckMapper;

    @Override
    public PxInBoxCheck selectDocInBoxCheckByOrderId(String orderId) {
        return docPxInBoxCheckMapper.selectDocInBoxCheckByOrderId(orderId);
    }

    @Override
    public int insertDocInBoxCheck(PxInBoxCheck pxInBoxCheck) {
        return docPxInBoxCheckMapper.insertDocInBoxCheck(pxInBoxCheck);
    }

    @Override
    public int deleteDocInBoxCheckByOrderId(String orderId) {
        return docPxInBoxCheckMapper.deleteDocInBoxCheckByOrderId(orderId);
    }
}
