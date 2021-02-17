package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.LadingBill;

public interface LadingBillMapper {

    public LadingBill selectLadingBillByOrderId(String orderId);
}
