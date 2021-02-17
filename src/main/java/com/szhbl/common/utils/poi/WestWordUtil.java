package com.szhbl.common.utils.poi;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.itext.WordContents;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * <p> 创建word文档 </p>
 *
 * @description : 步骤:
 *                  1、建立文档
 *                  2、创建一个书写器
 *                  3、打开文档
 *                  4、向文档中写入数据
 *                  5、关闭文档
 */
@Slf4j
public class WestWordUtil {
    public static String toWord(BookingInquiryResult bookingInquiryResult, BookingInquiry bookingInquiry) {
        // tableToWordUtil.toWord(tables, docFileName, Constants.FILE_NAME);
        //(List<Tables> tables, String fileName, String title)
        String filePath = "E:\\word";
        String fileName = filePath + "\\" + System.currentTimeMillis() + "询价结果.doc";

        Document document = new Document(PageSize.A4);
        try {
            int i=0;
            // 创建文件夹
            File dir = new File(filePath);
            dir.mkdirs();

            // 创建文件
            File file = new File(filePath+"\\"+System.currentTimeMillis()+"询价结果.doc");
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            file.createNewFile();
            // 写入文件信息
            RtfWriter2.getInstance(document, new FileOutputStream(fileName));
            document.open();

            String sendCity = StringUtils.isNotEmpty(bookingInquiryResult.getPickUpAddress())?(StringUtils.isNotEmpty(bookingInquiry.getSenderProvince())?(bookingInquiry.getSenderProvince() + bookingInquiry.getSenderCity() +bookingInquiry.getSenderArea() + bookingInquiryResult.getPickUpAddress()) : bookingInquiryResult.getPickUpAddress()) : bookingInquiryResult.getUploadStation();
            String receiveCity = StringUtils.isNotEmpty(bookingInquiryResult.getDeliveryAddress())?(StringUtils.isNotEmpty(bookingInquiry.getReceiveProvince())?bookingInquiry.getReceiveProvince() + bookingInquiry.getReceiveCity() +bookingInquiry.getReceiveArea() + bookingInquiryResult.getDeliveryAddress() : bookingInquiryResult.getDeliveryAddress()) : bookingInquiryResult.getDropStation();
            boolean isDelivery = "1".equals(bookingInquiry.getIsDelivery());
            boolean isPickUp = "0".equals(bookingInquiry.getIsPickUp());

            Paragraph sendPlace = new Paragraph("发货地：",getTitleFont());
            document.add(sendPlace);
            Paragraph senderCity = new Paragraph(sendCity, getFeeNoFont());
            senderCity.setAlignment(0);
            document.add(senderCity);
            document.add(new Paragraph(""));
            Paragraph receivePlace = new Paragraph("收货地：", getTitleFont());
            receivePlace.setAlignment(0);
            document.add(receivePlace);
            Paragraph receiverCity = new Paragraph(receiveCity,getFeeNoFont());
            document.add(receiverCity);
            document.add(new Paragraph(""));

            //提货费
            if(isPickUp){
                Paragraph pickupFee = new Paragraph("提货费/Pickup Fee:", getTitleFont());
                document.add(pickupFee);
                Paragraph pickupFeeNum = new Paragraph("提货费报价编号/PickupFee No: "+bookingInquiryResult.getInquiryNumber(),getFeeNoFont());
                document.add(pickupFeeNum);

                String pickupAddress=StringUtils.isNotEmpty(bookingInquiry.getSenderProvince())?(bookingInquiry.getSenderProvince() + bookingInquiry.getSenderCity()
                        +bookingInquiry.getSenderArea() + bookingInquiryResult.getPickUpAddress()):bookingInquiryResult.getPickUpAddress();

                i=4;
                if("1".equals(bookingInquiry.getGoodsType())){
                    i+=1;
                }

                Table table = getTable(i);
                //添加表头的元素，并设置表头背景的颜色
                Color chade = getTableHeaderColor();
                Cell cell = new Cell("提货地/\npickupAddress");
                addCell(table, cell, chade);
                cell = new Cell("价格/Price");
                addCell(table, cell, chade);
                cell = new Cell("币种/Currency");
                addCell(table, cell, chade);
                if("1".equals(bookingInquiry.getGoodsType())){
                    cell = new Cell("是否堆叠/Stack");
                    addCell(table, cell, chade);
                }
                cell = new Cell("备注/Remark");
                addCell(table, cell, chade);
                table.endHeaders();

                // 表格的主体
                /*提货地*/
                addContent(table, cell, pickupAddress);
                /*价格*/
                addContent(table, cell, bookingInquiryResult.getPickUpFees());
                /*币种*/
                addContent(table, cell, bookingInquiryResult.getPickUpCurrencyType());
                /*是否堆叠*/
                if("1".equals(bookingInquiry.getGoodsType())){
                    addContent(table, cell, "1".equals(bookingInquiry.getIsStack())?"是":"否");
                }
                /*备注*/
                addContent(table, cell, bookingInquiryResult.getXxyoRemark());
                //生成表格
                document.add(table);

                document.add(new Paragraph(""));
            }

            //铁路运费
            Paragraph railFee = new Paragraph("铁路运费/Rail Freight: ", getTitleFont());
            document.add(railFee);
            i=4;
            if(!isPickUp &&"0".equals(bookingInquiry.getGoodsType()) && "1".equals(bookingInquiry.getDeliverySelfType())){
                i+=2;
            }
            if(!isDelivery &&"0".equals(bookingInquiry.getGoodsType())){
                i+=2;
            }

            Table table = getTable(i);
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
            if (!isPickUp &&"0".equals(bookingInquiry.getGoodsType()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                cell = new Cell("提箱地/Suitcase");
                addCell(table, cell, chade);
                cell = new Cell("提箱平衡费/\nSuitcase Balance Fee");
                addCell(table, cell, chade);
            }
            if (!isDelivery &&"0".equals(bookingInquiry.getGoodsType())) {
                cell = new Cell("还箱地/\nplaceOfReturn");
                addCell(table, cell, chade);
                cell = new Cell("还箱平衡费/\nReturn Balance Fee");
                addCell(table, cell, chade);
            }
            table.endHeaders();

            // 表格的主体
            /*上/下货站*/
            addContent(table, cell, bookingInquiryResult.getUploadStation() + " / " + bookingInquiryResult.getDropStation());
            /*价格*/
            addContent(table, cell, bookingInquiryResult.getRailwayFees());
            /*币种*/
            addContent(table, cell, bookingInquiryResult.getRailwayCurrencyType());
            /*时效*/
            addContent(table, cell, bookingInquiryResult.getRailwayAging());
            if (!isPickUp &&"0".equals(bookingInquiry.getGoodsType()) && "1".equals(bookingInquiry.getDeliverySelfType())) {
                /*提箱地*/
                addContent(table, cell, bookingInquiryResult.getTxAddress());
                /*提箱平衡费*/
                addContent(table, cell, bookingInquiryResult.getPickUpBoxFee());
            }
            if (!isDelivery &&"0".equals(bookingInquiry.getGoodsType())) {
                /*还箱地*/
                addContent(table, cell, bookingInquiryResult.getHxAddress());
                /*还箱平衡费*/
                addContent(table, cell, bookingInquiryResult.getReturnBoxFee());
            }
            document.add(table);

            String tip="0".equals(bookingInquiry.getGoodsType())? WordContents.tip2+WordContents.tip3:WordContents.tip2;
            Paragraph tipPara = new Paragraph(tip, getRemarkFont());
            document.add(tipPara);
            document.add(new Paragraph(""));

            //派送费
            if(isDelivery){
                Paragraph pickupFee = new Paragraph("派送费/Delivery Fee: ", getTitleFont());
                document.add(pickupFee);
                Paragraph pickupFeeNum = new Paragraph("派送费报价编号/DeliveryFee No: "+bookingInquiryResult.getInquiryNum(),getFeeNoFont());
                document.add(pickupFeeNum);

                i=5;
                if("0".equals(bookingInquiry.getGoodsType())){
                    i+=2;
                }

                table = getTable(i);
                //添加表头的元素，并设置表头背景的颜色
                chade = getTableHeaderColor();
                cell = new Cell("送货地\n/SenderAddress");
                addCell(table, cell, chade);
                cell = new Cell("价格/Price");
                addCell(table, cell, chade);
                cell = new Cell("币种/Currency");
                addCell(table, cell, chade);
                cell = new Cell("时效/Aging");
                addCell(table, cell, chade);
                if("0".equals(bookingInquiry.getGoodsType())){
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
                addContent(table, cell, bookingInquiry.getReceiptPlace());
                /*价格*/
                addContent(table, cell, bookingInquiryResult.getDeliveryFees());
                /*币种*/
                addContent(table, cell, bookingInquiryResult.getDeliveryCurrencyType());
                /*时效*/
                addContent(table, cell, bookingInquiryResult.getDeliveryAging());
                if("0".equals(bookingInquiry.getGoodsType())){
                    addContent(table, cell, bookingInquiryResult.getHxAddress());
                    addContent(table, cell, bookingInquiryResult.getReturnBoxFee());
                }
                /*备注*/
                addContent(table, cell, bookingInquiryResult.getDeliveryRemark());
                //生成表格
                document.add(table);

                Paragraph jsRemark = new Paragraph(bookingInquiryResult.getJsRemark(), getRemarkFont());
                document.add(jsRemark);
            }
            document.close();
        } catch (Exception e) {
            log.error("导出word失败：{}",e.toString(),e.getStackTrace());
        }
        return fileName;
    }

    /**
     * 添加表头到表格
     *
     * @param table
     * @param cell
     * @param chade
     */
    private static void addCell(Table table, Cell cell, Color chade) {
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
    private static void addContent(Table table, Cell cell, String content) {
        cell = new Cell(content);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }


    private static Table getTable(int column) throws BadElementException {
        Table table = new Table(column);
        table.setBorder(-1);
        table.setWidth(100);
        table.setPadding(0);
        table.setSpacing(0);
        return table;
    }

    private static Font getTitleFont() {
        return new Font(Font.NORMAL, 12, Font.BOLDITALIC, new Color(10, 18, 83));
    }

    private static Font getFeeNoFont() {
        return new Font(Font.NORMAL, (float) 11.5, Font.BOLD, new Color(0, 0, 0));
    }

    private static Font getRemarkFont() { return new Font(Font.NORMAL, (float) 10.5, Font.NORMAL, new Color(0, 0, 0)); }

    private static Color getTableHeaderColor() {
        return new Color(214, 209, 222);
    }

}
