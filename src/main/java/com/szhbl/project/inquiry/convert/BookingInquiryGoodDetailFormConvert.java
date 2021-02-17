package com.szhbl.project.inquiry.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryBackup;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetailsBackup;
import com.szhbl.project.inquiry.form.BookingInquiryGoodDetailForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class BookingInquiryGoodDetailFormConvert {
    public static BookingInquiry convert(BookingInquiryGoodDetailForm bookingInquiryGoodDetailForm) {
        Gson gson = new Gson();
        BookingInquiry bookingInquiry = new BookingInquiry();
        BeanUtils.copyProperties(bookingInquiryGoodDetailForm,bookingInquiry);
        List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList = new ArrayList<>();
        if (StringUtils.isEmpty(bookingInquiryGoodDetailForm.getBookingInquiryGoodsDetails())) {

        } else {
            bookingInquiryGoodsDetailsList = gson.fromJson(bookingInquiryGoodDetailForm.getBookingInquiryGoodsDetails(), new TypeToken<List<BookingInquiryGoodsDetails>>() {
            }.getType());
        }
        bookingInquiry.setBookingInquiryGoodsDetailsList(bookingInquiryGoodsDetailsList);
        return bookingInquiry;
    }

    public static BookingInquiryBackup convertBackup(BookingInquiryGoodDetailForm bookingInquiryGoodDetailForm) {
        Gson gson = new Gson();
        BookingInquiryBackup bookingInquiryBackup = new BookingInquiryBackup();
        BeanUtils.copyProperties(bookingInquiryGoodDetailForm, bookingInquiryBackup);
        List<BookingInquiryGoodsDetailsBackup> bookingInquiryGoodsDetailsListBackup = new ArrayList<>();
        if (StringUtils.isEmpty(bookingInquiryGoodDetailForm.getBookingInquiryGoodsDetails())) {

        } else {
            bookingInquiryGoodsDetailsListBackup = gson.fromJson(bookingInquiryGoodDetailForm.getBookingInquiryGoodsDetails(), new TypeToken<List<BookingInquiryGoodsDetailsBackup>>() {
            }.getType());
        }
        bookingInquiryBackup.setBookingInquiryGoodsDetailsBackupList(bookingInquiryGoodsDetailsListBackup);
        return bookingInquiryBackup;
    }
}
