package com.szhbl.common.utils;

import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientEmailUtil {

    public static String enEmailContent(String account,String clientValidityDate,String clientDisableDate,String password){
        StringBuilder sb = new StringBuilder();
        sb.append("Dear customer:<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Hello!thank you for choosing ZIH transportation product of Zhengzhou-Europe Block Trian. With your support and help, ZIH’s freight <br/>");
        sb.append("transportation, fully loaded rate, gowth of main business income are at the top of the China Railway Express. <br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;In the year of 2015, the new block train of Zhengzhou-russia, Zhengzhou-Kazakhstan, Zhengzhou-Korea, Japan and the new business<br/>");
        sb.append(" of cross-border e-commerce, ZIH trade have formed good reputation at home. Zhengzhou-Europe Block Trian has been the important brand in the<br/>");
        sb.append("“Silk Road economic belt”</br>");
        sb.append("<p>");
        sb.append("You have successfully registered as my company's quality customers, Wish our online booking platform brings efficient and convenient service to you!<br/>");
        sb.append("your account:"+account + "&nbsp;&nbsp;<br/>" + "password:"+password+ "valid time: " + clientValidityDate + "&nbsp;&nbsp; Invalid time:" + clientDisableDate + "<br/>");
        return sb.toString();

    }
    public static String chEmailContent(String account,String clientValidityDate,String clientDisableDate,String password){
        StringBuilder sb = new StringBuilder();
        sb.append("尊敬的客户：<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;您好！感谢您选择郑州国际陆港开发建设有限公司“郑欧班列”运输产品！ 在您的支持和帮助下，郑欧班列货<br/>");
        sb.append("运量、满载率、主营业务收入增速均位列中欧班列第一。<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;2015年新增郑俄、郑哈、郑日韩班列和跨境电商业务及陆港贸易，在国内外客户群中形成了良好的口碑。<br/>");
        sb.append("郑欧班列已成为新“丝绸之路经济带”上的重要品牌。<br/>");
        sb.append("<p>");
        sb.append("您已成功注册成为我公司的优质客户，愿我们的在线订舱平台给您带来高效便捷的服务！<br/>");
        sb.append("您的账号："+account + "&nbsp;&nbsp;"  +"<br/>"+"密码："+ password + "有效时间:" + clientValidityDate + "&nbsp;&nbsp;无效时间:" + clientDisableDate + "<br/>");
        return sb.toString();
    }
    public static String chOrderEmailContent(String content, ShippingOrder shippingOrder,String orderNum){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String classDate = "";
        if(StringUtils.isNotNull(shippingOrder.getClassDate())){
            classDate = dateFormat.format(shippingOrder.getClassDate());
        }
        sb.append("班列日期:" + classDate + "\n");
        sb.append("托书编号:" + shippingOrder.getOrderNumber() + "\n");
        sb.append("业务编号:" + shippingOrder.getClientYwNumber() + "\n");
        sb.append("订舱单位:" + shippingOrder.getClientUnit()+ ",  " + "订舱人:" + shippingOrder.getClientContacts() + "\n");
        sb.append("上货站:" + shippingOrder.getOrderUploadsite() + ",  " + "下货站:" + shippingOrder.getOrderUnloadsite()+ "\n");
        sb.append("货品名称:" + shippingOrder.getGoodsName() + "\n");
        //整柜
        if("0".equals(shippingOrder.getIsconsolidation())){
            sb.append("总体积(CBM):" +shippingOrder.getGoodsCbm() + ",  " + "总重量(KGS):" + shippingOrder.getGoodsKgs() + "\n");
            sb.append("最外层包装形式:" + shippingOrder.getGoodsPacking() + ",  " + "最外层包装数量:" + shippingOrder.getGoodsNumber() + "\n");
            sb.append("发货方:" + shippingOrder.getShipOrederName() + "\n");
            sb.append("收货方:" + shippingOrder.getReceiveOrderName() + "\n");
            String containerType = shippingOrder.getContainerType();
            if(StringUtils.isNotEmpty(containerType)){
                switch (containerType)
                {
                    case "20GP":containerType = "20尺普箱";break;
                    case "20HC":containerType = "20尺高箱";break;
                    case "40GP":containerType = "40尺普箱";break;
                    case "40HC":containerType = "40尺高箱";break;
                    case "45HC":containerType = "45尺高箱";break;
                    case "20HOT":containerType = "20尺超高开顶箱";break;
                    case "20HT":containerType = "20尺挂衣箱";break;
                    case "20OT":containerType = "20尺普高开顶箱";break;
                    case "40HOT":containerType = "40尺超高开顶箱";break;
                    case "40HT":containerType = "40尺挂衣箱";break;
                    case "40MT":containerType = "40尺分层箱";break;
                    case "40OT":containerType = "40尺普高开顶箱";break;
                    case "40RF":containerType = "40尺冷藏";break;
                    case "45RF":containerType = "45尺冷藏箱";break;
                }
            }
            sb.append("箱量:" + shippingOrder.getContainerBoxamount() + ",  " + "箱型:" + containerType + "\n");
        }
        //拼箱
        else if("1".equals(shippingOrder.getIsconsolidation())){
            sb.append("发货方:" + shippingOrder.getShipOrederName()  + "\n");
            sb.append("收货方:" + shippingOrder.getReceiveOrderName()+ "\n");
            sb.append("最外层包装形式:" + shippingOrder.getGoodsPacking() +"\n");
            sb.append("最外层包装数量:" + shippingOrder.getGoodsNumber() + "\n");
            sb.append("体积(CBM):" + shippingOrder.getGoodsCbm() + ",  " + "重量(KGS):" + shippingOrder.getGoodsKgs() + "\n");
        }
        if ("0".equals(shippingOrder.getShipOrderBinningway() )&& "1".equals(shippingOrder.getIsexamline())) {
            sb.append("提货地址:" + shippingOrder.getShipOrderUnloadaddress()+ "\n");
            sb.append("(提货)联系人:" + shippingOrder.getShipOrderUnloadcontacts() + "\n");
            sb.append("(提货)联系电话:" + shippingOrder.getShipOrderUnloadway() + "\n");
            //回有，去没有
            if("1".equals(shippingOrder.getClassEastandwest())){
                sb.append("(提货)邮箱:" + shippingOrder.getShipOrderUnloadwayEmail() + "\n");
            }
            String thtime = "";
            if(StringUtils.isNotNull(shippingOrder.getShipOrderUnloadtime())){
                thtime = dateFormat.format(shippingOrder.getShipOrderUnloadtime());
            }
            sb.append("提货时间:" +thtime + "\n");
        }
        if ("2".equals(shippingOrder.getLineTypeid())&&!shippingOrder.getOrderNumber().equals(orderNum))
        {
            sb.append("备注:原舱位号为" +orderNum + "\n");
        }
        if(StringUtils.isNotEmpty(shippingOrder.getIsexamline())){
            String isexamline = shippingOrder.getIsexamline();
            isexamline = "0".equals(isexamline)?"待审核":"1".equals(isexamline)?"审核通过":"2".equals(isexamline)?"审核失败":"3".equals(isexamline)?"取消委托":"4".equals(isexamline)?"转待审核中":"5".equals(isexamline)?"草稿":"6".equals(isexamline)?"转待审核失败":"7".equals(isexamline)?"箱管部审核中": "8".equals(isexamline)?"箱管部审核失败":"9".equals(isexamline)? "报价中":"10".equals(isexamline)?"客户确认中":"11".equals(isexamline)?"公路审核中":"12".equals(isexamline)?"集疏审核中":"13".equals(isexamline)?"撤舱审核中":"14".equals(isexamline)?"订舱报价中":"订舱确认中";
            sb.append("状态："+isexamline);
        }
        return sb.toString();
    }

    public static String enOrderEmailContent(String content, ShippingOrder shippingOrder,String orderNum){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String classDate = "";
        if(StringUtils.isNotNull(shippingOrder.getClassDate())){
            classDate = dateFormat.format(shippingOrder.getClassDate());
        }
        sb.append("Train date:" + classDate + "\n");
        sb.append("Space number:" + shippingOrder.getOrderNumber() + "\n");
        sb.append("POA No:" + shippingOrder.getClientYwNumber() + "\n");
        sb.append("Booking party:" + shippingOrder.getClientUnit()+ ",  " + "contact person:" + shippingOrder.getClientContacts() + "\n");
        sb.append("Loading station:" + shippingOrder.getOrderUploadsite() + ",  " + "Unloading station:" + shippingOrder.getOrderUnloadsite()+ "\n");
        sb.append("Cargo Name:" + shippingOrder.getGoodsName() + "\n");
        //整柜
        if("0".equals(shippingOrder.getIsconsolidation())){
            sb.append("Total Volume(CBM):" +shippingOrder.getGoodsCbm() + ",  " + "Total Weight(KGS):" + shippingOrder.getGoodsKgs() + "\n");
            sb.append("Outer Packing Form:" + shippingOrder.getGoodsPacking() + ",  " + "Quantity of outer-packing:" + shippingOrder.getGoodsNumber() + "\n");
            sb.append("Consignor:" + shippingOrder.getShipOrederName() + "\n");
            sb.append("Receiving party:" + shippingOrder.getReceiveOrderName() + "\n");
            sb.append("Container Quantity:" + shippingOrder.getContainerBoxamount() + ",  " + "Container Size(type):" + shippingOrder.getContainerType() + "\n");
        }
        //拼箱
        else if("1".equals(shippingOrder.getIsconsolidation())){
            sb.append("Consignor:" + shippingOrder.getShipOrederName()  + "\n");
            sb.append("Name of consignee:" + shippingOrder.getReceiveOrderName()+ "\n");
            sb.append("Outer Packing Form:" + shippingOrder.getGoodsPacking() +"\n");
            sb.append("Quantity of outer-packing:" + shippingOrder.getGoodsNumber() + "\n");
            sb.append("Total Volume(CBM):" + shippingOrder.getGoodsCbm() + ",  " + "Total Weight(KGS):" + shippingOrder.getGoodsKgs() + "\n");
        }
        if ("0".equals(shippingOrder.getShipOrderBinningway() )&& "1".equals(shippingOrder.getIsexamline())) {
            sb.append("pick up address:" + shippingOrder.getShipOrderUnloadaddress()+ "\n");
            sb.append("Contacts:" + shippingOrder.getShipOrderUnloadcontacts() + "\n");
            sb.append("Phone:" + shippingOrder.getShipOrderUnloadway() + "\n");
            //回有，去没有
            if("1".equals(shippingOrder.getClassEastandwest())){
                sb.append("Contact Email:" + shippingOrder.getShipOrderUnloadwayEmail() + "\n");
            }
            String thtime = "";
            if(StringUtils.isNotNull(shippingOrder.getShipOrderUnloadtime())){
                thtime = dateFormat.format(shippingOrder.getShipOrderUnloadtime());
            }
            sb.append("Pick up time:" +thtime + "\n");
        }
       /* if ("2".equals(shippingOrder.getLineTypeid())&&!shippingOrder.getOrderNumber().equals(orderNum))
        {
            sb.append("备注:原舱位号为" +orderNum + "\n");
        }*/
        if(StringUtils.isNotEmpty(shippingOrder.getIsexamline())){
            String isexamline = shippingOrder.getIsexamline();
            isexamline = "0".equals(isexamline)?"WaitReview":"1".equals(isexamline)?"approved":"2".equals(isexamline)?"failureApproved":"3".equals(isexamline)?"cancelled order":"4".equals(isexamline)?"turningToReview":"5".equals(isexamline)?"draft":"6".equals(isexamline)?"turn to andit failed":"7".equals(isexamline)?"WaitReview": "8".equals(isexamline)?"failureApproved":"9".equals(isexamline)? "Quoting":"10".equals(isexamline)?"Under customer`s confirmation":"11".equals(isexamline)?"WaitReview":"12".equals(isexamline)?"WaitReview":"13".equals(isexamline)?"Cancellation pending review":"14".equals(isexamline)?"Quoting":"Under customer`s confirmation";
            sb.append("Status："+isexamline);
        }
        return sb.toString();
    }
}
