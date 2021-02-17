package com.szhbl.project.order.service;

/**
 * 异步
 */
public interface AsyncService {

    void createOrderNotice(String orderId);

    void orderSendEmail(String orderId,String content);

    void orderSendEmailIn(String orderId,String content);
}
