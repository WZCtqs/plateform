package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.LadingBill;
import com.szhbl.project.documentcenter.mapper.LadingBillMapper;
import com.szhbl.project.documentcenter.service.LadingBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LadingBillServiceImpl implements LadingBillService {

    @Resource
    LadingBillMapper ladingBillMapper;

    @Override
    public LadingBill selectLadingBillByOrderId(String orderId) {
        return ladingBillMapper.selectLadingBillByOrderId(orderId);
    }
}
