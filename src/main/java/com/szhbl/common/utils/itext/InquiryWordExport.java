package com.szhbl.common.utils.itext;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.szhbl.common.utils.file.FileUploadUtils;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description : 询价结果word导出
 * @Author : wangzhichao
 * @Date: 2020-08-12 15:59
 */
public class InquiryWordExport {
    private final String ZERO_STR = "0";
    private final String ONE_STR = "1";

    /**
     * 生成word文档
     *
     * @return: void
     */
    public AjaxResult toWord(BookingInquiryResult inquiryResult) {
        BookingInquiry bookingInquiry = inquiryResult.getBookingInquiry();

        Document document = new Document(PageSize.A4);
        try {
            // 创建文件夹
            String path = WordContents.getInquiryResultPath();
            File paths = new File(path);
            if (!paths.exists()) {
                paths.mkdirs();
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            String fileName = format.format(new Date()) + "询价结果.doc";
            path = path + "/" + fileName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            // 写入文件信息
            RtfWriter2.getInstance(document, new FileOutputStream(path));
            document.open();
            /*空行*/
            document.add(new Paragraph(""));
            /*标题*/
            Paragraph pheae1 = new Paragraph("发货地：", getTitleFont());
            document.add(pheae1);

            String senderCity = getSenderCity(inquiryResult, bookingInquiry);
            Paragraph p2 = new Paragraph(senderCity, getFeeNoFont());
            p2.setAlignment(0);
            p2.setFirstLineIndent(25);
            document.add(p2);

            /*空行*/
            document.add(new Paragraph(""));

            Paragraph pheae2 = new Paragraph("收货地：", getTitleFont());
            document.add(pheae2);

            String receiveCity = getReceiveCity(inquiryResult, bookingInquiry);
            Paragraph p3 = new Paragraph(receiveCity, getFeeNoFont());
            p3.setAlignment(0);
            p3.setFirstLineIndent(25);
            document.add(p3);

            if (ZERO_STR.equals(bookingInquiry.getIsPickUp())) {
                /*空行*/
                document.add(new Paragraph(""));
                /*提货费*/
                document = pickUpFeeTable(document, inquiryResult, bookingInquiry);
            }
            /*空行*/
            document.add(new Paragraph(""));
            /*铁路*/
            document = railWayFeeTable(document, inquiryResult, bookingInquiry);

            if (ONE_STR.equals(bookingInquiry.getIsDelivery())) {
                /*空行*/
                document.add(new Paragraph(""));
                /*送货费*/
                document = deliveryFeeTable(document, inquiryResult, bookingInquiry);
            }
            document.close();
            path = FileUploadUtils.getPathFileName(WordContents.getInquiryResultPath(), fileName);
            path = SzhblConfig.getFileServer() + path;
            return AjaxResult.success(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("下载失败，请重试！Download failed, please try again!");
    }

    /**
     * 添加表头到表格
     *
     * @param table
     * @param cell
     * @param chade
     */
    private void addCell(Table table, Cell cell, Color chade) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(chade);
        table.addCell(cell);
    }

    /**
     * 添加内容到表格
     *
     * @param table
     * @param content
     */
    private void addContent(Table table, Cell cell, String content) {
        cell = new Cell(content);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    /**
     * 提货费table
     *
     * @param document
     * @param inquiryResult
     * @param bookingInquiry
     * @return
     * @throws DocumentException
     */
    public Document pickUpFeeTable(Document document, BookingInquiryResult inquiryResult, BookingInquiry bookingInquiry) throws DocumentException {
        String goodsType = bookingInquiry.getGoodsType();
        boolean isPickUp = ZERO_STR.equals(bookingInquiry.getIsPickUp());
        String deliveryType = bookingInquiry.getDeliveryType();
        String deliverySelfType = bookingInquiry.getDeliverySelfType();
        Paragraph pheae1 = new Paragraph("提货费/Pickup Fee: ", getTitleFont());
        document.add(pheae1);
        Paragraph pheae2 = new Paragraph("提货费报价编号/PickupFee No: " + inquiryResult.getInquiryNum(), getFeeNoFont());
        pheae2.setFirstLineIndent(25);
        document.add(pheae2);

        /*计算列数*/
        int column = 9;
        if (!(isPickUp && ZERO_STR.equals(goodsType) && ZERO_STR.equals(deliveryType))) {
            column -= 2;
        }
        if (!(!isPickUp && ZERO_STR.equals(goodsType) && ZERO_STR.equals(deliveryType))) {
            column -= 2;
        }

        Table table = getTable(column);

        //添加表头的元素，并设置表头背景的颜色
        Color chade = getTableHeaderColor();
        Cell cell = new Cell("提货地/\npickupAddress");
        addCell(table, cell, chade);

        cell = new Cell("价格/Price");
        addCell(table, cell, chade);

        cell = new Cell("币种/Currency");
        addCell(table, cell, chade);

        if (isPickUp && ZERO_STR.equals(goodsType) && ZERO_STR.equals(deliveryType)) {
            cell = new Cell("提箱地/Suitcase");
            addCell(table, cell, chade);
            cell = new Cell("提箱平衡费/\nSuitcase Balance Fee");
            addCell(table, cell, chade);
        }
        cell = new Cell("时效(天)/Aging(day)");
        addCell(table, cell, chade);
        if (!isPickUp && ZERO_STR.equals(goodsType) && ONE_STR.equals(deliverySelfType)) {
            cell = new Cell("提箱地/Suitcase");
            addCell(table, cell, chade);
            cell = new Cell("提箱平衡费/\nSuitcase Balance Fee");
            addCell(table, cell, chade);
        }
        cell = new Cell("备注/Remark");
        addCell(table, cell, chade);

        table.endHeaders();
        // 表格的主体
        /*提货地*/
        addContent(table, cell, bookingInquiry.getShipmentPlace());
        /*价格*/
        addContent(table, cell, inquiryResult.getPickUpFees());
        /*币种*/
        addContent(table, cell, inquiryResult.getPickUpCurrencyType());
        if (isPickUp && ZERO_STR.equals(goodsType) && ZERO_STR.equals(deliveryType)) {
            /*提箱地*/
            addContent(table, cell, inquiryResult.getTxAddress());
            /*提箱平衡费*/
            addContent(table, cell, inquiryResult.getPickUpBoxFee());
        }
        /*时效(天)*/
        addContent(table, cell, inquiryResult.getPickUpAging());
        if (!isPickUp && ZERO_STR.equals(goodsType) && ZERO_STR.equals(deliveryType)) {
            /*提箱地*/
            addContent(table, cell, inquiryResult.getTxAddress());
            /*提箱平衡费*/
            addContent(table, cell, inquiryResult.getPickUpBoxFee());
        }
        /*备注*/
        addContent(table, cell, inquiryResult.getPickUpRemark());

        //生成表格
        document.add(table);

        Paragraph jsRemark = new Paragraph(inquiryResult.getJsRemark(), getRemarkFont());
        jsRemark.setFirstLineIndent(25);
        document.add(jsRemark);

        return document;

    }

    /**
     * 铁路费table
     *
     * @param document
     * @param inquiryResult
     * @param bookingInquiry
     * @return
     * @throws DocumentException
     */
    public Document railWayFeeTable(Document document, BookingInquiryResult inquiryResult, BookingInquiry bookingInquiry) throws DocumentException {
        String goodsType = bookingInquiry.getGoodsType();
        boolean isPickUp = ZERO_STR.equals(bookingInquiry.getIsPickUp());
        String deliverySelfType = bookingInquiry.getDeliverySelfType();
        String eastOrWest = bookingInquiry.getEastOrWest();
        Paragraph pheae = new Paragraph("铁路运费/Rail Freight: ", getTitleFont());
        //写入表说明
        document.add(pheae);

        /*计算列数*/
        int column = 9;
        if (!ONE_STR.equals(goodsType)) {
            column--;
        }
        if (!(!isPickUp && ZERO_STR.equals(goodsType) && ONE_STR.equals(deliverySelfType))) {
            column -= 2;
        }
        if (!(!isPickUp && ZERO_STR.equals(goodsType))) {
            column -= 2;
        }

        Table table = getTable(column);

        //添加表头的元素，并设置表头背景的颜色
        Color chade = getTableHeaderColor();
        Cell cell = new Cell("上/下货站/\nLoadingStation/UnloadingStation");
        addCell(table, cell, chade);
        cell = new Cell("价格/Price");
        addCell(table, cell, chade);
        cell = new Cell("币种/Currency");
        addCell(table, cell, chade);
        cell = new Cell("时效/Aging");
        addCell(table, cell, chade);
        if (ONE_STR.equals(goodsType)) {
            cell = new Cell("备注/Remark");
            addCell(table, cell, chade);
        }
        if (!isPickUp && ZERO_STR.equals(goodsType) && ONE_STR.equals(deliverySelfType)) {
            cell = new Cell("提箱地/Suitcase");
            addCell(table, cell, chade);
            cell = new Cell("提箱平衡费/\nSuitcase Balance Fee");
            addCell(table, cell, chade);
        }
        if (!isPickUp && ZERO_STR.equals(goodsType)) {
            cell = new Cell("还箱地/\nplaceOfReturn");
            addCell(table, cell, chade);
            cell = new Cell("还箱平衡费/\nReturn Balance Fee");
            addCell(table, cell, chade);
        }
        table.endHeaders();
        // 表格的主体
        /*上/下货站*/
        addContent(table, cell, inquiryResult.getUploadStation() + " / " + inquiryResult.getDropStation());
        /*价格*/
        addContent(table, cell, inquiryResult.getRailwayFees());
        /*币种*/
        addContent(table, cell, inquiryResult.getRailwayCurrencyType());
        /*时效*/
        addContent(table, cell, inquiryResult.getRailwayAging());
        if (ONE_STR.equals(goodsType)) {
            /*备注——收费标准*/
            addContent(table, cell, "散货铁路运费需经堆场实际测量后给出准确价格");
        }
        if (!isPickUp && ZERO_STR.equals(goodsType) && ONE_STR.equals(deliverySelfType)) {
            /*提箱地*/
            addContent(table, cell, inquiryResult.getTxAddress());
            /*提箱平衡费*/
            addContent(table, cell, inquiryResult.getPickUpBoxFee());
        }
        if (!isPickUp && ZERO_STR.equals(goodsType)) {
            /*还箱地*/
            addContent(table, cell, inquiryResult.getHxAddress());
            /*还箱平衡费*/
            addContent(table, cell, inquiryResult.getReturnBoxFee());
        }
        //生成表格
        document.add(table);
        String flag = eastOrWest + goodsType;
        switch (flag) {
            case "00":
                Paragraph pheae00 = new Paragraph(WordContents.tip2 + WordContents.tip3, getRemarkFont());
                pheae00.setFirstLineIndent(25);
                document.add(pheae00);
                break;
            case "01":
                Paragraph pheae01 = new Paragraph(WordContents.tip2, getRemarkFont());
                pheae01.setFirstLineIndent(25);
                document.add(pheae01);
                break;
            case "10":
                Paragraph pheae10 = new Paragraph(WordContents.tip4 + WordContents.tip5, getRemarkFont());
                pheae10.setFirstLineIndent(25);
                document.add(pheae10);
                break;
            case "11":
                Paragraph pheae11 = new Paragraph(WordContents.tip4, getRemarkFont());
                pheae11.setFirstLineIndent(25);
                document.add(pheae11);
                break;
        }
        return document;
    }

    /**
     * 派送费table
     *
     * @param document
     * @param inquiryResult
     * @param bookingInquiry
     * @return
     * @throws DocumentException
     */
    public Document deliveryFeeTable(Document document, BookingInquiryResult inquiryResult, BookingInquiry bookingInquiry) throws DocumentException {
        String goodsType = bookingInquiry.getGoodsType();
        Paragraph pheae1 = new Paragraph("派送费/Delivery Fee: ", getTitleFont());
        document.add(pheae1);
        Paragraph pheae2 = new Paragraph("派送费报价编号/DeliveryFee No: " + inquiryResult.getInquiryNumber(), getFeeNoFont());
        pheae2.setFirstLineIndent(25);
        document.add(pheae2);

        /*计算列数*/
        int column = 6;
        if (!ZERO_STR.equals(goodsType)) {
            column -= 2;
        }
        Table table = getTable(column);
        //添加表头的元素，并设置表头背景的颜色
        Color chade = getTableHeaderColor();

        Cell cell = new Cell("送货地\n/SenderAddress");
        addCell(table, cell, chade);

        cell = new Cell("价格/Price");
        addCell(table, cell, chade);

        cell = new Cell("币种/Currency");
        addCell(table, cell, chade);

        if (ZERO_STR.equals(goodsType)) {
            cell = new Cell("还箱地/\nplaceOfReturn");
            addCell(table, cell, chade);
            cell = new Cell("还箱平衡费/\nReturn Balance Fee");
            addCell(table, cell, chade);
        }
        cell = new Cell("备注/Remark");
        addCell(table, cell, chade);
        table.endHeaders();
        // 表格的主体
        /*送货地*/
        addContent(table, cell, getSenderAddress(bookingInquiry));
        /*价格*/
        addContent(table, cell, inquiryResult.getDeliveryFees());
        /*币种*/
        addContent(table, cell, inquiryResult.getDeliveryCurrencyType());
        if (ZERO_STR.equals(goodsType)) {
            /*还箱地*/
            addContent(table, cell, inquiryResult.getHxAddress());
            /*还箱地衡费*/
            addContent(table, cell, inquiryResult.getReturnBoxFee());
        }
        /*备注*/
        addContent(table, cell, inquiryResult.getXxyoRemark());
        //生成表格
        document.add(table);

        return document;
    }

    public String getSenderAddress(BookingInquiry bookingInquiry) {
        String senderrAddress = "";
        if (!StringUtils.isEmpty(bookingInquiry.getReceiveProvince())) {
            senderrAddress = bookingInquiry.getReceiveProvince()
                    + bookingInquiry.getReceiveCity()
                    + bookingInquiry.getReceiveArea()
                    + bookingInquiry.getReceiptPlace();
        } else {
            senderrAddress = bookingInquiry.getReceiptPlace();
        }
        return senderrAddress;
    }

    public String getSenderCity(BookingInquiryResult inquiryResult, BookingInquiry bookingInquiry) {
        String senderCity = "";
        if (!StringUtils.isEmpty(inquiryResult.getPickUpAddress())) {
            if (!StringUtils.isEmpty(bookingInquiry.getSenderProvince())) {
                senderCity = bookingInquiry.getSenderProvince()
                        + bookingInquiry.getSenderCity()
                        + bookingInquiry.getSenderArea()
                        + inquiryResult.getPickUpAddress();
            } else {
                senderCity = inquiryResult.getPickUpAddress();
            }
        } else {
            senderCity = inquiryResult.getUploadStation();
        }
        return senderCity;
    }

    public String getReceiveCity(BookingInquiryResult inquiryResult, BookingInquiry bookingInquiry) {
        String receiveCity = "";
        if (!StringUtils.isEmpty(inquiryResult.getDeliveryAddress())) {
            if (!StringUtils.isEmpty(bookingInquiry.getReceiveProvince())) {
                receiveCity = bookingInquiry.getReceiveProvince()
                        + bookingInquiry.getReceiveCity()
                        + bookingInquiry.getReceiveArea()
                        + inquiryResult.getDeliveryAddress();
            } else {
                receiveCity = inquiryResult.getDeliveryAddress();
            }
        } else {
            receiveCity = inquiryResult.getDropStation();
        }
        return receiveCity;
    }

    private Table getTable(int column) throws BadElementException {
        Table table = new Table(column);
        table.setWidth(100);
        table.setBorder(-1);
        table.setPadding(0);
        table.setSpacing(0);
        return table;
    }

    private Font getTitleFont() {
        return new Font(Font.NORMAL, 12, Font.BOLDITALIC, new Color(10, 18, 83));
    }

    private Font getFeeNoFont() {
        return new Font(Font.NORMAL, (float) 11.5, Font.BOLD, new Color(0, 0, 0));
    }

    private Font getRemarkFont() {
        return new Font(Font.NORMAL, (float) 10.5, Font.NORMAL, new Color(0, 0, 0));
    }

    private Color getTableHeaderColor() {
        return new Color(214, 209, 222);
    }
}
