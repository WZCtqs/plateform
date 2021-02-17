package com.szhbl.common.utils.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

import static com.szhbl.framework.config.SzhblConfig.getProfile;

/**
 * @author HP
 */
public class PdfConstants {

    /**
     * 客户端上传报关文件
     */
    public static String getClientApplyPath() {
        return getProfile() + "/document/clientApply";
    }

    /**
     * 客户端上传报关文件正本
     */
    public static String getClientApplyOriginalPath() {
        return getProfile() + "/document/clientApplyOriginal";
    }

    /**
     * 客户端上传随车文件
     */
    public static String getFollowVehiclePath() {
        return getProfile() + "/document/followVehicle";
    }

    /**
     * 客户端上传装箱明细（客户自装箱）
     */
    public static String getBoxingListcPath() {
        return getProfile() + "/document/boxingListc";
    }

    /**
     * 客户端上传 电放保函
     */
    public static String getLetterGuaranteePath() {
        return getProfile() + "/document/letterGuarantee";
    }

    /**
     * 客户端上传 欧盟报关单
     */
    public static String getDeclareCustomsEUPath() {
        return getProfile() + "/document/declareCustomsEU";
    }

    /**
     * 拼箱场站系统  装箱方案地址
     */
    public static String getBoxingSchemePath() {
        return getProfile() + "/document/boxingScheme";
    }

    /**
     * 拼箱场站系统  拼箱到货信息地址
     */
    public static String getArrivalGoodsPath() {
        return getProfile() + "/document/arrivalGoods";
    }

    /**
     * 拼箱场站系统  装柜清单（件重尺）地址
     */
    public static String getBoxingListSizePath() {
        return getProfile() + "/document/boxingListSize";
    }

    /**
     * 拼箱场站系统  装柜清单地址
     */
    public static String getBoxingListPath() {
        return getProfile() + "/document/boxingList";
    }

    /**
     * 拼箱场站系统  堆场重箱进站表地址
     */
    public static String getYardLoadedInPath() {
        return getProfile() + "/document/yardLoadedIn";
    }

    /**
     * 国外场站系统  装箱要求
     */
    public static String getBoxingRequirePath() {
        return getProfile() + "/boxingRequire";
    }

    /**
     * 国外场站系统  拆箱代理表
     */
    public static String getDevanningAgentPath() {
        return getProfile() + "/devanningAgent";
    }

    /**
     * 国外场站系统  到货信息表
     */
    public static String getArrivalgoodslistPath() {
        return getProfile() + "/arrivalgoodslist";
    }

    /**
     * 国外场站系统  回程进站时间统计表
     */
    public static String getArrivalStationTimeTotalPath() {
        return getProfile() + "/arrivalStationTimeTotal";
    }

    /**
     * 国外场站系统  提箱号表
     */
    public static String getPickConNoPath() {
        return getProfile() + "/pickConNo";
    }

    /**
     * 国外场站系统  提箱指令
     */
    public static String getPickConCommandPath() {
        return getProfile() + "/pickConCommand";
    }

    /**
     * 国外场站系统  回程进站时间统计表
     */
    public static String getPickConDataPath() {
        return getProfile() + "/pickConData";
    }

    /**
     * 箱型亚欧系统  装箱要求
     */
    public static String getArrivalStationPhotoPath() {
        return getProfile() + "/arrivalStationPhoto";
    }

    /**
     * 箱型亚欧系统  签收单
     */
    public static String getReceiptGoodsPath() {
        return getProfile() + "/receiptGoods";
    }

    /**
     * 大口岸系统文件 SMGS
     */
    public static String getSMGSPath() {
        return getProfile() + "/SMGS";
    }

    /**
     * 大口岸系统文件 CIM
     */
    public static String getCIMPath() {
        return getProfile() + "/CIM";
    }

    /**
     * 大口岸系统文件 ATB
     */
    public static final String DKA_ATB_PATH = "E:\\ZZLG\\ATB\\";
    /**
     * 大口岸系统文件 正式随车
     */
    public static final String DKA_FOLLOW_VEHICLE_FORMAL_PATH = "E:\\ZZLG\\followVehicleFormal\\";

    /**
     * 大口岸系统文件 正式随车
     */
    public static final String DKA_LADING_BILL_FORMAL_PATH = "E:\\ZZLG\\ladingBillFormal\\";

    /**
     * 大口岸系统文件 提箱情况表
     */
    public static final String DKA_PICK_CON_MSG_PATH = "E:\\ZZLG\\pickConMsg\\";

    /**
     * 关务系统  报关单文件
     */
    public static final String GW_DECLARE_CUSTOMS_PATH = "E:\\ZZLG\\declareCustoms\\";
    /**
     * 关务系统  放行单文件
     */
    public static final String GW_CLEARANCE_PAPER_PATH = "E:\\ZZLG\\clearancePaper\\";
    /**
     * 关务系统  口岸转关信息表
     */
    public static final String GW_PORT_TRANS_PATH = "E:\\ZZLG\\portTrans\\";
    /**
     * 关务系统  口岸转关信息表
     */
    public static final String GW_PX_GOODS_INOUT_PATH = "E:\\ZZLG\\pxGoodsInOut\\";

    /**
     * pdf 地址
     */
    public static String getNoticePDFPath() {
        return getProfile() + "/document/noticePDF";
    }

    /**
     * 二维码 地址
     */
    public static String getQRcodePath() {
        return getProfile() + "/document/QRcode";
    }

    /**
     * pdf生成所需参数
     */
    public static final Font FONT = new XMLWorkerFontProvider().getFont("幼圆", 10);
    public static final Font FONT_M = new XMLWorkerFontProvider().getFont("幼圆", 8);
    public static final Font FONT_S = new XMLWorkerFontProvider().getFont("幼圆", 6);
    public static final int MAX_WIDTH = 480;
    public static final float FIXED_LEADING = 3;
    public static final float MULTIPLIED_LEADING = 1;
    /**
     * pdf模板所需图片
     */
    public static final String IMAGE_1 = "C:\\blpt\\into_1.png";
    public static final String IMAGE_3 = "C:\\blpt\\into_3.jpg";
    public static final String IMAGE_4 = "C:\\blpt\\into_4.jpg";
    public static final String IMAGE_5 = "C:\\blpt\\into_5.png";
    public static final String IMAGE_6 = "C:\\blpt\\into_6.jpg";
    public static final String IMAGE_7 = "C:\\blpt\\into_7.jpg";
    public static final String HEADER_LOGO = "C:\\blpt\\into_logo.jpg";
    public static final String SIGN = "C:\\blpt\\into_sign1.png";
    public static final String MNH = "C:\\blpt\\MNH.png";

    // 二维码logo图片
    public static final String QRCODE_LOGO = "C:\\blpt\\qrcode_logo.png";

}
