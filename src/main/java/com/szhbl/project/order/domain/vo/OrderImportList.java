package com.szhbl.project.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OrderImportList {

    /** 推荐人 */
    @Excel(name = "ZIH推荐人")
    private String clientTjr;

    /** 订舱方 */
    @Excel(name = "订舱方")
    private String clientUnit;

    /** 箱属 */
    @Excel(name = "箱属", readConverterExp = "0=委托ZIH,1=自备")
    private String orderAuditBelongto;

    /** 订舱方地址 */
    @Excel(name = "订舱方地址")
    private String clientAddress;

    /** 订舱方联系人*/
    @Excel(name = "订舱方联系人")
    private String clientContacts;

    /** 订舱方联系方式 */
    @Excel(name = "订舱方联系方式")
    private String clientTel;

    /** 发货方 */
    @Excel(name = "发货方")
    private String shipOrederName;

    /** 发货方地址 */
    @Excel(name = "发货方地址")
    private String shipOrederAddress;

    /** 发货方联系人 */
    @Excel(name = "发货方联系人")
    private String shipOrederContacts;

    /** 发货方联系方式 */
    @Excel(name = "发货方联系方式")
    private String shipOrederPhone;

    /** zih报关 0=是,1=否*/
    @Excel(name = "报关方式", readConverterExp = "0=委托zih代办,1=否")
    private String clientOrderBindingway;

    /** 报关费用 */
    @Excel(name = "报关费用")
    private String clientBgCost;

    /** 委托ZIH提货 0=是，1=否*/
    @Excel(name = "到货方式", readConverterExp = "0=委托ZIH提货,1=自送货,2=铁路送货")
    private String shipOrderBinningway;

    /** 提货方式 0=整箱到车站，1=散货到堆场*/
    @Excel(name = "装箱方式", readConverterExp = "0=整箱到车站,1=散货到堆场")
    private String shipThTypeId;

    /** 提货时间 */
    @Excel(name = "提货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shipOrderUnloadtime;

    /** 提货地址 */
    @Excel(name = "提货地址")
    private String shipOrderUnloadaddress;

    /** 去程提货地，回程送货地详细地址*/
    @Excel(name = "提货详细地址")
    private String detailedAddress;

    /** 发货方自送货方式 0=散货到堆场，1=整箱到车站*/
    private String shipZsTypeId;

    /** 自送货时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shipOrderSendtime;


    /** 提货联系人*/
    @Excel(name = "提货联系人")
    private String shipOrderUnloadcontacts;

    /** 提货联系方式 */
    @Excel(name = "提货联系方式")
    private String shipOrderUnloadway;

    /** 提货费用 */
    @Excel(name = "提货费用")
    private String shipThCost;

    /** 提货费用币种 */
    private String zxThcostCurrency;

    /** 提货费报价编号 */
    @Excel(name = "提货费用编号")
    private String shipThCostNo;

    /** 车站名称 */
    @Excel(name = "车站名称")
    private String station;

    /** 收货方（送货方） */
    @Excel(name = "收货方")
    private String receiveOrderName;

    /** 收货方通讯地址 */
    @Excel(name = "收货方地址")
    private String receiveOrderAddress;

    /** 收货方联系人 */
    @Excel(name = "收货方联系人")
    private String receiveOrderContacts;

    /** 收货方联系电话 */
    @Excel(name = "收货方联系方式")
    private String receiveOrderPhone;

    /** 是否由ZIH代理清关 */
    @Excel(name = "是否由ZIH代理清关", readConverterExp = "0=否,1=是")
    private String receiveOrderIsclearance;

    /** 清关费用 */
    @Excel(name = "清关费用")
    private String receiveQgCost;

    /** 清关费币种 */
    private String qgCostcurrency;

    /** 是否由ZIH代理送货 */
    @Excel(name = "到站后由ZIH代理分拨", readConverterExp = "0=否,1=是")
    private String receiveOrderIspart;

    /** 分拨方式（0整柜派送，1拆箱散货派送) */
    //@Excel(name = "分拨方式", readConverterExp = "0=整柜派送,1=散货派送")
    @Excel(name = "分拨方式")
    private String distributionType;

    /** 送货分拨地址 */
    @Excel(name = "送货分拨地址")
    private String receiveOrderPartaddress;

    /** 去程提货地，回程送货地详细地址*/
    @Excel(name = "分拨详细地址")
    private String detailedAddressFb;

    /** 送货分拨联系人 */
    @Excel(name = "送货分拨联系人")
    private String receiveOrderZihcontacts;

    /** 送货分拨联系电话 */
    @Excel(name = "送货分拨联系电话")
    private String receiveOrderZihtel;

    /** 送货费用 */
    @Excel(name = "分拨费用")
    private String receiveShCost;

    /** 送货费币种 */
    private String shCostcurrency;

    /** 送货报价编号 */
    @Excel(name = "送货费用编号")
    private String receiveShCostId;

    /** 在途信息接收邮箱 */
    @Excel(name = "在途信息接收邮箱")
    private String receiveOrderReceiveemail;

    /** 到站通知提货邮箱 */
    @Excel(name = "到站通知提货邮箱")
    private String receiveOrderMail;

    /** 条款名称 */
    @Excel(name = "条款名称")
    private String dictName;

    /** 上货站 */
    @Excel(name = "上货站")
    private String orderUploadsite;

    /** 上货站编号 */
    private String orderUploadcode;

    /** 下货站 */
    @Excel(name = "下货站")
    private String orderUnloadsite;

    /** 下货站编号 */
    private String orderUnloadcode;

    /** 箱量*/
    @Excel(name = "箱量")
    private String containerBoxamount;

    /** 箱型 */
    @Excel(name = "箱型")
    private String containerType;

    /**
     * 站到站运费
     */
    @Excel(name = "站到站运费")
    private String sitecost;

    /**
     * 铁路运费币种
     */
    private String sitecostCurrency;

    /**
     * 提箱地点shipFhSite
     */
    @Excel(name = "提箱地点")
    private String shipFhSite;  //去程发货提箱地
    private String shipHyd; //回程发货提箱地

    /**
     * 收货还箱地
     */
    @Excel(name = "还箱地点")
    private String receiveHxAddress;

    /**
     * 提箱费
     */
    @Excel(name = "箱管费用")
    private String pickUpBoxFee;

    /**
     * 还箱费
     */
    private String returnBoxFee;

    /**
     * 货物品名
     */
    @Excel(name = "货品中文品名")
    private String goodsName;

    /**
     * 货品英文名称
     */
    @Excel(name = "货品英文品名")
    private String goodsEnName;

    /**
     * 清关HS
     */
    @Excel(name = "下货站清关HS")
    private String goodsClearance;

    /**
     * 报关HS
     */
    @Excel(name = "上货站报关HS")
    private String goodsReport;

    /**
     * 最外层包装形式
     */
    @Excel(name = "最外层包装形式")
    private String goodsPacking;

    /**
     * 最外层包装数量
     */
    @Excel(name = "最外层包装数量")
    private String goodsNumber;

    @Excel(name = "规格")
    private String goodsStandard;
    private List<String> goodsStandards;

    @Excel(name = "单件重量")
    private String oneWeight;

    private List<String> oneWeights;

    /**
     * 体积
     */
    @Excel(name = "总体积")
    private String goodsCbm;

    /**
     * 重量
     */
    @Excel(name = "总毛重")
    private String goodsKgs;

    /**
     * 是否需要装箱方案 0=是，1=否
     */
    @Excel(name = "是否需要装箱方案", readConverterExp = "0=是,1=否")
    private String goodsIsscheme;

    /**
     * 是否派监装员 0=否，1=是
     */
    @Excel(name = "是否需要派监装员", readConverterExp = "0=否,1=是")
    private String shipOrderIsdispatch;

    /**
     * 监装费用
     */
    @Excel(name = "监装费用")
    private String shipJzCost;

    /**
     * 委托书编号
     */
    @Excel(name = "委托书编号")
    private String orderNumber;

    /**
     * 业务员编码
     */
    @Excel(name = "业务员编码")
    private String ywNumber;

    /**
     * 业务编码
     */
    @Excel(name = "业务编码")
    private String clientYwNumber;

    /**
     * 跟单员姓名
     */
    @Excel(name = "跟单员")
    private String orderMerchandiser;

    /**
     * 跟单员工号
     */
    private String orderMerchandiserNumber;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderAuditCreatedate;

    /**
     * 客户提交时间
     */
    @Excel(name = "填写托书时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tjTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /** 装箱方式 */
    @Excel(name = "整拼箱", readConverterExp = "0=整柜,1=拼箱")
    private String isconsolidation;

    /** 是否可堆叠 */
    @Excel(name = "是否可堆叠", readConverterExp = "0=否,1=是,2仅可自叠")
    private String goodsCannotpileup;

    /** 是否易碎 */
    @Excel(name = "是否易碎", readConverterExp = "0=否,1=是")
    private String goodsFragile;

    /** 单件超长超重 */
    @Excel(name = "单件超长超重", readConverterExp = "0=否,1=是")
    private String goodsGeneral;

    /** 托书状态 */
    @Excel(name = "托书状态")
    private String isexamline;

    /** 中文发货人(俄线) */
    @Excel(name = "中文发货人")
    private String consignorc;

    /** 发货人声明(俄线) */
    @Excel(name = "发货人声明")
    private String econsignorstate;

    /** 实际收货人名称(俄线) */
    @Excel(name = "实际收货人")
    private String etxCompany;

    /** 承担监管区费用的公司或个人(俄线) */
    @Excel(name = "承担监管区费用的公司或个人")
    private String etxName;

    /** 到货提箱公司税号(俄线) */
    @Excel(name = "到货提箱公司税号")
    private String eduty;

    /** 口岸 */
    @Excel(name = "口岸")
    private String lineName;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 开始日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateStart;

    /** 结束日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateEnd;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String orderClassBh;

    /** 客户转待审核次数和 */
    private Long totalturncount;

    /** 客户转待审核次数和 */
    @Excel(name = "去程N-4 12:00之后，回程N-5 12:00之后转待审核次数")
    private Long totalturncountavg;

    /**国内拼箱场站报价单号*/
    @Excel(name = "国内拼箱场站报价单号")
    private String domesticNumber;

    /**国外拼箱场站报价单号*/
    @Excel(name = "国外拼箱场站报价单号")
    private String foreignNumber;

    /** 订单id */
    private String orderId;

    /** 托书操作时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    /** 托书操作人 */
    private String createusername;

    /** 服务类型 */
    //@Excel(name = "服务类型", readConverterExp = "0=门到门,1=门到站,2=站到站,3=站到门")
    private String bookingService;

    /** 时效*/
    //@Excel(name = "时效", readConverterExp = "0=普通,1=加急")
    private String limitation;

    /** 国内公路运输车辆类型 */
    //@Excel(name = "国内公路运输车辆类型", readConverterExp = "0=普通车,1=普通卡车,2=白卡专车")
    private String truckType;

//    @Excel(name = "托书审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date exameTime;

    /** 往返*/
    //@Excel(name = "往返", readConverterExp = "0=西向,1=东向")
    private String classEastandwest;

    /** 线路 */
    //@Excel(name = "线路", readConverterExp = "0=中欧,2=中亚,3=中越,4=中俄")
    private String lineTypeid;

    /** 是否可提前班列 */
    //@Excel(name = "是否可提前班列", readConverterExp = "0=是,1=否")
    private Long putoffClass;

    /** 班列号 */
    private String classNumber;

    /** 是否需要发票 0=否，1=是*/
    //@Excel(name = "是否需要发票", readConverterExp = "0=否,1=是")
    private String orderIsticket;

    /** 订舱方ID*/
    private String clientId;

    //订舱方子账号id
    private Long khId;

    /** 订舱方邮箱 */
    private String clientEmail;


    /** 报关地点 */
    private String clientOrderBindingaddress;

    /** 发货方邮箱 */
    private String shipOrederEmail;

    /** 提货联系邮箱 */
    private String shipOrderUnloadwayEmail;


    /** 送货分拨邮箱 */
    private String receiveOrderZihemail;

//    @Excel(name = "到货城市，就近还空箱")
//    private String receiveHyd;

    /** 每日转待审核次数 */
    private Long turncount;

    /** 重箱托书编号 */
    private String zxNumber;

    /** 是否有改单 0=否，1=是*/
    //@Excel(name = "是否有改单", readConverterExp = "0=否,1=是")
    private String dcGaidanState;

    /** 是否有修改记录 0=否，1=是*/
    //@Excel(name = "是否有修改记录", readConverterExp = "0=否,1=是")
    private String isupdate;

    /** 是否上车,运综货物状态 */
    //@Excel(name = "是否上车,运综货物状态")
    private String isgetin;

    /** 唛头 */
    private String goodsMark;

    /** 货物备注 */
    private String goodsbz;

    /** 货物hs编码备注 */
    private String hsbz;

    /** 部门名称 */
    private String deptCode;

    /** 部门名编号*/
    private String organizationCode;

    public String userid;

    /** 托书列表权限类别*/
    private String readType;

    /** 操作代理 */

    /** 订单类型（0是中文订单1是英文） */
    private String yuyan;

    /** 查询类型 0列表 1导出 */
    private String type;

    /** 拼箱总体积 */
    private String totalVolumn;

    /** 拼箱总重量 */
    private String totalWeight;


}
