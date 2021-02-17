package com.szhbl.project.order.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订单转待审核暂存对象 busi_shippingorder_apply
 * 
 * @author dps
 * @date 2020-04-13
 */
public class BusiShippingorderApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id,主键 */
    private String orderId;

    /** 站点ID,回程入货通知书上堆场地址 */
    @Excel(name = "站点ID,回程入货通知书上堆场地址")
    private String stationid;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号", readConverterExp = "委=托书编号")
    private String orderNumber;

    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "服务：0门到门 1门到站 2站到站 3站到门")
    private String bookingService;

    /** 集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱 */
    @Excel(name = "集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱")
    private String orderAuditBelongto;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderAuditCreatedate;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private String orderAuditUserid;

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    private String orderAuditUsername;

    /** 班列ID */
    @Excel(name = "班列ID")
    private String classId;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date classDate;

    /** 班列站点 */
    @Excel(name = "班列站点")
    private String classSite;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String orderClassBh;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "线路类型：0中欧 2中亚 3中越 4中俄")
    private String lineTypeid;

    /** 班列号 */
    @Excel(name = "班列号")
    private String classNumber;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    private String classEastandwest;

    /** 上货站 */
    @Excel(name = "上货站")
    private String orderUploadsite;

    /** 下货站 */
    @Excel(name = "下货站")
    private String orderUnloadsite;

    /** 上货站唯一码 */
    @Excel(name = "上货站唯一码")
    private String orderUploadcode;

    /** 下货丫唯一码 */
    @Excel(name = "下货丫唯一码")
    private String orderUnloadcode;

    /** 贸易方式（条款ID） */
    @Excel(name = "贸易方式", readConverterExp = "条=款ID")
    private Long dictId;

    /** 条款名称 */
    @Excel(name = "条款名称")
    private String dictName;

    /** 部门 */
    @Excel(name = "部门")
    private String orderDeptname;

    /** 跟单姓名 */
    @Excel(name = "跟单姓名")
    private String orderMerchandiser;

    /** 跟单ID */
    @Excel(name = "跟单ID")
    private String orderMerchandiserId;

    /** 是否需要发票 0否 1是 */
    @Excel(name = "是否需要发票 0否 1是")
    private String orderIsticket;

    /** 订舱方ID/客户id */
    @Excel(name = "订舱方ID/客户id")
    private String clientId;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    private String clientUnit;

    /** 订舱方联系人 */
    @Excel(name = "订舱方联系人")
    private String clientContacts;

    /** 订舱方联系方式 */
    @Excel(name = "订舱方联系方式")
    private String clientTel;

    /** 订舱方邮箱 */
    @Excel(name = "订舱方邮箱")
    private String clientEmail;

    /** 订舱方地址 */
    @Excel(name = "订舱方地址")
    private String clientAddress;

    /** 报关地点 */
    @Excel(name = "报关地点")
    private String clientOrderBindingaddress;

    /** zih报关：0是 1否 */
    @Excel(name = "zih报关：0是 1否")
    private String clientOrderBindingway;

    /** 特殊要求备注 */
    @Excel(name = "特殊要求备注")
    private String clientOrderRemarks;

    /** 集装箱箱量 */
    @Excel(name = "集装箱箱量")
    private String containerBoxamount;

    /** 集装箱箱型 */
    @Excel(name = "集装箱箱型")
    private String containerType;

    /** 箱型值 */
    @Excel(name = "箱型值")
    private String containerTypeval;

    /** 还箱点 */
    @Excel(name = "还箱点")
    private String clientOrderRepaycontainer;

    /** 是否派监装员 0否 1是 */
    @Excel(name = "是否派监装员 0否 1是")
    private String shipOrderIsdispatch;

    /** 发货方id */
    @Excel(name = "发货方id")
    private String shipOrderId;

    /** 发货方名称 */
    @Excel(name = "发货方名称")
    private String shipOrederName;

    /** 发货方联系人 */
    @Excel(name = "发货方联系人")
    private String shipOrederContacts;

    /** 发货方邮箱 */
    @Excel(name = "发货方邮箱")
    private String shipOrederEmail;

    /** 发货方联系电话 */
    @Excel(name = "发货方联系电话")
    private String shipOrederPhone;

    /** 发货方地址 */
    @Excel(name = "发货方地址")
    private String shipOrederAddress;

    /** 委托ZIH提货 0是 1否  2铁路到货 */
    @Excel(name = "委托ZIH提货 0是 1否  2铁路到货")
    private String shipOrderBinningway;

    /** 提货地址 */
    @Excel(name = "提货地址")
    private String shipOrderUnloadaddress;

    /** 提货联系人 */
    @Excel(name = "提货联系人")
    private String shipOrderUnloadcontacts;

    /** 提货联系方式 */
    @Excel(name = "提货联系方式")
    private String shipOrderUnloadway;

    /** 提货联系邮箱 */
    @Excel(name = "提货联系邮箱")
    private String shipOrderUnloadwayEmail;

    /** 提货时间 */
    @Excel(name = "提货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shipOrderUnloadtime;

    /** 自送货时间 */
    @Excel(name = "自送货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shipOrderSendtime;

    /** 收货方id */
    @Excel(name = "收货方id")
    private String receiveOrderId;

    /** 收货方名称 */
    @Excel(name = "收货方名称")
    private String receiveOrderName;

    /** 收货方联系人 */
    @Excel(name = "收货方联系人")
    private String receiveOrderContacts;

    /** 到站通知提货邮箱 */
    @Excel(name = "到站通知提货邮箱")
    private String receiveOrderMail;

    /** 收货方联系电话 */
    @Excel(name = "收货方联系电话")
    private String receiveOrderPhone;

    /** 通讯地址 */
    @Excel(name = "通讯地址")
    private String receiveOrderAddress;

    /** 是否由ZIH代理清关  0否 1是 */
    @Excel(name = "是否由ZIH代理清关  0否 1是")
    private String receiveOrderIsclearance;

    /** 是否由ZIH代理送货  0否 1是 */
    @Excel(name = "是否由ZIH代理送货  0否 1是")
    private String receiveOrderIspart;

    /** 送货分拨邮箱 */
    @Excel(name = "送货分拨邮箱")
    private String receiveOrderZihemail;

    /** 分拨状态0待分拨，1已分拨 */
    @Excel(name = "分拨状态0待分拨，1已分拨")
    private String receiveOrderFbStatus;

    /** 送货地址 */
    @Excel(name = "送货地址")
    private String receiveOrderPartaddress;

    /** 在途信息接收邮箱 */
    @Excel(name = "在途信息接收邮箱")
    private String receiveOrderReceiveemail;

    /** 收货方英文 */
    @Excel(name = "收货方英文")
    private String receiveOrderEnName;

    /** 收货方联系人英文 */
    @Excel(name = "收货方联系人英文")
    private String receiveOrderEnContacts;

    /** 收货方邮箱英文 */
    @Excel(name = "收货方邮箱英文")
    private String receiveOrderEnEmail;

    /** 收货方电话英文 */
    @Excel(name = "收货方电话英文")
    private String receiveOrderEnPhone;

    /** 收货方通讯地址英文 */
    @Excel(name = "收货方通讯地址英文")
    private String receiveOrderEnAddress;

    /** 收货方俄文 */
    @Excel(name = "收货方俄文")
    private String receiveOrderEname;

    /** 收货方联系人俄文 */
    @Excel(name = "收货方联系人俄文")
    private String receiveOrderEcontacts;

    /** 收货方邮箱俄文 */
    @Excel(name = "收货方邮箱俄文")
    private String receiveOrderEemail;

    /** 收货方电话俄文 */
    @Excel(name = "收货方电话俄文")
    private String receiveOrderEphone;

    /** 收货方通讯地址俄文 */
    @Excel(name = "收货方通讯地址俄文")
    private String receiveOrderEaddress;

    /** 到站提箱公司名称（俄线） */
    @Excel(name = "到站提箱公司名称", readConverterExp = "俄=线")
    private String etxCompany;

    /** 到货提箱公司税号（俄线） */
    @Excel(name = "到货提箱公司税号", readConverterExp = "俄=线")
    private String eduty;

    /** 承担监管区费用的公司（或个人）名称(俄线) */
    @Excel(name = "承担监管区费用的公司", readConverterExp = "或=个人")
    private String etxName;

    /** 送货分拨联系人 */
    @Excel(name = "送货分拨联系人")
    private String receiveOrderZihcontacts;

    /** 送货分拨联系电话 */
    @Excel(name = "送货分拨联系电话")
    private String receiveOrderZihtel;

    /** 是否可堆叠（1是0否2仅可自叠） */
    @Excel(name = "是否可堆叠", readConverterExp = "1=是0否2仅可自叠")
    private String goodsCannotpileup;

    /** 是否易碎（1是0否） */
    @Excel(name = "是否易碎", readConverterExp = "1=是0否")
    private String goodsFragile;

    /** 单件超长超重（1是0否） */
    @Excel(name = "单件超长超重", readConverterExp = "1=是0否")
    private String goodsGeneral;

    /** 0未审核 1已审核通过
2已审核未通过 3已取消的委托
4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过 2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    private String isexamline;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 托书操作创建时间 */
    @Excel(name = "托书操作创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 托书操作创建人ID */
    @Excel(name = "托书操作创建人ID")
    private String createuserid;

    /** 托书操作创建人名称 */
    @Excel(name = "托书操作创建人名称")
    private String createusername;

    /** 提货费用 */
    @Excel(name = "提货费用")
    private String shipThCost;

    /** 报关费用 */
    @Excel(name = "报关费用")
    private String clientBgCost;

    /** 收货还箱地 */
    @Excel(name = "收货还箱地")
    private String receiveHxAddress;

    /** 箱管费 */
    @Excel(name = "箱管费")
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
    private String receiveQgCost;

    /** 监装费用 */
    @Excel(name = "监装费用")
    private String shipJzCost;

    /** 送货费用 */
    @Excel(name = "送货费用")
    private String receiveShCost;

    /** 站到站运费 */
    @Excel(name = "站到站运费")
    private String sitecost;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    @Excel(name = "客户推荐人id")
    private String clientTjrId;

    /** 回程发货提箱地 */
    @Excel(name = "回程发货提箱地")
    private String shipFhSite;

    /** 去程发货提箱地 */
    @Excel(name = "去程发货提箱地")
    private String shipHyd;

    /** 到货城市，就近还空箱 */
    @Excel(name = "到货城市，就近还空箱")
    private String receiveHyd;

    /** 提货方式（整箱到车站，散货到堆场）针对整柜 */
    @Excel(name = "提货方式", readConverterExp = "整=箱到车站，散货到堆场")
    private String shipThType;

    /** 委托ZIH提货（0是整箱到车站，1是散货到堆场）针对整柜 */
    @Excel(name = "委托ZIH提货", readConverterExp = "0=是整箱到车站，1是散货到堆场")
    private String shipThTypeId;

    /** 发货方自送货方式 0散货到堆场 1整箱到车站 */
    @Excel(name = "发货方自送货方式 0散货到堆场 1整箱到车站")
    private String shipZsType;

    /** 发货方自送货ID */
    @Excel(name = "发货方自送货ID")
    private String shipZsTypeId;

    /** 语言（0是中文订单1是英文） */
    @Excel(name = "语言", readConverterExp = "0=是中文订单1是英文")
    private String yuyan;

    /** 客户每次托书提交时间 */
    @Excel(name = "客户每次托书提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tjTime;

    /** 客户首次提交托书时间 */
    @Excel(name = "客户首次提交托书时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tjFTime;

    /** 重箱托书编号 */
    @Excel(name = "重箱托书编号")
    private String zxNumber;

    /** 1有修改记录0没有修改记录 */
    @Excel(name = "1有修改记录0没有修改记录")
    private String isupdate;

    /** 0手机下单 */
    @Excel(name = "0手机下单")
    private String isphone;

    /** 提货费用币种（0是美金1是人民币） */
    @Excel(name = "提货费用币种", readConverterExp = "0=是美金1是人民币")
    private String zxThcostCurrency;

    /** 提货费报价编号 */
    @Excel(name = "提货费报价编号")
    private String shipThCostNo;

    /** 业务编码 */
    @Excel(name = "业务编码")
    private String ywNumber;

    /** 是否上车（运综货物状态） */
    @Excel(name = "是否上车", readConverterExp = "运=综货物状态")
    private String isgetin;

    /** 每日转待审核次数（每日两次） */
    @Excel(name = "每日转待审核次数", readConverterExp = "每=日两次")
    private Long turncount;

    /** 3个月内客户转待审核次数和 */
    @Excel(name = "3个月内客户转待审核次数和")
    private Long totalturncount;

    /** 3个月内客户转待审核次数平均数 */
    @Excel(name = "3个月内客户转待审核次数平均数")
    private Long totalturncountavg;

    /** 实际班列日期 */
    @Excel(name = "实际班列日期")
    private String actualClassdate;

    /** 业务审核费用状态：1审核成功，默认0 */
    @Excel(name = "业务审核费用状态：1审核成功，默认0")
    private Long ywFeedback;

    /** 去程提货地，回程送货地详细地址（去回程） */
    @Excel(name = "去程提货地，回程送货地详细地址", readConverterExp = "去=回程")
    private String detailedAddress;

    /** 是否可提前班列（0是1否） */
    @Excel(name = "是否可提前班列", readConverterExp = "0=是1否")
    private Long putoffClass;

    /** 费用确认单确认状态0未确认，1已确认 */
    @Excel(name = "费用确认单确认状态0未确认，1已确认")
    private String costVerify;

    /** 是否有改单 */
    @Excel(name = "是否有改单")
    private String dcGaidanState;

    /** 送货报价编号 */
    @Excel(name = "送货报价编号")
    private String receiveShCostId;

    /** 收货方邮箱 */
    @Excel(name = "收货方邮箱")
    private String receiveOrderEmail;

    /** 箱管费币种 */
    @Excel(name = "箱管费币种")
    private String xgCostcurrency;

    /** 清关费币种 */
    @Excel(name = "清关费币种")
    private String qgCostcurrency;

    /** 送货费币种 */
    @Excel(name = "送货费币种")
    private String shCostcurrency;

    /** 铁路运费币种 */
    @Excel(name = "铁路运费币种")
    private String sitecostCurrency;

    /** 审核操作时间 */
    @Excel(name = "审核操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date exameTime;

    /** 跟单船级社证书上传状态 */
    @Excel(name = "跟单船级社证书上传状态")
    private String gdBoat;

    /** 跟单船级社证书url */
    @Excel(name = "跟单船级社证书url")
    private String gdBoaturl;

    /** 跟单船级社证书备注 */
    @Excel(name = "跟单船级社证书备注")
    private String gdCjsremark;

    /** 跟单部押金保函URL */
    @Excel(name = "跟单部押金保函URL")
    private String gdVoucherurl;

    /** 跟单押金保函上传状态0是已上传 */
    @Excel(name = "跟单押金保函上传状态0是已上传")
    private String gdVoucher;

    /** 运综状态 */
    @Excel(name = "运综状态")
    private String trainState;

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setStationid(String stationid) 
    {
        this.stationid = stationid;
    }

    public String getStationid() 
    {
        return stationid;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setBookingService(String bookingService) 
    {
        this.bookingService = bookingService;
    }

    public String getBookingService() 
    {
        return bookingService;
    }
    public void setOrderAuditBelongto(String orderAuditBelongto) 
    {
        this.orderAuditBelongto = orderAuditBelongto;
    }

    public String getOrderAuditBelongto() 
    {
        return orderAuditBelongto;
    }
    public void setOrderAuditCreatedate(Date orderAuditCreatedate) 
    {
        this.orderAuditCreatedate = orderAuditCreatedate;
    }

    public Date getOrderAuditCreatedate() 
    {
        return orderAuditCreatedate;
    }
    public void setOrderAuditUserid(String orderAuditUserid) 
    {
        this.orderAuditUserid = orderAuditUserid;
    }

    public String getOrderAuditUserid() 
    {
        return orderAuditUserid;
    }
    public void setOrderAuditUsername(String orderAuditUsername) 
    {
        this.orderAuditUsername = orderAuditUsername;
    }

    public String getOrderAuditUsername() 
    {
        return orderAuditUsername;
    }
    public void setClassId(String classId) 
    {
        this.classId = classId;
    }

    public String getClassId() 
    {
        return classId;
    }
    public void setClassDate(Date classDate) 
    {
        this.classDate = classDate;
    }

    public Date getClassDate() 
    {
        return classDate;
    }
    public void setClassSite(String classSite) 
    {
        this.classSite = classSite;
    }

    public String getClassSite() 
    {
        return classSite;
    }
    public void setOrderClassBh(String orderClassBh) 
    {
        this.orderClassBh = orderClassBh;
    }

    public String getOrderClassBh() 
    {
        return orderClassBh;
    }
    public void setLineTypeid(String lineTypeid) 
    {
        this.lineTypeid = lineTypeid;
    }

    public String getLineTypeid() 
    {
        return lineTypeid;
    }
    public void setClassNumber(String classNumber) 
    {
        this.classNumber = classNumber;
    }

    public String getClassNumber() 
    {
        return classNumber;
    }
    public void setClassEastandwest(String classEastandwest) 
    {
        this.classEastandwest = classEastandwest;
    }

    public String getClassEastandwest() 
    {
        return classEastandwest;
    }
    public void setOrderUploadsite(String orderUploadsite) 
    {
        this.orderUploadsite = orderUploadsite;
    }

    public String getOrderUploadsite() 
    {
        return orderUploadsite;
    }
    public void setOrderUnloadsite(String orderUnloadsite) 
    {
        this.orderUnloadsite = orderUnloadsite;
    }

    public String getOrderUnloadsite() 
    {
        return orderUnloadsite;
    }
    public void setOrderUploadcode(String orderUploadcode) 
    {
        this.orderUploadcode = orderUploadcode;
    }

    public String getOrderUploadcode() 
    {
        return orderUploadcode;
    }
    public void setOrderUnloadcode(String orderUnloadcode) 
    {
        this.orderUnloadcode = orderUnloadcode;
    }

    public String getOrderUnloadcode() 
    {
        return orderUnloadcode;
    }
    public void setDictId(Long dictId) 
    {
        this.dictId = dictId;
    }

    public Long getDictId() 
    {
        return dictId;
    }
    public void setDictName(String dictName) 
    {
        this.dictName = dictName;
    }

    public String getDictName() 
    {
        return dictName;
    }
    public void setOrderDeptname(String orderDeptname) 
    {
        this.orderDeptname = orderDeptname;
    }

    public String getOrderDeptname() 
    {
        return orderDeptname;
    }
    public void setOrderMerchandiser(String orderMerchandiser) 
    {
        this.orderMerchandiser = orderMerchandiser;
    }

    public String getOrderMerchandiser() 
    {
        return orderMerchandiser;
    }
    public void setOrderMerchandiserId(String orderMerchandiserId) 
    {
        this.orderMerchandiserId = orderMerchandiserId;
    }

    public String getOrderMerchandiserId() 
    {
        return orderMerchandiserId;
    }
    public void setOrderIsticket(String orderIsticket) 
    {
        this.orderIsticket = orderIsticket;
    }

    public String getOrderIsticket() 
    {
        return orderIsticket;
    }
    public void setClientId(String clientId) 
    {
        this.clientId = clientId;
    }

    public String getClientId() 
    {
        return clientId;
    }
    public void setClientUnit(String clientUnit) 
    {
        this.clientUnit = clientUnit;
    }

    public String getClientUnit() 
    {
        return clientUnit;
    }
    public void setClientContacts(String clientContacts) 
    {
        this.clientContacts = clientContacts;
    }

    public String getClientContacts() 
    {
        return clientContacts;
    }
    public void setClientTel(String clientTel) 
    {
        this.clientTel = clientTel;
    }

    public String getClientTel() 
    {
        return clientTel;
    }
    public void setClientEmail(String clientEmail) 
    {
        this.clientEmail = clientEmail;
    }

    public String getClientEmail() 
    {
        return clientEmail;
    }
    public void setClientAddress(String clientAddress) 
    {
        this.clientAddress = clientAddress;
    }

    public String getClientAddress() 
    {
        return clientAddress;
    }
    public void setClientOrderBindingaddress(String clientOrderBindingaddress) 
    {
        this.clientOrderBindingaddress = clientOrderBindingaddress;
    }

    public String getClientOrderBindingaddress() 
    {
        return clientOrderBindingaddress;
    }
    public void setClientOrderBindingway(String clientOrderBindingway) 
    {
        this.clientOrderBindingway = clientOrderBindingway;
    }

    public String getClientOrderBindingway() 
    {
        return clientOrderBindingway;
    }
    public void setClientOrderRemarks(String clientOrderRemarks) 
    {
        this.clientOrderRemarks = clientOrderRemarks;
    }

    public String getClientOrderRemarks() 
    {
        return clientOrderRemarks;
    }
    public void setContainerBoxamount(String containerBoxamount) 
    {
        this.containerBoxamount = containerBoxamount;
    }

    public String getContainerBoxamount() 
    {
        return containerBoxamount;
    }
    public void setContainerType(String containerType) 
    {
        this.containerType = containerType;
    }

    public String getContainerType() 
    {
        return containerType;
    }
    public void setContainerTypeval(String containerTypeval) 
    {
        this.containerTypeval = containerTypeval;
    }

    public String getContainerTypeval() 
    {
        return containerTypeval;
    }
    public void setClientOrderRepaycontainer(String clientOrderRepaycontainer) 
    {
        this.clientOrderRepaycontainer = clientOrderRepaycontainer;
    }

    public String getClientOrderRepaycontainer() 
    {
        return clientOrderRepaycontainer;
    }
    public void setShipOrderIsdispatch(String shipOrderIsdispatch) 
    {
        this.shipOrderIsdispatch = shipOrderIsdispatch;
    }

    public String getShipOrderIsdispatch() 
    {
        return shipOrderIsdispatch;
    }
    public void setShipOrderId(String shipOrderId) 
    {
        this.shipOrderId = shipOrderId;
    }

    public String getShipOrderId() 
    {
        return shipOrderId;
    }
    public void setShipOrederName(String shipOrederName) 
    {
        this.shipOrederName = shipOrederName;
    }

    public String getShipOrederName() 
    {
        return shipOrederName;
    }
    public void setShipOrederContacts(String shipOrederContacts) 
    {
        this.shipOrederContacts = shipOrederContacts;
    }

    public String getShipOrederContacts() 
    {
        return shipOrederContacts;
    }
    public void setShipOrederEmail(String shipOrederEmail) 
    {
        this.shipOrederEmail = shipOrederEmail;
    }

    public String getShipOrederEmail() 
    {
        return shipOrederEmail;
    }
    public void setShipOrederPhone(String shipOrederPhone) 
    {
        this.shipOrederPhone = shipOrederPhone;
    }

    public String getShipOrederPhone() 
    {
        return shipOrederPhone;
    }
    public void setShipOrederAddress(String shipOrederAddress) 
    {
        this.shipOrederAddress = shipOrederAddress;
    }

    public String getShipOrederAddress() 
    {
        return shipOrederAddress;
    }
    public void setShipOrderBinningway(String shipOrderBinningway) 
    {
        this.shipOrderBinningway = shipOrderBinningway;
    }

    public String getShipOrderBinningway() 
    {
        return shipOrderBinningway;
    }
    public void setShipOrderUnloadaddress(String shipOrderUnloadaddress) 
    {
        this.shipOrderUnloadaddress = shipOrderUnloadaddress;
    }

    public String getShipOrderUnloadaddress() 
    {
        return shipOrderUnloadaddress;
    }
    public void setShipOrderUnloadcontacts(String shipOrderUnloadcontacts) 
    {
        this.shipOrderUnloadcontacts = shipOrderUnloadcontacts;
    }

    public String getShipOrderUnloadcontacts() 
    {
        return shipOrderUnloadcontacts;
    }
    public void setShipOrderUnloadway(String shipOrderUnloadway) 
    {
        this.shipOrderUnloadway = shipOrderUnloadway;
    }

    public String getShipOrderUnloadway() 
    {
        return shipOrderUnloadway;
    }
    public void setShipOrderUnloadwayEmail(String shipOrderUnloadwayEmail) 
    {
        this.shipOrderUnloadwayEmail = shipOrderUnloadwayEmail;
    }

    public String getShipOrderUnloadwayEmail() 
    {
        return shipOrderUnloadwayEmail;
    }
    public void setShipOrderUnloadtime(Date shipOrderUnloadtime) 
    {
        this.shipOrderUnloadtime = shipOrderUnloadtime;
    }

    public Date getShipOrderUnloadtime() 
    {
        return shipOrderUnloadtime;
    }
    public void setShipOrderSendtime(Date shipOrderSendtime) 
    {
        this.shipOrderSendtime = shipOrderSendtime;
    }

    public Date getShipOrderSendtime() 
    {
        return shipOrderSendtime;
    }
    public void setReceiveOrderId(String receiveOrderId) 
    {
        this.receiveOrderId = receiveOrderId;
    }

    public String getReceiveOrderId() 
    {
        return receiveOrderId;
    }
    public void setReceiveOrderName(String receiveOrderName) 
    {
        this.receiveOrderName = receiveOrderName;
    }

    public String getReceiveOrderName() 
    {
        return receiveOrderName;
    }
    public void setReceiveOrderContacts(String receiveOrderContacts) 
    {
        this.receiveOrderContacts = receiveOrderContacts;
    }

    public String getReceiveOrderContacts() 
    {
        return receiveOrderContacts;
    }
    public void setReceiveOrderMail(String receiveOrderMail) 
    {
        this.receiveOrderMail = receiveOrderMail;
    }

    public String getReceiveOrderMail() 
    {
        return receiveOrderMail;
    }
    public void setReceiveOrderPhone(String receiveOrderPhone) 
    {
        this.receiveOrderPhone = receiveOrderPhone;
    }

    public String getReceiveOrderPhone() 
    {
        return receiveOrderPhone;
    }
    public void setReceiveOrderAddress(String receiveOrderAddress) 
    {
        this.receiveOrderAddress = receiveOrderAddress;
    }

    public String getReceiveOrderAddress() 
    {
        return receiveOrderAddress;
    }
    public void setReceiveOrderIsclearance(String receiveOrderIsclearance) 
    {
        this.receiveOrderIsclearance = receiveOrderIsclearance;
    }

    public String getReceiveOrderIsclearance() 
    {
        return receiveOrderIsclearance;
    }
    public void setReceiveOrderIspart(String receiveOrderIspart) 
    {
        this.receiveOrderIspart = receiveOrderIspart;
    }

    public String getReceiveOrderIspart() 
    {
        return receiveOrderIspart;
    }
    public void setReceiveOrderZihemail(String receiveOrderZihemail) 
    {
        this.receiveOrderZihemail = receiveOrderZihemail;
    }

    public String getReceiveOrderZihemail() 
    {
        return receiveOrderZihemail;
    }
    public void setReceiveOrderFbStatus(String receiveOrderFbStatus) 
    {
        this.receiveOrderFbStatus = receiveOrderFbStatus;
    }

    public String getReceiveOrderFbStatus() 
    {
        return receiveOrderFbStatus;
    }
    public void setReceiveOrderPartaddress(String receiveOrderPartaddress) 
    {
        this.receiveOrderPartaddress = receiveOrderPartaddress;
    }

    public String getReceiveOrderPartaddress() 
    {
        return receiveOrderPartaddress;
    }
    public void setReceiveOrderReceiveemail(String receiveOrderReceiveemail) 
    {
        this.receiveOrderReceiveemail = receiveOrderReceiveemail;
    }

    public String getReceiveOrderReceiveemail() 
    {
        return receiveOrderReceiveemail;
    }
    public void setReceiveOrderEnName(String receiveOrderEnName) 
    {
        this.receiveOrderEnName = receiveOrderEnName;
    }

    public String getReceiveOrderEnName() 
    {
        return receiveOrderEnName;
    }
    public void setReceiveOrderEnContacts(String receiveOrderEnContacts) 
    {
        this.receiveOrderEnContacts = receiveOrderEnContacts;
    }

    public String getReceiveOrderEnContacts() 
    {
        return receiveOrderEnContacts;
    }
    public void setReceiveOrderEnEmail(String receiveOrderEnEmail) 
    {
        this.receiveOrderEnEmail = receiveOrderEnEmail;
    }

    public String getReceiveOrderEnEmail() 
    {
        return receiveOrderEnEmail;
    }
    public void setReceiveOrderEnPhone(String receiveOrderEnPhone) 
    {
        this.receiveOrderEnPhone = receiveOrderEnPhone;
    }

    public String getReceiveOrderEnPhone() 
    {
        return receiveOrderEnPhone;
    }
    public void setReceiveOrderEnAddress(String receiveOrderEnAddress) 
    {
        this.receiveOrderEnAddress = receiveOrderEnAddress;
    }

    public String getReceiveOrderEnAddress() 
    {
        return receiveOrderEnAddress;
    }
    public void setReceiveOrderEname(String receiveOrderEname) 
    {
        this.receiveOrderEname = receiveOrderEname;
    }

    public String getReceiveOrderEname() 
    {
        return receiveOrderEname;
    }
    public void setReceiveOrderEcontacts(String receiveOrderEcontacts) 
    {
        this.receiveOrderEcontacts = receiveOrderEcontacts;
    }

    public String getReceiveOrderEcontacts() 
    {
        return receiveOrderEcontacts;
    }
    public void setReceiveOrderEemail(String receiveOrderEemail) 
    {
        this.receiveOrderEemail = receiveOrderEemail;
    }

    public String getReceiveOrderEemail() 
    {
        return receiveOrderEemail;
    }
    public void setReceiveOrderEphone(String receiveOrderEphone) 
    {
        this.receiveOrderEphone = receiveOrderEphone;
    }

    public String getReceiveOrderEphone() 
    {
        return receiveOrderEphone;
    }
    public void setReceiveOrderEaddress(String receiveOrderEaddress) 
    {
        this.receiveOrderEaddress = receiveOrderEaddress;
    }

    public String getReceiveOrderEaddress() 
    {
        return receiveOrderEaddress;
    }
    public void setEtxCompany(String etxCompany) 
    {
        this.etxCompany = etxCompany;
    }

    public String getEtxCompany() 
    {
        return etxCompany;
    }
    public void setEduty(String eduty) 
    {
        this.eduty = eduty;
    }

    public String getEduty() 
    {
        return eduty;
    }
    public void setEtxName(String etxName) 
    {
        this.etxName = etxName;
    }

    public String getEtxName() 
    {
        return etxName;
    }
    public void setReceiveOrderZihcontacts(String receiveOrderZihcontacts) 
    {
        this.receiveOrderZihcontacts = receiveOrderZihcontacts;
    }

    public String getReceiveOrderZihcontacts() 
    {
        return receiveOrderZihcontacts;
    }
    public void setReceiveOrderZihtel(String receiveOrderZihtel) 
    {
        this.receiveOrderZihtel = receiveOrderZihtel;
    }

    public String getReceiveOrderZihtel() 
    {
        return receiveOrderZihtel;
    }
    public void setGoodsCannotpileup(String goodsCannotpileup) 
    {
        this.goodsCannotpileup = goodsCannotpileup;
    }

    public String getGoodsCannotpileup() 
    {
        return goodsCannotpileup;
    }
    public void setGoodsFragile(String goodsFragile) 
    {
        this.goodsFragile = goodsFragile;
    }

    public String getGoodsFragile() 
    {
        return goodsFragile;
    }
    public void setGoodsGeneral(String goodsGeneral) 
    {
        this.goodsGeneral = goodsGeneral;
    }

    public String getGoodsGeneral() 
    {
        return goodsGeneral;
    }
    public void setIsexamline(String isexamline) 
    {
        this.isexamline = isexamline;
    }

    public String getIsexamline() 
    {
        return isexamline;
    }
    public void setIsconsolidation(String isconsolidation) 
    {
        this.isconsolidation = isconsolidation;
    }

    public String getIsconsolidation() 
    {
        return isconsolidation;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setCreateuserid(String createuserid) 
    {
        this.createuserid = createuserid;
    }

    public String getCreateuserid() 
    {
        return createuserid;
    }
    public void setCreateusername(String createusername) 
    {
        this.createusername = createusername;
    }

    public String getCreateusername() 
    {
        return createusername;
    }
    public void setShipThCost(String shipThCost) 
    {
        this.shipThCost = shipThCost;
    }

    public String getShipThCost() 
    {
        return shipThCost;
    }
    public void setClientBgCost(String clientBgCost) 
    {
        this.clientBgCost = clientBgCost;
    }

    public String getClientBgCost() 
    {
        return clientBgCost;
    }
    public void setReceiveHxAddress(String receiveHxAddress) 
    {
        this.receiveHxAddress = receiveHxAddress;
    }

    public String getReceiveHxAddress() 
    {
        return receiveHxAddress;
    }

    public void setReceiveXgCost(String receiveXgCost) 
    {
        this.receiveXgCost = receiveXgCost;
    }
    public String getReceiveXgCost() 
    {
        return receiveXgCost;
    }

    public void setPickUpBoxFee(String pickUpBoxFee)
    {
        this.pickUpBoxFee = pickUpBoxFee;
    }
    public String getPickUpBoxFee()
    {
        return pickUpBoxFee;
    }

    public void setReturnBoxFee(String returnBoxFee)
    {
        this.returnBoxFee = returnBoxFee;
    }
    public String getReturnBoxFee()
    {
        return returnBoxFee;
    }

    public void setReceiveQgCost(String receiveQgCost) 
    {
        this.receiveQgCost = receiveQgCost;
    }

    public String getReceiveQgCost() 
    {
        return receiveQgCost;
    }
    public void setShipJzCost(String shipJzCost) 
    {
        this.shipJzCost = shipJzCost;
    }

    public String getShipJzCost() 
    {
        return shipJzCost;
    }
    public void setReceiveShCost(String receiveShCost) 
    {
        this.receiveShCost = receiveShCost;
    }

    public String getReceiveShCost() 
    {
        return receiveShCost;
    }
    public void setSitecost(String sitecost) 
    {
        this.sitecost = sitecost;
    }

    public String getSitecost() 
    {
        return sitecost;
    }
    public void setClientTjr(String clientTjr) 
    {
        this.clientTjr = clientTjr;
    }

    public String getClientTjr() 
    {
        return clientTjr;
    }
    public void setClientTjrId(String clientTjrId) 
    {
        this.clientTjrId = clientTjrId;
    }

    public String getClientTjrId() 
    {
        return clientTjrId;
    }
    public void setShipFhSite(String shipFhSite) 
    {
        this.shipFhSite = shipFhSite;
    }

    public String getShipFhSite() 
    {
        return shipFhSite;
    }
    public void setShipHyd(String shipHyd) 
    {
        this.shipHyd = shipHyd;
    }

    public String getShipHyd() 
    {
        return shipHyd;
    }
    public void setReceiveHyd(String receiveHyd) 
    {
        this.receiveHyd = receiveHyd;
    }

    public String getReceiveHyd() 
    {
        return receiveHyd;
    }
    public void setShipThType(String shipThType) 
    {
        this.shipThType = shipThType;
    }

    public String getShipThType() 
    {
        return shipThType;
    }
    public void setShipThTypeId(String shipThTypeId) 
    {
        this.shipThTypeId = shipThTypeId;
    }

    public String getShipThTypeId() 
    {
        return shipThTypeId;
    }
    public void setShipZsType(String shipZsType) 
    {
        this.shipZsType = shipZsType;
    }

    public String getShipZsType() 
    {
        return shipZsType;
    }
    public void setShipZsTypeId(String shipZsTypeId) 
    {
        this.shipZsTypeId = shipZsTypeId;
    }

    public String getShipZsTypeId() 
    {
        return shipZsTypeId;
    }
    public void setYuyan(String yuyan) 
    {
        this.yuyan = yuyan;
    }

    public String getYuyan() 
    {
        return yuyan;
    }
    public void setTjTime(Date tjTime) 
    {
        this.tjTime = tjTime;
    }

    public Date getTjTime() 
    {
        return tjTime;
    }
    public void setTjFTime(Date tjFTime) 
    {
        this.tjFTime = tjFTime;
    }

    public Date getTjFTime() 
    {
        return tjFTime;
    }
    public void setZxNumber(String zxNumber) 
    {
        this.zxNumber = zxNumber;
    }

    public String getZxNumber() 
    {
        return zxNumber;
    }
    public void setIsupdate(String isupdate) 
    {
        this.isupdate = isupdate;
    }

    public String getIsupdate() 
    {
        return isupdate;
    }
    public void setIsphone(String isphone) 
    {
        this.isphone = isphone;
    }

    public String getIsphone() 
    {
        return isphone;
    }
    public void setZxThcostCurrency(String zxThcostCurrency) 
    {
        this.zxThcostCurrency = zxThcostCurrency;
    }

    public String getZxThcostCurrency() 
    {
        return zxThcostCurrency;
    }
    public void setShipThCostNo(String shipThCostNo) 
    {
        this.shipThCostNo = shipThCostNo;
    }

    public String getShipThCostNo() 
    {
        return shipThCostNo;
    }
    public void setYwNumber(String ywNumber) 
    {
        this.ywNumber = ywNumber;
    }

    public String getYwNumber() 
    {
        return ywNumber;
    }
    public void setIsgetin(String isgetin) 
    {
        this.isgetin = isgetin;
    }

    public String getIsgetin() 
    {
        return isgetin;
    }
    public void setTurncount(Long turncount) 
    {
        this.turncount = turncount;
    }

    public Long getTurncount() 
    {
        return turncount;
    }
    public void setTotalturncount(Long totalturncount) 
    {
        this.totalturncount = totalturncount;
    }

    public Long getTotalturncount() 
    {
        return totalturncount;
    }
    public void setTotalturncountavg(Long totalturncountavg) 
    {
        this.totalturncountavg = totalturncountavg;
    }

    public Long getTotalturncountavg() 
    {
        return totalturncountavg;
    }
    public void setActualClassdate(String actualClassdate) 
    {
        this.actualClassdate = actualClassdate;
    }

    public String getActualClassdate() 
    {
        return actualClassdate;
    }
    public void setYwFeedback(Long ywFeedback) 
    {
        this.ywFeedback = ywFeedback;
    }

    public Long getYwFeedback() 
    {
        return ywFeedback;
    }
    public void setDetailedAddress(String detailedAddress) 
    {
        this.detailedAddress = detailedAddress;
    }

    public String getDetailedAddress() 
    {
        return detailedAddress;
    }
    public void setPutoffClass(Long putoffClass) 
    {
        this.putoffClass = putoffClass;
    }

    public Long getPutoffClass() 
    {
        return putoffClass;
    }
    public void setCostVerify(String costVerify) 
    {
        this.costVerify = costVerify;
    }

    public String getCostVerify() 
    {
        return costVerify;
    }
    public void setDcGaidanState(String dcGaidanState) 
    {
        this.dcGaidanState = dcGaidanState;
    }

    public String getDcGaidanState() 
    {
        return dcGaidanState;
    }
    public void setReceiveShCostId(String receiveShCostId) 
    {
        this.receiveShCostId = receiveShCostId;
    }

    public String getReceiveShCostId() 
    {
        return receiveShCostId;
    }
    public void setReceiveOrderEmail(String receiveOrderEmail) 
    {
        this.receiveOrderEmail = receiveOrderEmail;
    }

    public String getReceiveOrderEmail() 
    {
        return receiveOrderEmail;
    }
    public void setXgCostcurrency(String xgCostcurrency) 
    {
        this.xgCostcurrency = xgCostcurrency;
    }

    public String getXgCostcurrency() 
    {
        return xgCostcurrency;
    }
    public void setQgCostcurrency(String qgCostcurrency) 
    {
        this.qgCostcurrency = qgCostcurrency;
    }

    public String getQgCostcurrency() 
    {
        return qgCostcurrency;
    }
    public void setShCostcurrency(String shCostcurrency) 
    {
        this.shCostcurrency = shCostcurrency;
    }

    public String getShCostcurrency() 
    {
        return shCostcurrency;
    }
    public void setSitecostCurrency(String sitecostCurrency) 
    {
        this.sitecostCurrency = sitecostCurrency;
    }

    public String getSitecostCurrency() 
    {
        return sitecostCurrency;
    }
    public void setExameTime(Date exameTime) 
    {
        this.exameTime = exameTime;
    }

    public Date getExameTime() 
    {
        return exameTime;
    }
    public void setGdBoat(String gdBoat) 
    {
        this.gdBoat = gdBoat;
    }

    public String getGdBoat() 
    {
        return gdBoat;
    }
    public void setGdBoaturl(String gdBoaturl) 
    {
        this.gdBoaturl = gdBoaturl;
    }

    public String getGdBoaturl() 
    {
        return gdBoaturl;
    }
    public void setGdCjsremark(String gdCjsremark) 
    {
        this.gdCjsremark = gdCjsremark;
    }

    public String getGdCjsremark() 
    {
        return gdCjsremark;
    }
    public void setGdVoucherurl(String gdVoucherurl) 
    {
        this.gdVoucherurl = gdVoucherurl;
    }

    public String getGdVoucherurl() 
    {
        return gdVoucherurl;
    }
    public void setGdVoucher(String gdVoucher) 
    {
        this.gdVoucher = gdVoucher;
    }

    public String getGdVoucher() 
    {
        return gdVoucher;
    }
    public void setTrainState(String trainState) 
    {
        this.trainState = trainState;
    }

    public String getTrainState() 
    {
        return trainState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("stationid", getStationid())
            .append("orderNumber", getOrderNumber())
            .append("bookingService", getBookingService())
            .append("orderAuditBelongto", getOrderAuditBelongto())
            .append("orderAuditCreatedate", getOrderAuditCreatedate())
            .append("orderAuditUserid", getOrderAuditUserid())
            .append("orderAuditUsername", getOrderAuditUsername())
            .append("classId", getClassId())
            .append("classDate", getClassDate())
            .append("classSite", getClassSite())
            .append("orderClassBh", getOrderClassBh())
            .append("lineTypeid", getLineTypeid())
            .append("classNumber", getClassNumber())
            .append("classEastandwest", getClassEastandwest())
            .append("orderUploadsite", getOrderUploadsite())
            .append("orderUnloadsite", getOrderUnloadsite())
            .append("orderUploadcode", getOrderUploadcode())
            .append("orderUnloadcode", getOrderUnloadcode())
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("orderDeptname", getOrderDeptname())
            .append("orderMerchandiser", getOrderMerchandiser())
            .append("orderMerchandiserId", getOrderMerchandiserId())
            .append("orderIsticket", getOrderIsticket())
            .append("clientId", getClientId())
            .append("clientUnit", getClientUnit())
            .append("clientContacts", getClientContacts())
            .append("clientTel", getClientTel())
            .append("clientEmail", getClientEmail())
            .append("clientAddress", getClientAddress())
            .append("clientOrderBindingaddress", getClientOrderBindingaddress())
            .append("clientOrderBindingway", getClientOrderBindingway())
            .append("clientOrderRemarks", getClientOrderRemarks())
            .append("containerBoxamount", getContainerBoxamount())
            .append("containerType", getContainerType())
            .append("containerTypeval", getContainerTypeval())
            .append("clientOrderRepaycontainer", getClientOrderRepaycontainer())
            .append("shipOrderIsdispatch", getShipOrderIsdispatch())
            .append("shipOrderId", getShipOrderId())
            .append("shipOrederName", getShipOrederName())
            .append("shipOrederContacts", getShipOrederContacts())
            .append("shipOrederEmail", getShipOrederEmail())
            .append("shipOrederPhone", getShipOrederPhone())
            .append("shipOrederAddress", getShipOrederAddress())
            .append("shipOrderBinningway", getShipOrderBinningway())
            .append("shipOrderUnloadaddress", getShipOrderUnloadaddress())
            .append("shipOrderUnloadcontacts", getShipOrderUnloadcontacts())
            .append("shipOrderUnloadway", getShipOrderUnloadway())
            .append("shipOrderUnloadwayEmail", getShipOrderUnloadwayEmail())
            .append("shipOrderUnloadtime", getShipOrderUnloadtime())
            .append("shipOrderSendtime", getShipOrderSendtime())
            .append("receiveOrderId", getReceiveOrderId())
            .append("receiveOrderName", getReceiveOrderName())
            .append("receiveOrderContacts", getReceiveOrderContacts())
            .append("receiveOrderMail", getReceiveOrderMail())
            .append("receiveOrderPhone", getReceiveOrderPhone())
            .append("receiveOrderAddress", getReceiveOrderAddress())
            .append("receiveOrderIsclearance", getReceiveOrderIsclearance())
            .append("receiveOrderIspart", getReceiveOrderIspart())
            .append("receiveOrderZihemail", getReceiveOrderZihemail())
            .append("receiveOrderFbStatus", getReceiveOrderFbStatus())
            .append("receiveOrderPartaddress", getReceiveOrderPartaddress())
            .append("receiveOrderReceiveemail", getReceiveOrderReceiveemail())
            .append("receiveOrderEnName", getReceiveOrderEnName())
            .append("receiveOrderEnContacts", getReceiveOrderEnContacts())
            .append("receiveOrderEnEmail", getReceiveOrderEnEmail())
            .append("receiveOrderEnPhone", getReceiveOrderEnPhone())
            .append("receiveOrderEnAddress", getReceiveOrderEnAddress())
            .append("receiveOrderEname", getReceiveOrderEname())
            .append("receiveOrderEcontacts", getReceiveOrderEcontacts())
            .append("receiveOrderEemail", getReceiveOrderEemail())
            .append("receiveOrderEphone", getReceiveOrderEphone())
            .append("receiveOrderEaddress", getReceiveOrderEaddress())
            .append("etxCompany", getEtxCompany())
            .append("eduty", getEduty())
            .append("etxName", getEtxName())
            .append("receiveOrderZihcontacts", getReceiveOrderZihcontacts())
            .append("receiveOrderZihtel", getReceiveOrderZihtel())
            .append("goodsCannotpileup", getGoodsCannotpileup())
            .append("goodsFragile", getGoodsFragile())
            .append("goodsGeneral", getGoodsGeneral())
            .append("isexamline", getIsexamline())
            .append("isconsolidation", getIsconsolidation())
            .append("createdate", getCreatedate())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("shipThCost", getShipThCost())
            .append("clientBgCost", getClientBgCost())
            .append("receiveHxAddress", getReceiveHxAddress())
            .append("receiveXgCost", getReceiveXgCost())
            .append("pickUpBoxFee", getPickUpBoxFee())
            .append("returnBoxFee", getReturnBoxFee())
            .append("receiveQgCost", getReceiveQgCost())
            .append("shipJzCost", getShipJzCost())
            .append("receiveShCost", getReceiveShCost())
            .append("sitecost", getSitecost())
            .append("clientTjr", getClientTjr())
            .append("clientTjrId", getClientTjrId())
            .append("shipFhSite", getShipFhSite())
            .append("shipHyd", getShipHyd())
            .append("receiveHyd", getReceiveHyd())
            .append("shipThType", getShipThType())
            .append("shipThTypeId", getShipThTypeId())
            .append("shipZsType", getShipZsType())
            .append("shipZsTypeId", getShipZsTypeId())
            .append("yuyan", getYuyan())
            .append("tjTime", getTjTime())
            .append("tjFTime", getTjFTime())
            .append("zxNumber", getZxNumber())
            .append("isupdate", getIsupdate())
            .append("isphone", getIsphone())
            .append("zxThcostCurrency", getZxThcostCurrency())
            .append("shipThCostNo", getShipThCostNo())
            .append("ywNumber", getYwNumber())
            .append("isgetin", getIsgetin())
            .append("turncount", getTurncount())
            .append("totalturncount", getTotalturncount())
            .append("totalturncountavg", getTotalturncountavg())
            .append("actualClassdate", getActualClassdate())
            .append("ywFeedback", getYwFeedback())
            .append("detailedAddress", getDetailedAddress())
            .append("putoffClass", getPutoffClass())
            .append("costVerify", getCostVerify())
            .append("dcGaidanState", getDcGaidanState())
            .append("receiveShCostId", getReceiveShCostId())
            .append("receiveOrderEmail", getReceiveOrderEmail())
            .append("xgCostcurrency", getXgCostcurrency())
            .append("qgCostcurrency", getQgCostcurrency())
            .append("shCostcurrency", getShCostcurrency())
            .append("sitecostCurrency", getSitecostCurrency())
            .append("exameTime", getExameTime())
            .append("gdBoat", getGdBoat())
            .append("gdBoaturl", getGdBoaturl())
            .append("gdCjsremark", getGdCjsremark())
            .append("gdVoucherurl", getGdVoucherurl())
            .append("gdVoucher", getGdVoucher())
            .append("trainState", getTrainState())
            .toString();
    }
}
