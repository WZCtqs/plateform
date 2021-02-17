package com.szhbl.project.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 订单对象 busi_shippingorder
 * 
 * @author dps
 * @date 2020-01-21
 */
@Data
public class BusiShippingorders
{
    private static final long serialVersionUID = 1L;

    /** 订单id,主键 */
    @ApiModelProperty(value = "托书id")
    private String orderId;

    /** 站点ID,回程入货通知书上堆场地址 */
    @Excel(name = "站点ID,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "站点ID,回程入货通知书上堆场地址")
    private String stationid;
    private String stationidNew;


    /** 站点ID,回程入货通知书上堆场地址 */
    @Excel(name = "站点ID,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "站点,回程入货通知书上堆场地址")
    private String statioin;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "订单号 托书编号")
    private String orderNumber;
    private String orderNumberNew;


    /** 修改订单号（委托书编号）（舱位号） */
    @Excel(name = "修改后订单号", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "修改后订单号")
    private String orderNumberEdit;

    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "服务：0门到门 1门到站 2站到站 3站到门", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "服务：0门到门 1门到站 2站到站 3站到门")
    private String bookingService;

    /** 集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱 */
    @Excel(name = "集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱")
    @ApiModelProperty(value = "集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱")
    private String orderAuditBelongto;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orderAuditCreatedate;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    @ApiModelProperty(value = "创建用户ID")
    private String orderAuditUserid;

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    @ApiModelProperty(value = "创建用户姓名")
    private String orderAuditUsername;

