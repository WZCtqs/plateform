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
public class OrderNoticePDFUtils1 extends PdfTableMehhod{
    //页眉事件
    public static class Header extends PdfPageEventHelper {
        public static PdfPTable header;

        public Header(PdfPTable header) {
            OrderNoticePDFUtils1.Header.header = header;
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
            OrderNoticePDFUtils1.Header event = new OrderNoticePDFUtils1.Header(table);
            writer.setPageEvent(event);
        }
    }

    // WHITE, LIGHT_GRAY, GRAY, DARK_GRAY, BLACK, RED, PINK, ORANGE, YELLOW, GREEN, MAGENTA, CYAN, BLUE
    // 白色、  浅灰色、    灰色、 深灰色、   黑色、 红色、 粉色、 橙色、  黄色、   绿色、 洋红、    青色、 蓝色
    public static void createOrderNoticePDF1(BusiShippingorder order) throws Exception {
        Document document = new Document(PageSize.A4, 70, 70, 110, 70);
        // add index page.
        String path = order.getOrderNumber()+".pdf";
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

        /*19: 1-19*/
        /*1*/
        String a = "We have reserved space for your cargo （ 贵司在我司订舱已配好舱位）";
        table.addCell(createCell(a, PdfConstants.FONT, Element.ALIGN_LEFT, 3, false));
        /*2*/
        String b1 = "TO（订舱方）:";
        String b2 = order.getClientUnit();
        table.addCell(createCell(b1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(b2, PdfConstants.FONT, Element.ALIGN_LEFT, 2,false));
        /*3*/
        String c1 = "Space No.（舱位号）:";
        String c2 = order.getOrderNumber();
        table.addCell(createCell(c1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(c2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*4*/
        String d1 = "Consignor（发货方）:";
        String d2 = order.getShipOrederName();
        table.addCell(createCell(d1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(d2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*5*/
        String e1 = "Consignee（收货方）:";
        String e2 = order.getReceiveOrderName();
        table.addCell(createCell(e1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(e2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*6*/
        String f1 = "Departure Date（班列日期）:";
        String f2 = DateUtils.format(order.getClassDate());
        table.addCell(createCell(f1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(f2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*7*/
        String g1 = "Mark（唛头）:";
        String g2 = order.getGoodsMark();
        table.addCell(createCell(g1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(g2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*8*/
        String h1 = "Originating Station(上货站):";
        String h2 = order.getOrderUploadsite();
        table.addCell(createCell(h1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(h2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*9*/
        String i1 = "Destination  Station(下货站):";
        String i2 = order.getOrderUnloadsite();
        table.addCell(createCell(i1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(i2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*10*/
        String j1 = "Container quantity(箱量):";
        String j2 = order.getContainerBoxamount();
        table.addCell(createCell(j1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(j2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*11*/
        String k1 = "Container type(箱型):";
        String k2 = order.getContainerType();
        table.addCell(createCell(k1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(k2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*11-01*/
        String k01 = "Service No.(业务编码):";
        String k02 = order.getClientYwNumber();
        table.addCell(createCell(k01, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(k02, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*12*/
        String l1 = "Description of goods&PCs(品名&件数):";
        String l2 = order.getGoodsName() + "  *  " + order.getGoodsNumber();
        table.addCell(createCell(l1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(l2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*13*/
        String m1 = order.getIsConsolidation().equals("0") ? "size(尺寸):" : "Specification(总体积):";
        String m2 = order.getIsConsolidation().equals("0") ? order.getGoodsStandard() : order.getGoodsCBM();
        table.addCell(createCell(m1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(m2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*14*/
        String n1 = "Total Gross Weight(KGS)(总毛重):";
        String n2 = order.getGoodsKGS();
        table.addCell(createCell(n1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(n2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*15*/
        String o1 = "Deadline（时间节点）:";
        String o2 = "Please deliver your cargo to designated CY before the below specified time \"A\" (local time)<货物请于以下“A” 规定时间节点之前运到指定场站(当地时间)>去程整柜和散货于班列发出日前3天12点(其中周一和周日班列为前4天12点)运到指定堆场";
        table.addCell(createCell(o1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(o2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*16*/
        String p1 = "Station name（车站名称）:";
        String p2 = order.getStatioin();
        table.addCell(createCell(p1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(p2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*17*/
        String q1 = "Closing time（截港时间）:";
        String q2 = order.getCuntofftime();
        table.addCell(createCell(q1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(q2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*18*/
        String r1 = "Station address（车站地址）:";
        String r2 = order.getPxYstype();
        table.addCell(createCell(r1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(r2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));
        /*19*/
        String s1 = "Contact information（联系方式）:";
        String s2 = HtmlParser.Html2Text(order.getContacts());
        table.addCell(createCell(s1, PdfConstants.FONT, Element.ALIGN_LEFT, false));
        table.addCell(createCell_colspan(s2, PdfConstants.FONT, Element.ALIGN_LEFT, 2, false));

        /*公章-地址*/
        /*20*/
        /*id图片*/
        /*生成二维码图片*/
        String contents = order.getOrderId();
        String QRCodepath = PdfConstants.getQRcodePath();
        String url = QRCodepath+"\\"+c2+".jpg";
        QRCodeUtils.encodeQRCODE(contents, 300, 300, "jpg", QRCodepath, c2);
        Image idImage = Image.getInstance(url);
        idImage.setAlignment(Image.ALIGN_CENTER);
        idImage.scalePercent(60); //依照比例缩放
        table.addCell(createCell(idImage, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*若为慕尼黑，多一张路标图片*/
        if(order.getIsConsolidation().equals("0")&&order.getOrderUploadsite().equals("慕尼黑")){
            Image routeSign = Image.getInstance(PdfConstants.MNH);
            routeSign.setAlignment(Image.ALIGN_CENTER);
            routeSign.scalePercent(15); //依照比例缩放
            table.addCell(createCell(routeSign, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        } else {
            table.addCell(createCell("", PdfConstants.FONT, Element.ALIGN_CENTER, false));
        }

        /*公章图片*/
        Image officialSeal = Image.getInstance(PdfConstants.SIGN);
        officialSeal.setAlignment(Image.ALIGN_CENTER);
        officialSeal.scalePercent(14); //依照比例缩放
        table.addCell(createCell(officialSeal, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*21*/
        String t = "As a freight forwarding company,Zhengzhou Jutong-International Freight Forwarding Co., Ltd., is responsible for providing international " +
                "freight forwarding services, booking party should ensure that the documents are in conformity with actual cargo." +
                "\n郑州聚通国际货运代理有限公司作为货运代理公司，负责提供国际货物运输代理服务，订舱方对实际发运货物的单货相符负责。";
        table.addCell(createCell_colspan(t, PdfConstants.FONT, Element.ALIGN_LEFT, 3, false));

        /*22*/
        String u = "Note : Please prepare the relevant documents after you receive the shipping order. You should provide all customs documents 3 days before train departure." +
                "\n备注：接到入货单后，请开始准备相关单证，于班列开行前3天提供整套报关报检资料。";
        table.addCell(createCell_colspan(u, PdfConstants.FONT_M, Element.ALIGN_LEFT, 3, false));
        /*23*/
        String v1 = "The Deadline（working time, N is the departure date of the train）, LCL for Hamburg：N-3 18:00（Beijing time）12:00 " +
                "(Local time),LCL forWarsaw/Mala：N-4 18:00（Beijing time）12:00 (Local time)," +
                "FCL for Hamburg：N-2 18:00（Beijing time）12:00 (Local time),FCL for Warsaw/Mala：N-3 18:00（Beijing time）12:00 (Local time)";
        table.addCell(createCell_colspan(v1, PdfConstants.FONT_M, Element.ALIGN_LEFT,3, false));
        /*24*/
        String v2 = "Providing documents（Packing list, Invioce, Customs declaration form). Packing  list（including：Consignor and consignee, " +
                "Description，Customscode，quantity，Packing，Gross weight and net weight）,receive_order_name,Invioce（including：Consignor and " +
                "consignee, Description，Customscode，quantity，Unit price, Amount, Gross weight and net weight）, EX(including：(Declaration number," +
                "Total gross weight,Packing,Customs code besides Saturday and sunday.)";
        table.addCell(createCell_colspan(v2, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*25*/
        String w = "Note : According to China customs regulations，please ensure the seal be locked at the 3rd Lock Handle with round rivet " +
                "on the locking device of the container rear end ( from left to right). Please to Mark 3 on the pictures below." +
                "\n备注：中国海关规定，封锁应锁到集装箱后方箱门左起第三根杆把手加锁处（有圆铆钉把手）。请参照下图标注的3号位置";
        table.addCell(createCell_colspan(w, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*26*/
        String x1 = "Warning：" +
                "\nA.Weight limit of the cargo in a 20' container is 21 tons. Weight limit of the cargo in a 40' container is 23 tons." +
                "\nA.20呎柜货重限重21吨，40呎柜货重限重23吨。" +
                "\nB.If the single piece of cargo weighs more than 100KG, packaging details and loading photo must be provided. " +
                "The container stuffing plan can beaccepted only after the confirmation from ZIH." +
                "\nB.单件货重超过100KG,装箱前需提供包装明细和装箱照片。经陆港公司审核并符合铁路要求的，依照此方案进行装箱。";
        table.addCell(createCell_colspan(x1, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*26*/
        String x2 = "C.Security requirements of railway transportation：Cargo's center of gravity should be placed in the center of the " +
                "container. Unbalance loading andover loading is unacceptable,for example, one side of the container is heavier than the other " +
                "side, or one end of container is heavier than the other end. If the container is unbalanced or over loaded, we will need to do " +
                "the container stuffing again. The related unstuffing and stuffing fees(Resulting in an additional operating cost of 150 dollars) " +
                "incurred shall be at the booking party's cost. If there is a shift packing of goods,dumping, spilling and other safety hazards, " +
                "the goods reinforcement must be handled in accordance with the requirements of ZIH and ZIH can provideon-site technical packing service." +
                "\nC.铁路运输安全要求：货箱重心必须居中，绝对不能偏载、偏重、超重。不可出现货箱一头重一头轻或者一侧重一侧轻的偏载现象。如发生以上偏载、" +
                "偏重、超重现象，必须将货物掏出来重新装箱，所产生的掏、装箱等相关费用（导致额外的操作成本为150美元），由订舱方自行承担。若装箱货物存在移位、" +
                "倾倒、溢出等安全隐患的，必须按照陆港公司的要求对货物进行加固处理，我司提供现场装箱技术服务。";
        table.addCell(createCell_colspan(x2, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*27*/
        String y1 = "40尺开顶箱：2个侧壁，每侧侧壁4个锁杆需要4个锁具，每侧箱顶需要3个锁具，箱门需要1个锁具 。一共15个" +
                "\n20尺开顶箱：2个侧壁，每侧侧壁2-3个锁杆需要2-3个锁具，每侧箱顶需要2个锁具，箱门需要1个锁具 。一共是9个或者11个";
        table.addCell(createCell_colspan(y1, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*28*/
        String y2 = "40OT: Two sides, four locks on each side, 3 locks on the container top of each side and 1 lock on the door, totally 15 locks." +
                "\n20OT: Two sides, 2 or 3 locks on each side, 2 locks on the container top of each side and 1 lock on the door, totally 9 or 11 locks.";
        table.addCell(createCell_colspan(y2, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));

        /*29*/
        /*1:图片*/
        Image image_a = Image.getInstance(PdfConstants.IMAGE_3);
        image_a.setAlignment(Image.ALIGN_CENTER);
        image_a.scalePercent(22); //依照比例缩放
        table.addCell(createCell(image_a, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*2:图片*/
        Image image_b = Image.getInstance(PdfConstants.IMAGE_4);
        image_b.setAlignment(Image.ALIGN_CENTER);
        image_b.scalePercent(22); //依照比例缩放
        table.addCell(createCell(image_b, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*3:图片*/
        Image image_c = Image.getInstance(PdfConstants.IMAGE_5);
        image_c.setAlignment(Image.ALIGN_CENTER);
        image_c.scalePercent(22); //依照比例缩放
        table.addCell(createCell(image_c, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*30*/
        /*1:图片  同第一个公章图片一致*/
        table.addCell(createCell(officialSeal, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, false));
        /*2:图片*/
        Image image_e = Image.getInstance(PdfConstants.IMAGE_6);
        image_e.setAlignment(Image.ALIGN_CENTER);
        image_e.scalePercent(90); //依照比例缩放
        table.addCell(createCell_colspan(image_e, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,2, false));
        /*31*/
        Image image_f = Image.getInstance(PdfConstants.IMAGE_7);
        image_f.setAlignment(Image.ALIGN_CENTER);
        image_f.scalePercent(88); //依照比例缩放
        table.addCell(createCell_colspan(image_f, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,3, false));

        /*32*/
        String z = "Note :Please deliver the bulk cargo to the designated terminal with this document and hand it over to personnel in terminal. " +
                "\n备注：请在送货到指定场站时携带本文件，并交给场站工作人员。\n" +
                "Note : If you would cancel this booking, please notify us 7 days before train departure. Or else, ZIH will charge you for dead freight. " +
                "\n备注：如果取消订舱请于班列开行前7天通知，否则陆港公司会收取亏舱费。 ";
        table.addCell(createCell_colspan(z, PdfConstants.FONT_S, Element.ALIGN_LEFT,3, false));
        /*33*/
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
