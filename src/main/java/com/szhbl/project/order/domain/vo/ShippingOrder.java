package com.szhbl.project.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShippingOrder implements Serializable {

//  列表字段
    /** 订单id,主键 */
    @Excel(name = "订单id")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    @ApiModelProperty(value = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    @Excel(name = "客户推荐人id")
    @ApiModelProperty(value = "客户推荐人id")
    private String clientTjrId;

    /** 客户推荐人邮箱 */
    @Excel(name = "客户推荐人邮箱")
    @ApiModelProperty(value = "客户推荐人邮箱")
    private String clientTjrEmail;

    /** 东向跟单姓名 */
    @Excel(name = "东向跟单姓名")
    @ApiModelProperty(value = "东向跟单姓名")
    private String orderMerchandiser;

    /** 东向跟单ID */
    @Excel(name = "东向跟单ID")
    @ApiModelProperty(value = "东向跟单ID")
    private String orderMerchandiserId;

    /** 西向跟单姓名 */
    @Excel(name = "西向跟单姓名")
    @ApiModelProperty(value = "西向跟单姓名")
    private String merchandiserW;

    /** 西向跟单ID */
    @Excel(name = "西向跟单ID")
    @ApiModelProperty(value = "西向跟单ID")
    private String merchandiserIdW;

    /** 跟单邮箱 */
    @Excel(name = "跟单邮箱")
    @ApiModelProperty(value = "跟单邮箱")
    private String orderMerchandiserEmail;

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

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列到站日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classEtime;

    /** 实际班列日期 */
    @Excel(name = "实际班列日期")
    @ApiModelProperty(value = "实际班列日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String actualClassdate;

    /** 班列号 */
    @Excel(name = "班列号")
    @ApiModelProperty(value = "班列号")
    private String classNumber;

    /** 班列ID */
    @Excel(name = "班列ID")
    @ApiModelProperty(value = "班列ID")
    private String classId;

    /** 班列编号 （班列表）*/
    @Excel(name = "班列编号")
    private String classBh;

    /** 班列编号 （托书表）*/
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    private String orderClassBh;

    /** 0未审核 1已审核通过 2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 7询价中")
    @ApiModelProperty(value = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 7询价中")
    private String isexamline;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程(西向) 1为回程(东向)")
    private String classEastandwest;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 （班列表）*/
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型：0是中欧2是中亚3是中越4是中俄")
    private String lineTypeid;

    /** 线路名称*/
    @Excel(name = "线路名称")
    @ApiModelProperty(value = "线路名称")
    private String lineName;

    /** 站点ID,回程入货通知书上堆场地址 */
    @Excel(name = "站点ID,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "站点ID,回程入货通知书上堆场地址")
    private String stationid;

    /** 站点ID,回程入货通知书上堆场地址 (busi_station表)*/
    @Excel(name = "车站名称,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "车站名称,回程入货通知书上堆场地址")
    private String station;

    /** 语言（0是中文订单1是英文） */
    @Excel(name = "语言", readConverterExp = "0=是中文订单1是英文")
    @ApiModelProperty(value = "语言")
    private String yuyan;

    /** 0手机下单 */
    @Excel(name = "0手机下单，1pc端下单")
    @ApiModelProperty(value = "0手机下单，1pc端下单")
    private String isphone;

    /** 部门名称 */
    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orderDeptName;

    @Excel(name = "业务部门编号")
    @ApiModelProperty(value = "业务部门编号")
    private String organizationCode;

    /** 委托ZIH提货 0是 1否  2铁路到货 */
    @Excel(name = "委托ZIH提货 0是 1否  2铁路到货")
    @ApiModelProperty(value = "委托ZIH提货 0是 1否  2铁路到货")
    private String shipOrderBinningway;

    /** 是否有改单 */
    @Excel(name = "是否有改单")
    @ApiModelProperty(value = "是否有改单")
    private String dcGaidanState;

    /** 货品中文名称 （订单商品表）*/
    @Excel(name = "货品中文名称")
    @ApiModelProperty(value = "货品中文名称")
    private String goodsName;

    /** 业务员编码 */
    @Excel(name = "业务员编码")
    @ApiModelProperty(value = "业务员编码")
    private String ywNumber;

    /** 业务编码 */
    @Excel(name = "业务编码")
    @ApiModelProperty(value = "业务编码")
    private String clientYwNumber;

    /** 业务编码 */
    @Excel(name = "查询站点")
    @ApiModelProperty(value = "查询站点")
    private String[] siteCode;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "订单号 托书编号")
    private String orderNumber;

    /** 客户首次提交托书时间 */
    @Excel(name = "客户首次提交托书时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户首次提交托书时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjFTime;

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

    /** 集装箱箱型 */
    @Excel(name = "集装箱箱型")
    @ApiModelProperty(value = "集装箱箱型")
    private String containerType;

    /** 集装箱箱量 */
    @Excel(name = "集装箱箱量")
    @ApiModelProperty(value = "集装箱箱量")
    private String containerBoxamount;

    /** 体积 (订单商品表)*/
    @Excel(name = "体积(订单商品表)")
    @ApiModelProperty(value = "体积")
    private String goodsCbm;

    /** 重量 (订单商品表)*/
    @Excel(name = "重量")
    @ApiModelProperty(value = "重量")
    private String goodsKgs;

    /** 上货站 */
    @Excel(name = "上货站")
    @ApiModelProperty(value = "上货站")
    private String orderUploadsite;
    private String orderUploadsiteEn;

    /** 下货站 */
    @Excel(name = "下货站")
    @ApiModelProperty(value = "下货站")
    private String orderUnloadsite;
    private String orderUnloadsiteEn;

    /** 上货站唯一码 */
    @Excel(name = "上货站唯一码")
    @ApiModelProperty(value = "上货站唯一码")
    private String orderUploadcode;

    /** 下货丫唯一码 */
    @Excel(name = "下货站唯一码")
    @ApiModelProperty(value = "下货站唯一码")
    private String orderUnloadcode;

    /** 发货方 */
    @Excel(name = "发货方")
    @ApiModelProperty(value = "发货方")
    private String shipOrederName;

    /** 3个月内客户转待审核次数平均数 */
    @Excel(name = "3个月内客户转待审核次数平均数")
    @ApiModelProperty(value = "3个月内客户转待审核次数平均数")
    private Long totalturncountavg;

//    订单详情
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

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    @ApiModelProperty(value = "创建用户姓名")
    private String orderAuditUsername;

    /** 班列站点 */
    @Excel(name = "班列站点")
    @ApiModelProperty(value = "班列站点")
    private String classSite;

    /** 贸易方式（条款ID） */
    @Excel(name = "贸易方式", readConverterExp = "条款ID")
    @ApiModelProperty(value = "贸易方式")
    private Long dictId;

    /** 条款名称 */
    @Excel(name = "条款名称")
    @ApiModelProperty(value = "条款名称")
    private String dictName;

    /** 是否需要发票 */
    @Excel(name = "是否需要发票")
    @ApiModelProperty(value = "是否需要发票")
    private String orderIsticket;

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

    /** 特殊要求备注 */
    @Excel(name = "特殊要求备注")
    @ApiModelProperty(value = "特殊要求备注")
    private String clientOrderRemarks;

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

    /** 发货方联系人 */
    @Excel(name = "发货方联系人")
    @ApiModelProperty(value = "发货方联系人")
    private String shipOrederContacts;

    /** 发货方邮箱 */
    @Excel(name = "发货方邮箱")
    @ApiModelProperty(value = "发货方邮箱")
    private String shipOrederEmail;

    /** 发货方联系电话 */
    @Excel(name = "发货方联系电话")
    @ApiModelProperty(value = "发货方联系电话")
    private String shipOrederPhone;

    /** 发货方地址 */
    @Excel(name = "发货方地址")
    @ApiModelProperty(value = "发货方地址")
    private String shipOrederAddress;

    /** 提货地址 */
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

    /** 收货还箱地 */
    @Excel(name = "收货还箱地")
    @ApiModelProperty(value = "收货还箱地")
    private String receiveHxAddress;

    /** 箱管费 */
    @Excel(name = "箱管费")
    @ApiModelProperty(value = "箱管费")
    private String receiveXgCost;

    /** 提箱费 */
    @Excel(name = "提箱费")
    @ApiModelProperty(value = "提箱费")
    private String pickUpBoxFee;

    /** 还箱费 */
    @Excel(name = "还箱费")
    @ApiModelProperty(value = "还箱费")
    private String returnBoxFee;

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

    /** 回程发货提箱地 */
    @Excel(name = "回程发货提箱地")
    @ApiModelProperty(value = "回程发货提箱地")
    private String shipFhSite;

    /** 去程发货提箱地 */
    @Excel(name = "去程发货提箱地")
    @ApiModelProperty(value = "去程发货提箱地")
    private String shipHyd;

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

    /** 发货方自送货ID  0散货到堆场 1整箱到车站*/
    @Excel(name = "发货方自送货ID")
    @ApiModelProperty(value = "发货方自送货ID  0散货到堆场 1整箱到车站")
    private String shipZsTypeId;

    /** 1有修改记录0没有修改记录 */
    @Excel(name = "1有修改记录0没有修改记录")
    @ApiModelProperty(value = "1有修改记录0没有修改记录")
    private String isupdate;

    /** 提货费用币种（0是美金1是人民币） */
    @Excel(name = "提货费用币种", readConverterExp = "0=是美金1是人民币")
    @ApiModelProperty(value = "提货费用币种")
    private String zxThcostCurrency;

    /** 提货费报价编号 */
    @Excel(name = "提货费报价编号")
    @ApiModelProperty(value = "提货费报价编号")
    private String shipThCostNo;

    /** 是否上车（运综货物状态） */
    @Excel(name = "是否上车", readConverterExp = "运=综货物状态")
    @ApiModelProperty(value = "是否上车")
    private String isgetin;

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

    //订单详情-货物
    /** 唛头 (订单货物表)*/
    @Excel(name = "唛头")
    @ApiModelProperty(value = "唛头")
    private String goodsMark;

    /** 货品英文名称 (订单货物表)*/
    @Excel(name = "货品英文名称")
    @ApiModelProperty(value = "货品英文名称")
    private String goodsEnName;

    /** 国外报关HS(订单货物表) */
    @Excel(name = "国外报关HS")
    @ApiModelProperty(value = "国外报关HS")
    private String goodsReport;

    /** 国内报关HS(订单货物表) */
    @Excel(name = "国内报关HS")
    @ApiModelProperty(value = "国外报关HS")
    private String goodsInReport;

    /** 国内清关HS (订单货物表)*/
    @Excel(name = "国内清关HS")
    @ApiModelProperty(value = "国内清关HS")
    private String goodsClearance;

    /** 国外清关HS (订单货物表)*/
    @Excel(name = "国外清关HS")
    @ApiModelProperty(value = "国外清关HS")
    private String goodsOutClearance;

    /** 最外层包装形式 (订单货物表)*/
    @Excel(name = "最外层包装形式")
    @ApiModelProperty(value = "最外层包装形式")
    private String goodsPacking;

    /** 最外层包装数量 (订单货物表)*/
    @Excel(name = "最外层包装数量")
    @ApiModelProperty(value = "最外层包装数量")
    private String goodsNumber;

    /** 规格 长*宽*高*数量 (订单货物表)*/
    @Excel(name = "规格")
    @ApiModelProperty(value = "规格")
    private String goodsStandard;

    /** 货物单件重量 重量*数量 (订单货物表)*/
    private String oneStandard;

    /** 是否需要装箱方案：0需要 1不需要 (订单货物表)*/
    @Excel(name = "是否需要装箱方案：0需要 1不需要")
    @ApiModelProperty(value = "是否需要装箱方案：0需要 1不需要")
    private String goodsIsscheme;

    /** 货物备注 (订单货物表)*/
    @Excel(name = "货物备注")
    @ApiModelProperty(value = "货物备注")
    private String goodsbz;

    /** 货物备注 (订单货物表)*/
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 货物hs编码备注 (订单货物表)*/
    @Excel(name = "货物hs编码备注")
    @ApiModelProperty(value = "货物hs编码备注")
    private String hsbz;

    /** 是否含有放射性 (订单货物表)*/
    @Excel(name = "是否含有放射性")
    @ApiModelProperty(value = "是否含有放射性")
    private String radioaction;

    /** 业务开票状态1是已开票 (订单货物表)*/
    @Excel(name = "业务开票状态1是已开票")
    @ApiModelProperty(value = "业务开票状态1是已开票")
    private String kpState;

    /** 操作类别*/
    @Excel(name = "操作类别：0审核 1编辑")
    @ApiModelProperty(value = "操作类别：0审核 1编辑")
    private String operateType;

    //详情补充属性
    /** 发货方id*/
    @Excel(name = "发货方id")
    @ApiModelProperty(value = "发货方id")
    private String shipOrderId;

    /** 操作类别*/
    @Excel(name = "收货id")
    @ApiModelProperty(value = "收货方id")
    private String receiveOrderId;

    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "服务：0门到门 1门到站 2站到站 3站到门", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "服务：0门到门 1门到站 2站到站 3站到门")
    private String bookingService;

    /** 每日转待审核次数（每日两次） */
    @Excel(name = "每日转待审核次数", readConverterExp = "每=日两次")
    @ApiModelProperty(value = "每日转待审核次数")
    private Long turncount;

    /** 3个月内客户转待审核次数和 */
    @Excel(name = "3个月内客户转待审核次数和")
    @ApiModelProperty(value = "3个月内客户转待审核次数和")
    private Long totalturncount;

    /** 是否删除标识 */
    @Excel(name = "是否删除标识 ")
    @ApiModelProperty(value = "是否删除标识 ")
    private String isDelete;

    /** 始发站编号 */
    @Excel(name = "始发站编号")
    @ApiModelProperty(value = "始发站编号 ")
    private String classStationofdeparture;

    /** 目的站编号 */
    @Excel(name = "目的站编号")
    @ApiModelProperty(value = "目的站编号 ")
    private String classStationofdestination;

    /** 车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列 */
    @Excel(name = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列")
    @ApiModelProperty(value = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列 ")
    private String classState;

    /** 箱号 */
    @Excel(name = "箱号")
    @ApiModelProperty(value = "箱号 ")
    private String boxNo;

    /** 托书修改记录 */
    @Excel(name = "托书修改记录")
    @ApiModelProperty(value = "托书修改记录")
    private String editRecord;

    /** 整箱地址 */
    @Excel(name = "整箱地址")
    private String zxAddress;

    /** 车站地址 */
    @Excel(name = "车站地址")
    private String pxYstype;

    /** 拼箱地址 */
    @Excel(name = "拼箱地址")
    private String pxAddress;

    /** 截港时间 */
    @Excel(name = "截港时间")
    private String cuntofftime;

    /** 口岸 */
    @Excel(name = "口岸")
    private String classPort;

    /** 消息类别 */
    @ApiModelProperty(value = "消息类别")
    private String messageType;

    /** 审核失败原因 */
    @ApiModelProperty(value = "审核失败原因")
    private String examInfo;

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

    /** 危险品颜色  0 无样式， 1 边框blue,5px,字体red,16px; */
    @ApiModelProperty(value = "危险品颜色")
    private String nameColor;

    /** 危险品备注 */
    @ApiModelProperty(value = "危险品备注")
    private String nameRemark;

    /** 特殊单证颜色 0 无样式 ，1 边框blue,5px,字体red,16px;，2字体green,16px; */
    @ApiModelProperty(value = "特殊单证颜色 ")
    private String noteColor;

    /** 特殊单证备注 */
    @ApiModelProperty(value = "特殊单证备注")
    private String noteRemark;

    /** 提货时间审核 0集疏 1公路*/
    private String picktimeCheck;

    /** 驳回原因*/
    @Excel(name = "驳回原因")
    @ApiModelProperty(value = "驳回原因")
    private String refuseInfo;

    /** 询价id */
    private Long inquiryId;

    /** type类型，0转待审核，1新增订舱 2再次订舱*/
    private String type;

    //货物详情
    private String goodsInfoDetail;

    private String hxdExamine;

    /**国内拼箱场站报价单号*/
    @Excel(name = "国内拼箱场站报价单号")
    private String domesticNumber;

    /**国外拼箱场站报价单号*/
    @Excel(name = "国外拼箱场站报价单号")
    private String foreignNumber;

//
//    //转待审核询价表字段
//    //询价表id
//    @ApiModelProperty(value = "询价id")
//    private Long inquiryId;
//    /** 发货地省份 */
//    @ApiModelProperty(value = "发货地省份")
//    private String senderProvince;
//    /** 发货地城市 */
//    @ApiModelProperty(value = "发货地城市")
//    private String senderCity;
//    /** 发货地区域 */
//    @ApiModelProperty(value = "发货地区域")
//    private String senderArea;
//    /** 发货地 shipmentPlace*/
//    /** 上货站 */
//    @ApiModelProperty(value = "上货站")
//    private String uploadStation;
//    /** 收货地省份 */
//    @ApiModelProperty(value = "收货地省份")
//    private String receiveProvince;
//    /** 收货地城市 */
//    @ApiModelProperty(value = "收货地城市")
//    private String receiveCity;
//    /** 收货地区域 */
//    @ApiModelProperty(value = "收货地区域 ")
//    private String receiveArea;
//    /** 收货地 receiptPlace*/
//    /** 下货站 */
//    @ApiModelProperty(value = "下货站")
//    private String dropStation;
//    /** 服务（0：门到门；1：门到站；2：站到站；3：站到门） bookingService*/
//    /** 是否委托ZIH提货 （0是 1否  2铁路到货）不能为空 */
//    @Excel(name = "是否委托zih提货", readConverterExp = "0=：是；1：否")
//    @ApiModelProperty(value = "是否委托ZIH提货 （0是 1否  2铁路到货）")
//    private String isPickUp;
//    /** 是否由ZIH代理送货  （0否 1是），不能为空 */
//    @Excel(name = "是否委托zih送货", readConverterExp = "0=：否；1：是")
//    @ApiModelProperty(value = "是否由ZIH代理送货  （0否 1是）")
//    private String isDelivery;
//    /** 是否委托zih代理报关（0是 1否），不能为空 */
//    @Excel(name = "是否委托zih代理报关", readConverterExp = "0=：是；1：否")
//    @ApiModelProperty(value = "是否委托zih代理报关（0是 1否）")
//    private String isOrderCustoms;
//    /** 是否由ZIH代理清关 （0否 1是） ，不能为空 */
//    @Excel(name = "是否委托zih代理清关", readConverterExp = "0=：否；1：是")
//    @ApiModelProperty(value = "是否由ZIH代理清关 （0否 1是）")
//    private String isClearCustoms;
//    /** 箱属（0：自备；1：zih），不能为空 */
//    @Excel(name = "箱属", readConverterExp = "0=：自备；1：zih")
//    private String containerBelong;
//    /** 货物类型（0：整柜；1：拼箱） */
//    @Excel(name = "货物类型", readConverterExp = "0=：整柜；1：拼箱")
//    private String goodsType;
//    /** 箱型，不能为空 containerType*/
//    /** 箱量，不能为空 */
//    @Excel(name = "箱量")
//    private Integer containerNum;
//    /** 包装方式 */
//    @Excel(name = "包装方式")
//    private String packageType;
//    /** 是否可堆叠（0否：1是） */
//    @Excel(name = "是否可堆叠", readConverterExp = "0=否：1是")
//    private String isStack;
//    /** 总数量 */
//    @Excel(name = "总数量")
//    private String totalAmount;
//    /** 总重量(kg) */
//    @Excel(name = "总重量(kg)")
//    private String totalWeight;
//    /** 总体积(m³) */
//    @Excel(name = "总体积(m³)")
//    private String totalVolume;
//    /** 询价时间 */
//    @Excel(name = "询价时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date inquiryTime;
//    /** 客户id，不能为空 clientId*/
//    /** 删除标识（0未删除；1：已删除） */
//    private String delFlag;
//    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
//    @Excel(name = "线路线路类型", readConverterExp = "0=郑欧（中欧")
//    private String lineType;
//    /** 还箱地 */
//    @Excel(name = "还箱地")
//    private String hxAddress;
//    /** 去回程,0为去程 1为回程*/
//    private String eastOrWest;
//    /** 上货站编号 */
//    @Excel(name = "上货站编号")
//    private String uploadStationNum;
//    /** 下货站编号 */
//    @Excel(name = "下货站编号")
//    private String dropStationNum;
//    /** 备注 remark*/
//    /**时效（箱型亚欧，0普通，1加急） limitation*/
//    /**提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择时候才会出现这个选项 */
//    private String deliveryType;
//    /**客户类型 0货代 1直客 2贸易公司*/
//    private String clientType;
//    /**客户单位名称 clientUnit*/
//    /**是否易碎（1是0否） goodsFragile*/
//    /**询价状态（报价中）、2（待审核）、3（已报价）*/
//    private String inquiryState;
//    /** 询价时间*/
//    private Date createTime;
//    /**还箱地id*/
//    private String hxAddressId;
//    /** 清关费用标准 */
//    @Excel(name = "清关费用标准")
//    private String qgfy;
//    /** 场站费用标准 */
//    @Excel(name = "场站费用标准")
//    private String czfy;
//    /** 驳回原因 */
//    @Excel(name = "驳回原因")
//    private String turndownReason;
//    /**拼箱物品详细信息，长、宽、高、数量、单件货物重量*/
//    private List<BookingInquiryGoodsDetails> bookingInquiryGoodsDetailsList;

//    //转待审核询价货物详情表字段
//    //询价货物详情表id
//    @ApiModelProperty(value = "询价id")
//    private Long id;
//    /** 询价id inquiryId*/
//    /** 货物品名 */
//    @Excel(name = "货物品名")
//    private String goodsNames;
//    /** 货物数量 */
//    @Excel(name = "货物数量")
//    private String goodsAmount;
//    /** 货物重量 */
//    @Excel(name = "单件货物重量")
//    private String goodsWeight;
//    /** 长(mm) */
//    @Excel(name = "长(mm)")
//    private String goodsLength;
//    /** 宽(mm) */
//    @Excel(name = "宽(mm)")
//    private String goodsWidth;
//    /** 高(mm) */
//    @Excel(name = "高(mm)")
//    private String goodsHeight;

//    //转待审核询价结果表字段
//    /** 询价结果表id inquiryRecordId*/
//    /** 询价表id inquiryId*/
//    /** 上货站  uploadStation*/
//    /** 下货站  dropStation*/
//    /** 上货站备注 */
//    @Excel(name = "上货站备注")
//    private String uploadStationRemark;
//    /** 下货站备注 */
//    @Excel(name = "下货站备注")
//    private String dropStationRemark;
//    /** 提货地 */
//    @Excel(name = "提货地")
//    private String pickUpAddress;
//    /** 提货距离 */
//    @Excel(name = "提货距离")
//    private String pickUpDistance;
//    /** 提货时效 */
//    @Excel(name = "提货时效")
//    private String pickUpAging;
//    /** 提货费 */
//    @Excel(name = "提货费")
//    private String pickUpFees;
//    /** 提货费币种 */
//    @Excel(name = "提货费币种")
//    private String pickUpCurrencyType;
//    /** 提货费有效期 */
//    @Excel(name = "提货费有效期", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date pickUpExpiration;
//    /** 提货备注 */
//    @Excel(name = "提货备注")
//    private String pickUpRemark;
//    /** 提货报价反馈时间 */
//    @Excel(name = "提货报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date pickUpFeedbackTime;
//    /** 铁路运费 */
//    @Excel(name = "铁路运费")
//    private String railwayFees;
//    /** 铁路运费币种 */
//    @Excel(name = "铁路运费币种")
//    private String railwayCurrencyType;
//    /** 铁路收费标准 */
//    @Excel(name = "铁路收费标准")
//    private String railwayCharges;
//    /** 铁路费用有效期 */
//    @Excel(name = "铁路费用有效期", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date railwayExpiration;
//    /** 铁路时效 */
//    @Excel(name = "铁路时效")
//    private String railwayAging;
//    /** 铁路备注 */
//    @Excel(name = "铁路备注")
//    private String railwayRemark;
//    /** 铁路报价反馈时间 */
//    @Excel(name = "铁路报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date railwayFeedbackTime;
//    /** 派送距离 */
//    @Excel(name = "派送距离")
//    private String deliveryDistance;
//    /** 派送地 */
//    @Excel(name = "派送地")
//    private String deliveryAddress;
//    /** 派送费用 */
//    @Excel(name = "派送费用")
//    private String deliveryFees;
//    /** 派送费用币种 */
//    @Excel(name = "派送费用币种")
//    private String deliveryCurrencyType;
//    /** 派送时效 */
//    @Excel(name = "派送时效")
//    private String deliveryAging;
//    /** 派送报价有效期 */
//    @Excel(name = "派送报价有效期", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date deliveryExpiration;
//    /** 派送备注 */
//    @Excel(name = "派送备注")
//    private String deliveryRemark;
//    /** 派送报价反馈时间 */
//    @Excel(name = "派送报价反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date deliveryFeedbackTime;
//    /** 删除标识（0未删除；1已删除） */
//    private String delFlagRes;
//    /** 确认标识（0未确认；1已确认） */
//    @Excel(name = "确认标识", readConverterExp = "0=未确认；1已确认")
//    private String confirmFlag;
//    /** 确认时间 */
//    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date confirmTime;
//    /** 确认人 */
//    @Excel(name = "确认人")
//    private Long confirmUser;
//    private BookingInquiry bookingInquiry;
//    /** 集疏备注*/
//    private String jsRemark;
//    /** 还箱费 */
//    @Excel(name = "还箱费")
//    private String returnBoxFee;
//    /** 理货费 */
//    @Excel(name = "理货费")
//    private String lhuFee;
//    /** 超长超重费 */
//    @Excel(name = "超长超重费")
//    private String ccczFee;
//    /** 提空箱费 */
//    @Excel(name = "提空箱费")
//    private String tkxFee;
//    /** 加固费 */
//    @Excel(name = "加固费")
//    private String jgFee;
//    /** 打托费 */
//    @Excel(name = "打托费")
//    private String dtFee;
//    /** 装拆箱费 */
//    @Excel(name = "装拆箱费")
//    private String zcxFee;
//    /** 集疏报价编码*/
//    private String inquiryNum;
//    /**询价状态 enquiry_state"=1（报价中）、2（待审核）、3（已报价)*/
//    private String enquiryState;
//    /** 去程还箱地  hxAddress*/
//    /** 回程提箱地 */
//    private String txAddress;
//    /**铁路运费备注*/
//    private String railRemark;
//    /**箱型亚欧备注*/
//    private String xxyoRemark;
//    /**业务备注*/
//    private String businessRemark;

}
