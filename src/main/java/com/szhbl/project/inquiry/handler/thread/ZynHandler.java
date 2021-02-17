package com.szhbl.project.inquiry.handler.thread;

import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.handler.service.ZynCalculateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 中越线路计费计算
 */
@Slf4j
public class ZynHandler implements Runnable {

    private BookingInquiry bookingInquiry;

    private ZynCalculateService zynCalculateService;

    public ZynHandler(BookingInquiry bookingInquiry, ZynCalculateService zynCalculateService){
        this.bookingInquiry = bookingInquiry;
        this.zynCalculateService = zynCalculateService;
    }

    @Override
    public void run() {
        log.debug("中越新线程：：：：：bookingInquiry"+this.bookingInquiry);
        this.zynCalculateService.zynCalculateAndInsertBookingInquiryResult(this.bookingInquiry);
    }
}
