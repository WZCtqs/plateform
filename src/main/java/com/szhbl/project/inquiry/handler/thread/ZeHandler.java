package com.szhbl.project.inquiry.handler.thread;

import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.handler.service.ZeCalculateService;
import lombok.extern.slf4j.Slf4j;

/**
 * 郑俄线路计费计算
 */
@Slf4j
public class ZeHandler implements Runnable {

    private BookingInquiry bookingInquiry;

    private ZeCalculateService zeCalculateService;

    public ZeHandler(BookingInquiry bookingInquiry, ZeCalculateService zeCalculateService){
        this.bookingInquiry = bookingInquiry;
        this.zeCalculateService = zeCalculateService;
    }

    @Override
    public void run() {
        log.debug("中俄新线程：：：：：bookingInquiry"+this.bookingInquiry);
        this.zeCalculateService.zeCalculateAndInsertBookingInquiryResult(this.bookingInquiry);
    }
}