    /** 班列ID */
    @Excel(name = "班列ID")
    @ApiModelProperty(value = "班列ID")
    private String classId;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列站点 */
    @Excel(name = "班列站点")
    @ApiModelProperty(value = "班列站点")
    private String classSite;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String orderClassBh;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "线路类型：0中欧 2中亚 3中越 4中俄")
    private String lineTypeid;

    /** 班列号 */
    @Excel(name = "班列号")
    @ApiModelProperty(value = "班列号")
    private String classNumber;

    /**班列编号*/
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    private String classBh;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程 1为回程")
    private String classEastandwest;

    /** 上货站 */
    @Excel(name = "上货站")
    @ApiModelProperty(value = "上货站")
    private String orderUploadsite;

    /** 下货站 */
    @Excel(name = "下货站")
    @ApiModelProperty(value = "下货站")
    private String orderUnloadsite;

    /** 上货站唯一码 */
    @Excel(name = "上货站唯一码")
    @ApiModelProperty(value = "上货站唯一码")
    private String orderUploadcode;

    /** 下货丫唯一码 */
    @Excel(name = "下货站唯一码")
    @ApiModelProperty(value = "下货站唯一码")
    private String orderUnloadcode;

    /** 贸易方式（条款ID） */
    @Excel(name = "贸易方式", readConverterExp = "条=款ID")
    @ApiModelProperty(value = "贸易方式")
    private Long dictId;

    /** 条款名称 */
    @Excel(name = "条款名称")
    @ApiModelProperty(value = "条款名称")
    private String dictName;

    /** 跟单姓名 */
    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orderDeptName;

    /** 跟单姓名 */
    @Excel(name = "跟单姓名")
    @ApiModelProperty(value = "跟单姓名")
    private String orderMerchandiser;

    /** 跟单ID */
    @Excel(name = "跟单ID")
    @ApiModelProperty(value = "跟单ID")
    private String orderMerchandiserId;

    /** 是否需要发票 */
    @Excel(name = "是否需要发票")
    @ApiModelProperty(value = "是否需要发票")
    private String orderIsticket;

    /** 订舱方ID */
    @Excel(name = "订舱方ID")
    @ApiModelProperty(value = "订舱方ID")
    private String clientId;

    /** 订舱方子账号id */
    @Excel(name = "订舱方子账号id")
    @ApiModelProperty(value = "订舱方子账号id")
    private Long khId;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    @ApiModelProperty(value = "订舱方名称")
    private String clientUnit;

    /** 订舱方联系人 */
    @Excel(name = "订舱方联系人")
    @ApiModelProperty(value = "订舱方联系人")
    private String clientContacts;

    /** 订舱方联系方式 */
    @Excel(name = "订舱方联系方式")
    @ApiModelProperty(value = "订舱方联系方式")
    private String clientTel;

    /** 订舱方邮箱 */
    @Excel(name = "订舱方邮箱")
    @ApiModelProperty(value = "订舱方邮箱")
    private String clientEmail;

    /** 订舱方地址 */
    @Excel(name = "订舱方地址")
    @ApiModelProperty(value = "订舱方地址")
    private String clientAddress;

    /** 报关地点 */
    @Excel(name = "报关地点")
    @ApiModelProperty(value = "报关地点")
    private String clientOrderBindingaddress;

    /** zih报关：0是 1否 */
    @Excel(name = "zih报关：0是 1否")
    @ApiModelProperty(value = "zih报关：0是 1否")
    private String clientOrderBindingway;

    /** 特殊要求备注 :0再次订舱*/
    @Excel(name = "特殊要求备注")
    @ApiModelProperty(value = "特殊要求备注")
    private String clientOrderRemarks;

    /** 集装箱箱量 */
    @Excel(name = "集装箱箱量")
    @ApiModelProperty(value = "集装箱箱量")
    private String containerBoxamount;

    /** 集装箱箱型 */
    @Excel(name = "集装箱箱型")
    @ApiModelProperty(value = "集装箱箱型")
    private String containerType;

    /** 箱型值 */
    @Excel(name = "箱型值")
    @ApiModelProperty(value = "箱型值")
    private String containerTypeval;

    /** 还箱点 */
    @Excel(name = "还箱点")
    @ApiModelProperty(value = "还箱点")
    private String clientOrderRepaycontainer;

    /** 是否派监装员 0否 1是 */
    @Excel(name = "是否派监装员 0否 1是")
    @ApiModelProperty(value = "是否派监装员 0否 1是")
    private String shipOrderIsdispatch;

    /** 发货方 */
    @Excel(name = "发货方id")
    @ApiModelProperty(value = "发货方id")
    private String shipOrderId;

    /** 发货方 */
    @Excel(name = "发货方")
    @ApiModelProperty(value = "发货方")
    private String shipOrederName;

    /** 发货方联系人 */
    @Excel(name = "发货方联系人")
    @ApiModelProperty(value = "发货方联系人")
    private String shipOrederContacts;

    /** 发货方邮箱 */
    @Excel(name = "发货方邮箱")
    @ApiModelProperty(value = "发货方邮箱")
    private String shipOrederEmail;

    /**
     * 发货方联系电话
     */
    @Excel(name = "发货方联系电话")
    @ApiModelProperty(value = "发货方联系电话")
    private String shipOrederPhone;

    /**
     * 发货方地址
     */
    @Excel(name = "发货方地址")
    @ApiModelProperty(value = "发货方地址")
    private String shipOrederAddress;

    /**
     * 发货方国家
     */
    @Excel(name = "发货方国家")
    @ApiModelProperty(value = "发货方国家")
    private String country;

    /**
     * 委托ZIH提货 0是 1否  2铁路到货
     */
    @Excel(name = "委托ZIH提货 0是 1否  2铁路到货")
    @ApiModelProperty(value = "委托ZIH提货 0是 1否  2铁路到货")
    private String shipOrderBinningway;

    /**
     * 提货地址
     */
    @Excel(name = "提货地址")
    @ApiModelProperty(value = "提货地址")
    private String shipOrderUnloadaddress;

    /** 提货联系人 */
    @Excel(name = "提货联系人")
    @ApiModelProperty(value = "提货联系人")
    private String shipOrderUnloadcontacts;

    /** 提货联系方式 */
    @Excel(name = "提货联系方式")
    @ApiModelProperty(value = "提货联系方式")
    private String shipOrderUnloadway;

    /** 提货联系邮箱 */
    @Excel(name = "提货联系邮箱")
    @ApiModelProperty(value = "提货联系邮箱")
    private String shipOrderUnloadwayEmail;

    /** 提货时间 */
    @Excel(name = "提货时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "提货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shipOrderUnloadtime;

    /** 自送货时间 */
    @Excel(name = "自送货时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "自送货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date shipOrderSendtime;

    /** 收货方id */
    @Excel(name = "收货方id")
    @ApiModelProperty(value = "收货方id")
    private String receiveOrderId;

    /** 收货方 */
    @Excel(name = "收货方")
    @ApiModelProperty(value = "收货方")
    private String receiveOrderName;

    /** 收货方联系人 */
    @Excel(name = "收货方联系人")
    @ApiModelProperty(value = "收货方联系人")
    private String receiveOrderContacts;

    /** 到站通知提货邮箱 */
    @Excel(name = "到站通知提货邮箱")
    @ApiModelProperty(value = "到站通知提货邮箱")
    private String receiveOrderMail;

    /** 收货方联系电话 */
    @Excel(name = "收货方联系电话")
    @ApiModelProperty(value = "收货方联系电话")
    private String receiveOrderPhone;

    /** 通讯地址 */
    @Excel(name = "收货方通讯地址")
    @ApiModelProperty(value = "收货方通讯地址")
    private String receiveOrderAddress;

    /** 是否由ZIH代理清关  0否 1是 */
    @Excel(name = "是否由ZIH代理清关  0否 1是")
    @ApiModelProperty(value = "是否由ZIH代理清关  0否 1是")
    private String receiveOrderIsclearance;

    /** 是否由ZIH代理送货  0否 1是 */
    @Excel(name = "是否由ZIH代理送货  0否 1是")
    @ApiModelProperty(value = "是否由ZIH代理送货  0否 1是")
    private String receiveOrderIspart;

    /** 分拨方式（0整柜派送，1拆箱散货派送) */
    private String distributionType;

    /** 送货分拨邮箱 */
    @Excel(name = "送货分拨邮箱")
    @ApiModelProperty(value = "送货分拨邮箱")
    private String receiveOrderZihemail;

    /** 分拨状态0待分拨，1已分拨 */
    @Excel(name = "分拨状态0待分拨，1已分拨")
    @ApiModelProperty(value = "分拨状态0待分拨，1已分拨")
    private String receiveOrderFbStatus;

    /** 送货地址 */
    @Excel(name = "送货地址")
    @ApiModelProperty(value = "送货地址")
    private String receiveOrderPartaddress;

    /** 在途信息接收邮箱 */
    @Excel(name = "在途信息接收邮箱")
    @ApiModelProperty(value = "在途信息接收邮箱")
    private String receiveOrderReceiveemail;

    /** 收货方英文 */
    @Excel(name = "收货方英文")
    @ApiModelProperty(value = "收货方英文")
    private String receiveOrderEnName;

    /** 收货方联系人英文 */
    @Excel(name = "收货方联系人英文")
    @ApiModelProperty(value = "收货方联系人英文")
    private String receiveOrderEnContacts;

    /** 收货方邮箱英文 */
    @Excel(name = "收货方邮箱英文")
    @ApiModelProperty(value = "收货方邮箱英文")
    private String receiveOrderEnEmail;

    /** 收货方电话英文 */
    @Excel(name = "收货方电话英文")
    @ApiModelProperty(value = "收货方电话英文")
    private String receiveOrderEnPhone;

    /** 收货方通讯地址英文 */
    @Excel(name = "收货方通讯地址英文")
    @ApiModelProperty(value = "收货方通讯地址英文")
    private String receiveOrderEnAddress;

    /** 收货方俄文 */
    @Excel(name = "收货方俄文")
    @ApiModelProperty(value = "收货方俄文")
    private String receiveOrderEname;

    /** 收货方联系人俄文 */
    @Excel(name = "收货方联系人俄文")
    @ApiModelProperty(value = "收货方联系人俄文")
    private String receiveOrderEcontacts;

    /** 收货方邮箱俄文 */
    @Excel(name = "收货方邮箱俄文")
    @ApiModelProperty(value = "收货方邮箱俄文")
    private String receiveOrderEemail;

    /** 收货方电话俄文 */
    @Excel(name = "收货方电话俄文")
    @ApiModelProperty(value = "收货方电话俄文")
    private String receiveOrderEphone;

    /** 收货方通讯地址俄文 */
    @Excel(name = "收货方通讯地址俄文")
    @ApiModelProperty(value = "收货方通讯地址俄文")
    private String receiveOrderEaddress;

    /** 中文发货人 */
    @Excel(name = "中文发货人")
    @ApiModelProperty(value = "中文发货人")
    private String consignorc;

    /** 发货人声明（俄线） */
    @Excel(name = "发货人声明", readConverterExp = "俄=线")
    @ApiModelProperty(value = "发货人声明 俄线")
    private String econsignorstate;
    private String econsignorstateS;

    /** 实际收货人名称（俄线) */
    @Excel(name = "实际收货人名称（俄线)", readConverterExp = "俄=线")
    @ApiModelProperty(value = "实际收货人名称（俄线)")
    private String etxCompany;

    /** 到货提箱公司税号（俄线） */
    @Excel(name = "到货提箱公司税号", readConverterExp = "俄=线")
    @ApiModelProperty(value = "到货提箱公司税号 俄线")
    private String eduty;

    /** 承担监管区费用的公司（或个人）名称(俄线) */
    @Excel(name = "承担监管区费用的公司", readConverterExp = "或=个人")
    @ApiModelProperty(value = "承担监管区费用的公司或=个人")
    private String etxName;

    /** 送货分拨联系人 */
    @Excel(name = "送货分拨联系人")
    @ApiModelProperty(value = "送货分拨联系人")
    private String receiveOrderZihcontacts;

    /** 送货分拨联系电话 */
    @Excel(name = "送货分拨联系电话")
    @ApiModelProperty(value = "送货分拨联系电话")
    private String receiveOrderZihtel;

    /** 是否可堆叠（1是0否2仅可自叠） */
    @Excel(name = "是否可堆叠", readConverterExp = "1=是0否2仅可自叠")
    @ApiModelProperty(value = "是否可堆叠 1=是0否2仅可自叠")
    private String goodsCannotpileup;

    /** 是否易碎（1是0否） */
    @Excel(name = "是否易碎", readConverterExp = "1=是0否")
    @ApiModelProperty(value = "是否易碎 1=是0否")
    private String goodsFragile;

    /** 单件超长超重（1是0否） */
    @Excel(name = "单件超长超重", readConverterExp = "1=是0否")
    @ApiModelProperty(value = "单件超长超重 1=是0否")
    private String goodsGeneral;
    private String goodsGeneralS;

    /** 0未审核 1已审核通过
2已审核未通过 3已取消的委托
4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    @ApiModelProperty(value = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    private String isexamline;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 托书操作创建时间 */
    @Excel(name = "托书操作创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "托书操作创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    /** 托书操作创建人ID */
    @Excel(name = "托书操作创建人ID")
    @ApiModelProperty(value = "托书操作创建人ID")
    private String createuserid;

    /** 托书操作创建人名称 */
    @Excel(name = "托书操作创建人名称")
    @ApiModelProperty(value = "托书操作创建人名称")
    private String createusername;

    /** 提货费用 */
    @Excel(name = "提货费用")
    @ApiModelProperty(value = "提货费用")
    private String shipThCost;

    /** 报关费用 */
    @Excel(name = "报关费用")
    @ApiModelProperty(value = "报关费用")
    private String clientBgCost;

    /** 箱管费 */
    @Excel(name = "箱管费")
    @ApiModelProperty(value = "箱管费")
    private String receiveXgCost;

    /** 提箱费 */
    @Excel(name = "提箱费")
    @ApiModelProperty(value = "提箱费")
    private String pickUpBoxFee;
    private String pickUpBoxFeeS;

    /** 还箱费 */
    @Excel(name = "还箱费")
    @ApiModelProperty(value = "还箱费")
    private String returnBoxFee;
    private String returnBoxFeeS;

    /** 清关费用 */
    @Excel(name = "清关费用")
    @ApiModelProperty(value = "清关费用")
    private String receiveQgCost;

    /** 监装费用 */
    @Excel(name = "监装费用")
    @ApiModelProperty(value = "监装费用")
    private String shipJzCost;

    /** 送货费用 */
    @Excel(name = "送货费用")
    @ApiModelProperty(value = "送货费用")
    private String receiveShCost;

    /** 站到站运费 */
    @Excel(name = "站到站运费")
    @ApiModelProperty(value = "站到站运费")
    private String sitecost;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    @ApiModelProperty(value = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    @Excel(name = "客户推荐人id")
    @ApiModelProperty(value = "客户推荐人id")
    private String clientTjrId;

    /** 西向去程发货提箱地 */
    @Excel(name = "西向去程提箱发货地")
    @ApiModelProperty(value = "西向去程发货提箱地")
    private String shipFhSite;
    private String shipFhSiteS;

    /** 东向回程发货提箱地 */
    @Excel(name = "东向回程发货提箱地")
    @ApiModelProperty(value = "东向回程发货提箱地")
    private String shipHyd;
    private String shipHydS;

    /** 收货还箱地 */
    @Excel(name = "收货还箱地")
    @ApiModelProperty(value = "收货还箱地")
    private String receiveHxAddress;
    private String receiveHxAddressS;

    /** 到货城市，就近还空箱 */
    @Excel(name = "到货城市，就近还空箱")
    @ApiModelProperty(value = "到货城市，就近还空箱")
    private String receiveHyd;

    /** 提货方式（整箱到车站，散货到堆场）针对整柜 */
    @Excel(name = "提货方式", readConverterExp = "整=箱到车站，散货到堆场")
    @ApiModelProperty(value = "提货方式 整=箱到车站，散货到堆场")
    private String shipThType;

    /** 委托ZIH提货（0是整箱到车站，1是散货到堆场）针对整柜 */
    @Excel(name = "委托ZIH提货", readConverterExp = "0=是整箱到车站，1是散货到堆场")
    @ApiModelProperty(value = "委托ZIH提货 0=是整箱到车站，1是散货到堆场")
    private String shipThTypeId;

    /** 发货方自送货方式 0散货到堆场 1整箱到车站 */
    @Excel(name = "发货方自送货方式 0散货到堆场 1整箱到车站")
    @ApiModelProperty(value = "发货方自送货方式 0散货到堆场 1整箱到车站")
    private String shipZsType;

    /** 发货方自送货ID */
    @Excel(name = "发货方自送货ID")
    @ApiModelProperty(value = "发货方自送货ID")
    private String shipZsTypeId;

    /** 语言（0是中文订单1是英文） */
    @Excel(name = "语言", readConverterExp = "0=是中文订单1是英文")
    @ApiModelProperty(value = "语言")
    private String yuyan;

    /** 客户每次托书提交时间 */
    @Excel(name = "客户每次托书提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户每次托书提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjTime;

    /** 重箱托书编号 */
    @Excel(name = "重箱托书编号")
    @ApiModelProperty(value = "重箱托书编号")
    private String zxNumber;

    /** 客户首次提交托书时间 */
    @Excel(name = "客户首次提交托书时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户首次提交托书时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjFTime;

    /** 1有修改记录0没有修改记录 */
    @Excel(name = "1有修改记录0没有修改记录")
    @ApiModelProperty(value = "1有修改记录0没有修改记录")
    private String isupdate;

    /** 0手机下单 */
    @Excel(name = "0手机下单")
    @ApiModelProperty(value = "0手机下单")
    private String isphone;

    /** 提货费用币种（0是美金1是人民币） */
    @Excel(name = "提货费用币种", readConverterExp = "0=是美金1是人民币")
    @ApiModelProperty(value = "提货费用币种")
    private String zxThcostCurrency;

    /** 提货费报价编号 */
    @Excel(name = "提货费报价编号")
    @ApiModelProperty(value = "提货费报价编号")
    private String shipThCostNo;

    /** 业务员编码 */
    @Excel(name = "业务员编码")
    @ApiModelProperty(value = "业务员编码")
    private String ywNumber;

    /** 业务编码 */
    @Excel(name = "业务编码")
    @ApiModelProperty(value = "业务编码")
    private String clientYwNumber;

    /** 是否上车（运综货物状态） */
    @Excel(name = "是否上车", readConverterExp = "运=综货物状态")
    @ApiModelProperty(value = "是否上车")
    private String isgetin;

    /** 每日转待审核次数（每日两次） */
    @Excel(name = "每日转待审核次数", readConverterExp = "每=日两次")
    @ApiModelProperty(value = "每日转待审核次数")
    private Long turncount;

    /** 3个月内客户转待审核次数和 */
    @Excel(name = "3个月内客户转待审核次数和")
    @ApiModelProperty(value = "3个月内客户转待审核次数和")
    private Long totalturncount;

    /** 3个月内客户转待审核次数平均数--> 订单的去程N-4 12:00之后，回程N-5 12:00之后转待审核次数*/
    @Excel(name = "3个月内客户转待审核次数平均数")
    @ApiModelProperty(value = "3个月内客户转待审核次数平均数")
    private Long totalturncountavg;

    /** 实际班列日期 */
    @Excel(name = "实际班列日期")
    @ApiModelProperty(value = "实际班列日期")
    private String actualClassdate;

    /** 业务审核费用状态：1审核成功，默认0 */
    @Excel(name = "业务审核费用状态：1审核成功，默认0")
    @ApiModelProperty(value = "业务审核费用状态：1审核成功，默认0")
    private Long ywFeedback;

    /** 去程提货地，回程送货地详细地址（去回程） */
    @Excel(name = "去程提货地，回程送货地详细地址", readConverterExp = "去=回程")
    @ApiModelProperty(value = "去程提货地，回程送货地详细地址")
    private String detailedAddress;

    /** 是否可提前班列（0是1否） */
    @Excel(name = "是否可提前班列", readConverterExp = "0=是1否")
    @ApiModelProperty(value = "是否可提前班列 0=是1否")
    private Long putoffClass;

    /** 费用确认单确认状态0未确认，1已确认 */
    @Excel(name = "费用确认单确认状态0未确认，1已确认")
    @ApiModelProperty(value = "费用确认单确认状态0未确认，1已确认")
    private String costVerify;

    /** 是否有改单 */
    @Excel(name = "是否有改单")
    @ApiModelProperty(value = "是否有改单")
    private String dcGaidanState;

    /** 送货报价编号 */
    @Excel(name = "送货报价编号")
    @ApiModelProperty(value = "送货报价编号")
    private String receiveShCostId;

    /** 收货方邮箱 */
    @Excel(name = "收货方邮箱")
    @ApiModelProperty(value = "收货方邮箱")
    private String receiveOrderEmail;

    /** 箱管费币种 */
    @Excel(name = "箱管费币种")
    @ApiModelProperty(value = "箱管费币种")
    private String xgCostcurrency;

    /** 清关费币种 */
    @Excel(name = "清关费币种")
    @ApiModelProperty(value = "清关费币种")
    private String qgCostcurrency;

    /** 送货费币种 */
    @Excel(name = "送货费币种")
    @ApiModelProperty(value = "送货费币种")
    private String shCostcurrency;

    /** 铁路运费币种 */
    @Excel(name = "铁路运费币种")
    @ApiModelProperty(value = "铁路运费币种")
    private String sitecostCurrency;

    /** 审核操作时间 */
    @Excel(name = "审核操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date exameTime;

    /** 跟单船级社证书上传状态 */
    @Excel(name = "跟单船级社证书上传状态")
    @ApiModelProperty(value = "跟单船级社证书上传状态")
    private String gdBoat;

    /** 跟单船级社证书url */
    @Excel(name = "跟单船级社证书url")
    @ApiModelProperty(value = "跟单船级社证书url")
    private String gdBoaturl;

    /** 跟单船级社证书备注 */
    @Excel(name = "跟单船级社证书备注")
    @ApiModelProperty(value = "跟单船级社证书备注")
    private String gdCjsremark;

    /** 跟单部押金保函URL */
    @Excel(name = "跟单部押金保函URL")
    @ApiModelProperty(value = "跟单部押金保函URL")
    private String gdVoucherurl;

    /** 跟单押金保函上传状态0是已上传 */
    @Excel(name = "跟单押金保函上传状态0是已上传")
    @ApiModelProperty(value = "跟单押金保函上传状态0是已上传")
    private String gdVoucher;

    /** 唛头 */
    @Excel(name = "唛头")
    private String goodsMark;

    /** 货品中文名称 */
    @Excel(name = "货品中文名称")
    private String goodsName;

    /** 货品英文名称 */
    @Excel(name = "货品英文名称")
    private String goodsEnName;

    /** 国外报关HS(订单货物表) */
    @Excel(name = "国外报关HS")
    @ApiModelProperty(value = "国外报关HS")
    private String goodsReport;

    /** 国内报关HS(订单货物表) */
    @Excel(name = "国内报关HS")
    @ApiModelProperty(value = "国内报关HS")
    private String goodsInReport;

    /** 国内清关HS (订单货物表)*/
    @Excel(name = "国内清关HS")
    @ApiModelProperty(value = "国内清关HS")
    private String goodsClearance;

    /** 国外清关HS (订单货物表)*/
    @Excel(name = "国外清关HS")
    @ApiModelProperty(value = "国外清关HS")
    private String goodsOutClearance;

    /** 最外层包装形式 */
    @Excel(name = "最外层包装形式")
    private String goodsPacking;

    /** 最外层包装数量 */
    @Excel(name = "最外层包装数量")
    private String goodsNumber;

    /** 规格 */
    @Excel(name = "规格")
    private String goodsStandard;

    /** 重量 */
    @Excel(name = "重量")
    private String goodsKgs;

    /** 体积 */
    @Excel(name = "体积")
    private String goodsCbm;

    /** 是否需要装箱方案：0需要 1不需要 */
    @Excel(name = "是否需要装箱方案：0需要 1不需要")
    private String goodsIsscheme;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
    private String remarkS;

    /** 托书修改记录 */
    @Excel(name = "托书修改记录")
    private String goodsHistoryEditrecord;

    /** 货物备注 */
    @Excel(name = "货物备注")
    private String goodsbz;

    /** 货物hs编码备注 */
    @Excel(name = "货物hs编码备注")
    private String hsbz;

    /** 是否含有放射性 */
    @Excel(name = "是否含有放射性")
    private String radioaction;

    /** 业务开票状态1是已开票 */
    @Excel(name = "业务开票状态1是已开票")
    private String kpState;

    /** 操作类别*/
    @Excel(name = "操作类别：0审核 1编辑")
    @ApiModelProperty(value = "操作类别：0审核 1编辑")
    private String operateType;

    /** 运综状态 */
    @Excel(name = "运综状态")
    private String trainState;

    /** 申请转待类别*/
    @Excel(name = "申请转待类别")
    @ApiModelProperty(value = "申请转待类别")
    private String examType;

    /** 申请转待原因*/
    @Excel(name = "申请转待原因")
    @ApiModelProperty(value = "申请转待原因")
    private String examInfo;

    /** 订舱组对此条信息的备注说明*/
    @Excel(name = "订舱组对此条信息的备注说明")
    @ApiModelProperty(value = "订舱组对此条信息的备注说明")
    private String examRemark;

    /** 托书是否删除标识 0否 1是*/
    @Excel(name = "托书是否删除标识 0否 1是")
    @ApiModelProperty(value = "托书是否删除标识 0否 1是")
    private String isDelete;

    /** 驳回原因*/
    @Excel(name = "驳回原因")
    @ApiModelProperty(value = "驳回原因")
    private String refuseInfo;

    /**国内拼箱场站报价单号*/
    @Excel(name = "国内拼箱场站报价单号")
    private String domesticNumber;

    /**国外拼箱场站报价单号*/
    @Excel(name = "国外拼箱场站报价单号")
    private String foreignNumber;



    //托书询价补充
    /** 询价结果id */
    @ApiModelProperty(value = "询价结果id")
    private String inquiryRecordId;
    /** 时效（箱型亚欧，0普通，1加急） */
    @ApiModelProperty(value = "时效（箱型亚欧，0普通，1加急）")
    private String limitation;
    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车 */
    @ApiModelProperty(value = "国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车")
    private String truckType;
    /** 发货地 */
    @ApiModelProperty(value = "发货地")
    private String shipmentPlace;
    /** 收货地 */
    @ApiModelProperty(value = "收货地")
    private String receiptPlace;

    //转待审核询价表字段
    //询价表id
    @ApiModelProperty(value = "询价id")
    private Long inquiryId;
    /** 发货地省份 */
    @ApiModelProperty(value = "发货地省份")
    private String senderProvince;
    /** 发货地城市 */
    @ApiModelProperty(value = "发货地城市")
    private String senderCity;
    /** 发货地区域 */
    @ApiModelProperty(value = "发货地区域")
    private String senderArea;
    /** 发货地 shipmentPlace*/
    /** 上货站 */
    @ApiModelProperty(value = "上货站")
    private String uploadStation;
    /** 收货地省份 */
    @ApiModelProperty(value = "收货地省份")
    private String receiveProvince;
    /** 收货地城市 */
    @ApiModelProperty(value = "收货地城市")
    private String receiveCity;
    /** 收货地区域 */
    @ApiModelProperty(value = "收货地区域 ")
    private String receiveArea;
    /** 收货地 receiptPlace*/
    /** 下货站 */
    @ApiModelProperty(value = "下货站")
    private String dropStation;
    /** 服务（0：门到门；1：门到站；2：站到站；3：站到门） bookingService*/
    /** 是否委托ZIH提货 （0是 1否  2铁路到货）不能为空 */
    @Excel(name = "是否委托zih提货", readConverterExp = "0=：是；1：否")
    @ApiModelProperty(value = "是否委托ZIH提货 （0是 1否  2铁路到货）")
    private String isPickUp;
    /** 是否由ZIH代理送货  （0否 1是），不能为空 */
    @Excel(name = "是否委托zih送货", readConverterExp = "0=：否；1：是")
    @ApiModelProperty(value = "是否由ZIH代理送货  （0否 1是）")
    private String isDelivery;
    /** 是否委托zih代理报关（0是 1否），不能为空 */
    @Excel(name = "是否委托zih代理报关", readConverterExp = "0=：是；1：否")
    @ApiModelProperty(value = "是否委托zih代理报关（0是 1否）")
    private String isOrderCustoms;
    /** 是否由ZIH代理清关 （0否 1是） ，不能为空 */
    @Excel(name = "是否委托zih代理清关", readConverterExp = "0=：否；1：是")
    @ApiModelProperty(value = "是否由ZIH代理清关 （0否 1是）")
    private String isClearCustoms;
    /** 箱属（0：自备；1：zih），不能为空 */
    @Excel(name = "箱属", readConverterExp = "0=：自备；1：zih")
    private String containerBelong;
    /** 货物类型（0：整柜；1：拼箱） */
    @Excel(name = "货物类型", readConverterExp = "0=：整柜；1：拼箱")
    private String goodsType;
    /** 箱型，不能为空 containerType*/
    /** 箱量，不能为空 */
    @Excel(name = "箱量")
    private Integer containerNum;
    /** 包装方式 */
    @Excel(name = "包装方式")
    private String packageType;
    /** 是否可堆叠（0否：1是） */
    @Excel(name = "是否可堆叠", readConverterExp = "0=否：1是")
    private String isStack;
    /** 总数量 */
    @Excel(name = "总数量")
    private String totalAmount;
    /** 总重量(kg) */
    @Excel(name = "总重量(kg)")
    private String totalWeight;
    /** 总体积(m³) */
    @Excel(name = "总体积(m³)")
    private String totalVolume;
    /** 询价时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inquiryTime;
    /** 客户id，不能为空 clientId*/
    /** 删除标识（0未删除；1：已删除） */
    private String delFlag;
    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    @Excel(name = "线路线路类型", readConverterExp = "0=郑欧（中欧")
    private String lineType;
    /** 还箱地 */
    @Excel(name = "还箱地")
    private String hxAddress;
    /** 去回程,0为去程 1为回程*/
    private String eastOrWest;
    /** 上货站编号 */
    @Excel(name = "上货站编号")
    private String uploadStationNum;
    /** 下货站编号 */
    @Excel(name = "下货站编号")
    private String dropStationNum;
    /** 备注 remark*/
    /**时效（箱型亚欧，0普通，1加急） limitation*/
    /**提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择时候才会出现这个选项 */
    private String deliveryType;
    /**客户类型 0货代 1直客 2贸易公司*/
    private String clientType;
    /**客户单位名称 clientUnit*/
    /**是否易碎（1是0否） goodsFragile*/
    /**询价状态（报价中）、2（待审核）、3（已报价）*/
    private String inquiryState;
    /** 询价时间*/
    private Date createTime;
    /**还箱地id*/
    private String hxAddressId;
    /** 清关费用标准 */
    @Excel(name = "清关费用标准")
    private String qgfy;
    /** 场站费用标准 */
    @Excel(name = "场站费用标准")
    private String czfy;
    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String turndownReason;
    /**拼箱物品详细信息，长、宽、高、数量、单件货物重量*/
    private List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList;
    private String bookingInquiryGoodsDetails;
    /** 语言*/
    @ApiModelProperty(value = "语言 ")
    private String language;

    /** 是否需要子系统审核报价*/
    @ApiModelProperty(value = "是否需要子系统报价 0否 1是 ")
    private String isPriceCheck;

    /** 是否审核提货时间*/
    @ApiModelProperty(value = "是否审核提货时间 0否 1是 ")
    private String isThCheck;

    /** 是否需要订舱组审核*/
    @ApiModelProperty(value = "是否订舱组审核 0否 1是 ")
    private String isManageCheck;

    /** 箱量差值*/
    private Double boxNumDiff;
    /** 体积差值*/
    private Double volumeDiff;
    /** 重量差值*/
    private Double weightDiff;

    private String bookingTimeFlag; //0当月1次月

    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date nextMonth;

    private Date validDate;

    /**操作类别 0转待审核 1再次订舱*/
    private String tjType;

}
