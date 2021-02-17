package com.szhbl.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.qrcode.QRCodeUtils;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youxingyang
 * @Date 2017-5-9 下午1:15:46
 */
public class OrderNoticePDFUtils0 extends PdfTableMehhod{
    // WHITE, LIGHT_GRAY, GRAY, DARK_GRAY, BLACK, RED, PINK, ORANGE, YELLOW, GREEN, MAGENTA, CYAN, BLUE
    // 白色、  浅灰色、    灰色、 深灰色、   黑色、 红色、 粉色、 橙色、  黄色、   绿色、 洋红、    青色、 蓝色
//页眉事件
    public static class Header extends PdfPageEventHelper {
        public static PdfPTable header;

        public Header(PdfPTable header) {
            OrderNoticePDFUtils0.Header.header = header;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            //把页眉表格定位
            header.writeSelectedRows(0, -1, 75, 790, writer.getDirectContent());
        }

        /**
         * 设置页眉
         *
         * @param writer
         */
        public void setTableHeader(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
            PdfPTable table = new PdfPTable(3);
            table.setTotalWidth(480);
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            Image image01 = Image.getInstance(PdfConstants.HEADER_LOGO); //图片自己传
            image01.setWidthPercentage(80);
            image01.scalePercent(28);
            cell.addElement(image01);
            table.addCell(cell);
            PdfPCell cell2 = new PdfPCell();
            cell2.setColspan(2);
            cell2.setBorder(0);
            String string = "Zhengzhou—Europe International Block Train Shipping Order \n郑州—欧洲国际货运班列配舱通知\n";
            cell2.addElement(new Paragraph(string, PdfConstants.FONT));
            table.addCell(cell2);
            OrderNoticePDFUtils0.Header event = new OrderNoticePDFUtils0.Header(table);
            writer.setPageEvent(event);
        }
    }
    public static void createOrderNoticePDF0(BusiShippingorder order) throws Exception {
        Document document = new Document(PageSize.A4, 70, 70, 110, 70);
        // add index page.
        // pdf文件名
        String path = order.getOrderNumber() + ".pdf";
        String dir = PdfConstants.getNoticePDFPath();
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = dir + File.separator + path;
        FileOutputStream os = new FileOutputStream(path);
        PdfWriter writer = PdfWriter.getInstance(document, os);

        // 设置页面布局
        writer.setViewerPreferences(PdfWriter.PageLayoutOneColumn);
        // 为这篇文档设置页面事件(X/Y)
        writer.setPageEvent(new PageXofYTest());
        document.open();
        document.newPage();
        PdfPTable pdfPTable = new PdfPTable(1);
        // 添加页眉，事件的发生是在生成报告之后，写入到硬盘之前
        /*添加页眉*/
        Header headerTable = new Header(pdfPTable);
        headerTable.setTableHeader(writer);
        /*添加页脚*/
        //  Footer footerTable = new Footer(pdfPTable);
        //  footerTable.setTableFooter(writer, PdfConstants.FONT);
        document.add(pdfPTable);
        /*---------------------------------表格---------------------------------*/
        PdfPTable table = createTable(new float[]{155, 155, 155});

        /*1: 1 固定*/
        String a = "We have reserved space for your cargo （ 贵司在我司订舱已配好舱位）";
        table.addCell(createCell(a, PdfConstants.FONT, Element.ALIGN_LEFT, 3, false));
        /*4: 2-5*/
        String b1 = "TO（订舱方）:";
        String b2 = order.getClientUnit();
        table.addCell(createCell(b1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(b2, PdfConstants.FONT, Element.ALIGN_LEFT, 2,false));
        String c1 = "Space No.（舱位号）:";
        String c2 = order.getOrderNumber();
        table.addCell(createCell(c1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(c2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        String d1 = "Consignor（发货方）:";
        String d2 = order.getShipOrederName();
        table.addCell(createCell(d1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(d2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        String e1 = "Consignee（收货方）:";
        String e2 = order.getReceiveOrderName();
        table.addCell(createCell(e1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(e2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*10: 6-15*/
        /*合并列*/
        /*生成二维码图片*/
        String contents = order.getOrderId();
        String QRCodepath = PdfConstants.getQRcodePath();
        String url = QRCodepath+"\\"+c2+".jpg";
        QRCodeUtils.encodeQRCODE(contents, QRCodepath, c2);

        Image image = Image.getInstance(url);
        image.setAlignment(Image.ALIGN_CENTER);
        image.scalePercent(60);
        table.addCell(createCell_rowspan(image, PdfConstants.FONT, Element.ALIGN_CENTER, 10, false));
        /*1*/
        String f1 = "Departure Date（班列日期）:";
        String f2 = DateUtils.format(order.getClassDate());
        table.addCell(createCell(f1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(f2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*2*/
        String g1 = "Mark（唛头）:";
        String g2 = order.getGoodsMark();
        table.addCell(createCell(g1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(g2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*3*/
        String h1 = "Originating Station(上货站):";
        String h2 = order.getOrderUploadsite();
        table.addCell(createCell(h1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(h2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*4*/
        String i1 = "Destination  Station(下货站):";
        String i2 = order.getOrderUnloadsite();
        table.addCell(createCell(i1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(i2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*5*/
        String j1 = "Description of goods(品名):";
        String j2 = order.getGoodsName();
        table.addCell(createCell(j1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(j2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*6*/
        String k1 = "Packaging(包装形式):";
        String k2 = order.getGoodsPacking();
        table.addCell(createCell(k1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(k2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*7*/
        String l1 = "Stacking(是否可堆叠):";
        String l2 = order.getGoodsCannotpileup().equals("1")?"是":(
                        order.getGoodsCannotpileup().equals("2")?"自叠":(
                            order.getGoodsCannotpileup().equals("0")?"否":"-"));
        table.addCell(createCell(l1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(l2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*8*/
        String m1 = "quantity(数量):";
        String m2 = order.getGoodsNumber();
        table.addCell(createCell(m1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(m2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*9*/
        String n1 = "Weight(重量):";
        String n2 = order.getGoodsKGS();
        table.addCell(createCell(n1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(n2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*10*/
        String o1 = "Container quantity(箱量):";
        String o2 = order.getIsConsolidation().equals("0") ? order.getContainerBoxamount() : "LCL";
        table.addCell(createCell(o1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell(o2, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        /*7: 18-22*/
        /*0*/
        String p01 = "Service No.(业务编码):";
        String p02 = order.getClientYwNumber();
        table.addCell(createCell(p01, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(p02, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*1*/
        String p1 = "Container type(箱型):";
        String p2 = order.getIsConsolidation().equals("0") ? order.getContainerType() : "LCL";
        table.addCell(createCell(p1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(p2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*2*/
        String q1 = "size(尺寸):";
        String q2 = order.getIsConsolidation().equals("0") ? "FCL" : order.getGoodsStandard();
        table.addCell(createCell(q1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(q2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*3 固定*/
        String r1 = "Deadline(时间节点):";
        String r2 = "Please deliver your cargo to designated CY before the below specified time \"A\" (local time)<货物请于以下“A” 规定时间节点之前运到指定场站(当地时间)>去程整柜和散货于班列发出日前3天12点(其中周一和周日班列为前4天12点)运到指定堆场";
        table.addCell(createCell(r1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(r2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*4 固定*/
        String s1 = "送货地址:";
        String s2 = "郑欧班列多式联运场站（河南省郑州市航海东路第十八大街与经北四路交叉口东200米，郑欧商城进口商品体验中心向北500米）。";
        table.addCell(createCell(s1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(s2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*5 固定*/
        String t1 = "园区联系方式:";
        String t2 = "0371-55177817";
        table.addCell(createCell(t1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(t2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*6 固定*/
        String u1 = "园区工作时间:";
        String u2 = "周一到周日 8:00-11:30 14:00-18:00";
        table.addCell(createCell(u1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(u2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*7*/
        String v1 = "跟单员:";
        String v2 = order.getOrderMerchandiser();
        table.addCell(createCell(v1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(v2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*8*/
        if (order.getClassEastAndWest().equals("0")) {
            String w1 = "推荐人:";
            String w2 = order.getClientTjr();
            table.addCell(createCell(w1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
            table.addCell(createCell_colspan(w2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        }
        /*1-23*/
        String w = "A:送货到园区地址:";
        table.addCell(createCell_colspan(w, PdfConstants.FONT, Element.ALIGN_LEFT, 3, false));
        /*公章-地址*/
        /*1-24*/
        /*公章图片*/
        Image officialSeal = Image.getInstance(PdfConstants.SIGN);
        officialSeal.setAlignment(Image.ALIGN_CENTER);
        officialSeal.scalePercent(14); //依照比例缩放
        table.addCell(createCell(officialSeal, PdfConstants.FONT, Element.ALIGN_CENTER, false));
        /*路标图片*/
        Image routeSign = Image.getInstance(PdfConstants.IMAGE_1);
        routeSign.setAlignment(Image.ALIGN_CENTER);
        routeSign.scalePercent(53); //依照比例缩放
        table.addCell(createCell_colspan(routeSign, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));

        /*1-25 固定*/
        String x = "As a freight forwarding company,Zhengzhou Jutong-International Freight Forwarding Co., Ltd., is responsible for providing international " +
                "freight forwarding services, booking party should ensure that the documents are in conformity with actual cargo." +
                "\n郑州聚通国际货运代理有限公司作为货运代理公司，负责提供国际货物运输代理服务，订舱方对实际发运货物的单货相符负责。";
        table.addCell(createCell_colspan(x, PdfConstants.FONT, Element.ALIGN_LEFT, 3, false));
        /*1-26 固定*/
        String y = "Note : According to China customs regulations，please ensure the seal be locked at the 3rd Lock " +
                "Handle withround rivet on the locking device of the container rear end ( from left to right). " +
                "Please to Mark 3 on thepictures below." +
                "\n备注：中国海关规定，封锁应锁到集装箱后方箱门左起第三根杆把手加锁处（有圆铆钉把手）。请参照下图标注的3号位置.";
        table.addCell(createCell_colspan(y, PdfConstants.FONT_M, Element.ALIGN_LEFT, 3, false));
        /*1-27 固定*/
        String z1 = "Warning  : " +
                "\nA.Weight limit of the cargo in a 20' container is 21 tons. Weight limit of the cargo in a 40'container is 23 tons." +
                "\nA.20呎柜货重限重21吨，40呎柜货重限重23吨。" +
                "\nB.If the single piece of cargo weighs more than 100KG, packaging details and loading photo must be provided. " +
                "The container stuffing plan can be accepted only after the confirmation from ZIH." +
                "\nB.单件货重超过100KG,装箱前需提供包装明细和装箱照片。经陆港公司审核并符合铁路要求的，依照此方案进行装箱。" +
                "\nC.Security requirements of railway transportation: Cargo's center of gravity should be placed in the center of the container. " +
                "Unbalance loading and over loading is unacceptable,for example, one side of the container isheavier than the other side, " +
                "or one end of container is heavier than the other end. If the container isunbalanced or over loaded, we will need to do the " +
                "container stuffing again. The related unstuffing and stuffingfees (Resulting in an additional operating cost of 150 dollars) " +
                "incurred shall be at the booking party's cost.If there is a shift packing of goods, dumping, spilling and other safety hazards, " +
                "the goods reinforcement mustbe handled in accordance with the requirements of ZIH and ZIH can provide on-site technical packing service." +
                "\nC.铁路运输安全要求：货箱重心必须居中，绝对不能偏载、偏重、超重。不可出现货箱一头重一头轻或者一侧重一侧轻的偏载现象。如发生以上偏载、偏重、超重现象，" +
                "必须将货物掏出来重新装箱，所产生的掏、装箱等相关费用（导致额外的操作成本为150美元），由订舱方自行承担。若装箱货物存在移位、倾倒、溢出等安全隐患的，" +
                "必须按照陆港公司的要求对货物进行加固处理，我司提供现场装箱技术服务。";
        table.addCell(createCell_colspan(z1, PdfConstants.FONT_M, Element.ALIGN_LEFT,3, false));
        /*1-28 固定*/
        String z2 = "40尺开顶箱：2个侧壁，每侧侧壁4个锁杆需要4个锁具，每侧箱顶需要3个锁具，箱门需要1个锁具 。一共15个" +
                "\n20尺开顶箱：2个侧壁，每侧侧壁2-3个锁杆需要2-3个锁具，每侧箱顶需要2个锁具，箱门需要1个锁具 。一共是9个或者11个" +
                "\n40OT: Two sides, four locks on each side, 3 locks on the container top of each side and 1 lock on the door, totally 15 locks." +
                "\n20OT: Two sides, 2 or 3 locks on each side, 2 locks on the container top of each side and 1 lock on the door, totally 9 or 11 locks.";
        table.addCell(createCell_colspan(z2, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));

        /*3: 29-31 固定*/
        /*1:图片*/
        Image image_a = Image.getInstance(PdfConstants.IMAGE_3);
        image_a.setAlignment(Image.ALIGN_CENTER);
        image_a.scalePercent(20);
        table.addCell(createCell(image_a, PdfConstants.FONT, Element.ALIGN_CENTER, false));
        /*2:图片*/
        Image image_b = Image.getInstance(PdfConstants.IMAGE_4);
        image_b.setAlignment(Image.ALIGN_CENTER);
        image_b.scalePercent(20);
        table.addCell(createCell(image_b, PdfConstants.FONT, Element.ALIGN_CENTER, false));
        /*3:图片*/
        Image image_c = Image.getInstance(PdfConstants.IMAGE_5);
        image_c.setAlignment(Image.ALIGN_CENTER);
        image_c.scalePercent(20);
        table.addCell(createCell(image_c, PdfConstants.FONT, Element.ALIGN_CENTER, false));
        /*1-32*/
        /*1:图片  同第一个公章图片一致*/
        table.addCell(createCell(officialSeal, PdfConstants.FONT, Element.ALIGN_CENTER, false));
        /*2:图片*/
        Image image_e = Image.getInstance(PdfConstants.IMAGE_6);
        image_e.setAlignment(Image.ALIGN_CENTER);
        image_e.scalePercent(90);
        table.addCell(createCell_colspan(image_e, PdfConstants.FONT, Element.ALIGN_CENTER,2, false));
        /*1-33*/
        Image image_f = Image.getInstance(PdfConstants.IMAGE_7);
        image_f.setAlignment(Image.ALIGN_CENTER);
        image_f.scalePercent(88);
        table.addCell(createCell_colspan(image_f, PdfConstants.FONT, Element.ALIGN_CENTER,3, false));

        /*1-34 固定*/
        String z3 = "Note :Please deliver the bulk cargo to the designated terminal with this document and hand it over to personnel in terminal. " +
                "\n备注：请在送货到指定场站时携带本文件，并交给场站工作人员。\n" +
                "Note : If you would cancel this booking, please notify us 7 days before train departure. Or else, ZIH will charge you for dead freight. " +
                "\n备注：如果取消订舱请于班列开行前7天通知，否则陆港公司会收取亏舱费。 ";
        table.addCell(createCell_colspan(z3, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*1-35 固定*/
        SimpleDateFormat sdf =  new SimpleDateFormat("dd MM, yyyy");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        table.addCell(createCell_colspan("", PdfConstants.FONT, Element.ALIGN_CENTER,2, false));
        table.addCell(createCell(date, PdfConstants.FONT_S, Element.ALIGN_LEFT, false));

        document.add(table);
        document.close();
        /**/
        os.close();
    }
}
