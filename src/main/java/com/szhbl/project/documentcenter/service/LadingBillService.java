package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.LadingBill;

public interface LadingBillService {

    public LadingBill selectLadingBillByOrderId(String orderId);
}
