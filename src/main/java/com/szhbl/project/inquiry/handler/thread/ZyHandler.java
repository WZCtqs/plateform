package com.szhbl.project.inquiry.handler.thread;

import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.handler.service.ZyCalculateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 中亚线路计费计算
 */
@Slf4j
public class ZyHandler implements Runnable {

    private BookingInquiry bookingInquiry;

    private ZyCalculateService zyCalculateService;

    public ZyHandler(BookingInquiry bookingInquiry, ZyCalculateService zyCalculateService){
        this.bookingInquiry = bookingInquiry;
        this.zyCalculateService = zyCalculateService;
    }

    @Override
    public void run() {
        log.debug("中亚新线程：：：：：bookingInquiry"+this.bookingInquiry);
        this.zyCalculateService.zyCalculateAndInsertBookingInquiryResult(this.bookingInquiry);
    }
}
