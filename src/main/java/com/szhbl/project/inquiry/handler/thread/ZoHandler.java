package com.szhbl.project.inquiry.handler.thread;

import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.handler.service.ZoCalculateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 郑欧线路计费计算
 */
@Slf4j
public class ZoHandler implements Runnable {


    private BookingInquiry bookingInquiry;

    private ZoCalculateService zoCalculateService;

    public ZoHandler(BookingInquiry bookingInquiry, ZoCalculateService zoCalculateService){
        this.bookingInquiry = bookingInquiry;
        this.zoCalculateService = zoCalculateService;
    }

    @Override
    public void run() {
        log.debug("中欧新线程：：：：：bookingInquiry"+this.bookingInquiry);
        this.zoCalculateService.zoCalculateAndInsertBookingInquiryResult(this.bookingInquiry);
    }
}
