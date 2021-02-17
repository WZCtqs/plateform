package com.szhbl.project.cabinarrangement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订单对象 busi_shippingorder
 * 
 * @author szhbl
 * @date 2020-01-15
 */
public class BusiShippingorderPc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String stationid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderAuditIspass;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderAuditBelongto;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderAuditRemark;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date orderAuditCreatedate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderAuditUserid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderAuditUsername;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String classId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date classDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String classSite;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String classNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String classEastandwest;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderUploadsite;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderUnloadsite;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderUploadcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderUnloadcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long dictId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dictName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderDeptname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderMerchandiser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderMerchandiserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderClasscost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderSpecialcost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderRoadcost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderIsticket;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientUnit;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientContacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientTel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientOrderBindingaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientOrderBindingway;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientOrderRemarks;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String containerBoxamount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String containerType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String containerTypeval;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String containerBearing;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String containerVolume;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientOrderRepaycontainer;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderIsdispatch;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderDispatchcost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederContacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederTel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderBinningway;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderUnloadaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderUnloadcontacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderUnloadway;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderUnloadwayEmail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date shipOrderUnloadtime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date shipOrderSendtime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnIsdispatch;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnDispatchcost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederEnName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederEnContacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederEnTel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederEnPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrederEnAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnBinningway;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnUnloadaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnUnloadcontacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderEnUnloadway;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date shipOrderEnUnloadtime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipOrderThStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date shipOrderEnSendtime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderContacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderTel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderIsclearance;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderIspart;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderFbStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderPartaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderReceiveemail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnContacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnTel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnIsclearance;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnIspart;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnPartaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEnReceiveemail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderZihcontacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderZihtel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsCannotpileup;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsFragile;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsGeneral;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isexamline;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isconsolidation;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String createuserid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String createusername;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipThCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientBgCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveHxAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveXgCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveQgCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipJzCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveShCost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sitecost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientTjr;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientTjrId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveDhSite;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipFhSite;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipHyd;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveHyd;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date qxdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipThType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipThTypeId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipZsType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipZsTypeId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String zdyy;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String yuyan;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date tjTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String zxNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isupdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String historyEditrecord;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isphone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shipThCostNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String zxThcostCurrency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pricenum;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String issendemail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pushemailaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ywNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isgetin;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dczState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long turncount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String actualClassdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long cangdanRelation;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountsState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ywFeedback;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String detailedAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long putoffClass;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String costVerify;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String billState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dcGaidanState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveemailUserSend;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date sendemailTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sendemailNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveShCostId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEmail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderClassBh;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String xgCostcurrency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String qgCostcurrency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shCostcurrency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sitecostCurrency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String changeClassnumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String changeClassdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String changeBoxnumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ischange;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date exameTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gdBoat;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gdBoaturl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gdCjsremark;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gdVoucherurl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gdVoucher;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long totalturncount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long totalturncountavg;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderZihemail;

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
    public void setOrderAuditIspass(String orderAuditIspass) 
    {
        this.orderAuditIspass = orderAuditIspass;
    }

    public String getOrderAuditIspass() 
    {
        return orderAuditIspass;
    }
    public void setOrderAuditBelongto(String orderAuditBelongto) 
    {
        this.orderAuditBelongto = orderAuditBelongto;
    }

    public String getOrderAuditBelongto() 
    {
        return orderAuditBelongto;
    }
    public void setOrderAuditRemark(String orderAuditRemark) 
    {
        this.orderAuditRemark = orderAuditRemark;
    }

    public String getOrderAuditRemark() 
    {
        return orderAuditRemark;
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
    public void setOrderClasscost(String orderClasscost) 
    {
        this.orderClasscost = orderClasscost;
    }

    public String getOrderClasscost() 
    {
        return orderClasscost;
    }
    public void setOrderSpecialcost(String orderSpecialcost) 
    {
        this.orderSpecialcost = orderSpecialcost;
    }

    public String getOrderSpecialcost() 
    {
        return orderSpecialcost;
    }
    public void setOrderRoadcost(String orderRoadcost) 
    {
        this.orderRoadcost = orderRoadcost;
    }

    public String getOrderRoadcost() 
    {
        return orderRoadcost;
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
    public void setClientPhone(String clientPhone) 
    {
        this.clientPhone = clientPhone;
    }

    public String getClientPhone() 
    {
        return clientPhone;
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
    public void setContainerBearing(String containerBearing) 
    {
        this.containerBearing = containerBearing;
    }

    public String getContainerBearing() 
    {
        return containerBearing;
    }
    public void setContainerVolume(String containerVolume) 
    {
        this.containerVolume = containerVolume;
    }

    public String getContainerVolume() 
    {
        return containerVolume;
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
    public void setShipOrderDispatchcost(String shipOrderDispatchcost) 
    {
        this.shipOrderDispatchcost = shipOrderDispatchcost;
    }

    public String getShipOrderDispatchcost() 
    {
        return shipOrderDispatchcost;
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
    public void setShipOrederTel(String shipOrederTel) 
    {
        this.shipOrederTel = shipOrederTel;
    }

    public String getShipOrederTel() 
    {
        return shipOrederTel;
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
    public void setShipOrderEnIsdispatch(String shipOrderEnIsdispatch) 
    {
        this.shipOrderEnIsdispatch = shipOrderEnIsdispatch;
    }

    public String getShipOrderEnIsdispatch() 
    {
        return shipOrderEnIsdispatch;
    }
    public void setShipOrderEnDispatchcost(String shipOrderEnDispatchcost) 
    {
        this.shipOrderEnDispatchcost = shipOrderEnDispatchcost;
    }

    public String getShipOrderEnDispatchcost() 
    {
        return shipOrderEnDispatchcost;
    }
    public void setShipOrederEnName(String shipOrederEnName) 
    {
        this.shipOrederEnName = shipOrederEnName;
    }

    public String getShipOrederEnName() 
    {
        return shipOrederEnName;
    }
    public void setShipOrederEnContacts(String shipOrederEnContacts) 
    {
        this.shipOrederEnContacts = shipOrederEnContacts;
    }

    public String getShipOrederEnContacts() 
    {
        return shipOrederEnContacts;
    }
    public void setShipOrederEnTel(String shipOrederEnTel) 
    {
        this.shipOrederEnTel = shipOrederEnTel;
    }

    public String getShipOrederEnTel() 
    {
        return shipOrederEnTel;
    }
    public void setShipOrederEnPhone(String shipOrederEnPhone) 
    {
        this.shipOrederEnPhone = shipOrederEnPhone;
    }

    public String getShipOrederEnPhone() 
    {
        return shipOrederEnPhone;
    }
    public void setShipOrederEnAddress(String shipOrederEnAddress) 
    {
        this.shipOrederEnAddress = shipOrederEnAddress;
    }

    public String getShipOrederEnAddress() 
    {
        return shipOrederEnAddress;
    }
    public void setShipOrderEnBinningway(String shipOrderEnBinningway) 
    {
        this.shipOrderEnBinningway = shipOrderEnBinningway;
    }

    public String getShipOrderEnBinningway() 
    {
        return shipOrderEnBinningway;
    }
    public void setShipOrderEnUnloadaddress(String shipOrderEnUnloadaddress) 
    {
        this.shipOrderEnUnloadaddress = shipOrderEnUnloadaddress;
    }

    public String getShipOrderEnUnloadaddress() 
    {
        return shipOrderEnUnloadaddress;
    }
    public void setShipOrderEnUnloadcontacts(String shipOrderEnUnloadcontacts) 
    {
        this.shipOrderEnUnloadcontacts = shipOrderEnUnloadcontacts;
    }

    public String getShipOrderEnUnloadcontacts() 
    {
        return shipOrderEnUnloadcontacts;
    }
    public void setShipOrderEnUnloadway(String shipOrderEnUnloadway) 
    {
        this.shipOrderEnUnloadway = shipOrderEnUnloadway;
    }

    public String getShipOrderEnUnloadway() 
    {
        return shipOrderEnUnloadway;
    }
    public void setShipOrderEnUnloadtime(Date shipOrderEnUnloadtime) 
    {
        this.shipOrderEnUnloadtime = shipOrderEnUnloadtime;
    }

    public Date getShipOrderEnUnloadtime() 
    {
        return shipOrderEnUnloadtime;
    }
    public void setShipOrderThStatus(String shipOrderThStatus) 
    {
        this.shipOrderThStatus = shipOrderThStatus;
    }

    public String getShipOrderThStatus() 
    {
        return shipOrderThStatus;
    }
    public void setShipOrderEnSendtime(Date shipOrderEnSendtime) 
    {
        this.shipOrderEnSendtime = shipOrderEnSendtime;
    }

    public Date getShipOrderEnSendtime() 
    {
        return shipOrderEnSendtime;
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
    public void setReceiveOrderTel(String receiveOrderTel) 
    {
        this.receiveOrderTel = receiveOrderTel;
    }

    public String getReceiveOrderTel() 
    {
        return receiveOrderTel;
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
    public void setReceiveOrderEnTel(String receiveOrderEnTel) 
    {
        this.receiveOrderEnTel = receiveOrderEnTel;
    }

    public String getReceiveOrderEnTel() 
    {
        return receiveOrderEnTel;
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
    public void setReceiveOrderEnIsclearance(String receiveOrderEnIsclearance) 
    {
        this.receiveOrderEnIsclearance = receiveOrderEnIsclearance;
    }

    public String getReceiveOrderEnIsclearance() 
    {
        return receiveOrderEnIsclearance;
    }
    public void setReceiveOrderEnIspart(String receiveOrderEnIspart) 
    {
        this.receiveOrderEnIspart = receiveOrderEnIspart;
    }

    public String getReceiveOrderEnIspart() 
    {
        return receiveOrderEnIspart;
    }
    public void setReceiveOrderEnPartaddress(String receiveOrderEnPartaddress) 
    {
        this.receiveOrderEnPartaddress = receiveOrderEnPartaddress;
    }

    public String getReceiveOrderEnPartaddress() 
    {
        return receiveOrderEnPartaddress;
    }
    public void setReceiveOrderEnReceiveemail(String receiveOrderEnReceiveemail) 
    {
        this.receiveOrderEnReceiveemail = receiveOrderEnReceiveemail;
    }

    public String getReceiveOrderEnReceiveemail() 
    {
        return receiveOrderEnReceiveemail;
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
    public void setReceiveDhSite(String receiveDhSite) 
    {
        this.receiveDhSite = receiveDhSite;
    }

    public String getReceiveDhSite() 
    {
        return receiveDhSite;
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
    public void setQxdate(Date qxdate) 
    {
        this.qxdate = qxdate;
    }

    public Date getQxdate() 
    {
        return qxdate;
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
    public void setZdyy(String zdyy) 
    {
        this.zdyy = zdyy;
    }

    public String getZdyy() 
    {
        return zdyy;
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
    public void setHistoryEditrecord(String historyEditrecord) 
    {
        this.historyEditrecord = historyEditrecord;
    }

    public String getHistoryEditrecord() 
    {
        return historyEditrecord;
    }
    public void setIsphone(String isphone) 
    {
        this.isphone = isphone;
    }

    public String getIsphone() 
    {
        return isphone;
    }
    public void setShipThCostNo(String shipThCostNo) 
    {
        this.shipThCostNo = shipThCostNo;
    }

    public String getShipThCostNo() 
    {
        return shipThCostNo;
    }
    public void setZxThcostCurrency(String zxThcostCurrency) 
    {
        this.zxThcostCurrency = zxThcostCurrency;
    }

    public String getZxThcostCurrency() 
    {
        return zxThcostCurrency;
    }
    public void setPricenum(String pricenum) 
    {
        this.pricenum = pricenum;
    }

    public String getPricenum() 
    {
        return pricenum;
    }
    public void setIssendemail(String issendemail) 
    {
        this.issendemail = issendemail;
    }

    public String getIssendemail() 
    {
        return issendemail;
    }
    public void setPushemailaddress(String pushemailaddress) 
    {
        this.pushemailaddress = pushemailaddress;
    }

    public String getPushemailaddress() 
    {
        return pushemailaddress;
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
    public void setDczState(String dczState) 
    {
        this.dczState = dczState;
    }

    public String getDczState() 
    {
        return dczState;
    }
    public void setTurncount(Long turncount) 
    {
        this.turncount = turncount;
    }

    public Long getTurncount() 
    {
        return turncount;
    }
    public void setActualClassdate(String actualClassdate) 
    {
        this.actualClassdate = actualClassdate;
    }

    public String getActualClassdate() 
    {
        return actualClassdate;
    }
    public void setCangdanRelation(Long cangdanRelation) 
    {
        this.cangdanRelation = cangdanRelation;
    }

    public Long getCangdanRelation() 
    {
        return cangdanRelation;
    }
    public void setDiscountsState(Long discountsState) 
    {
        this.discountsState = discountsState;
    }

    public Long getDiscountsState() 
    {
        return discountsState;
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
    public void setBillState(String billState) 
    {
        this.billState = billState;
    }

    public String getBillState() 
    {
        return billState;
    }
    public void setDcGaidanState(String dcGaidanState) 
    {
        this.dcGaidanState = dcGaidanState;
    }

    public String getDcGaidanState() 
    {
        return dcGaidanState;
    }
    public void setReceiveemailUserSend(String receiveemailUserSend) 
    {
        this.receiveemailUserSend = receiveemailUserSend;
    }

    public String getReceiveemailUserSend() 
    {
        return receiveemailUserSend;
    }
    public void setSendemailTime(Date sendemailTime) 
    {
        this.sendemailTime = sendemailTime;
    }

    public Date getSendemailTime() 
    {
        return sendemailTime;
    }
    public void setSendemailNumber(Long sendemailNumber) 
    {
        this.sendemailNumber = sendemailNumber;
    }

    public Long getSendemailNumber() 
    {
        return sendemailNumber;
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
    public void setOrderClassBh(String orderClassBh) 
    {
        this.orderClassBh = orderClassBh;
    }

    public String getOrderClassBh() 
    {
        return orderClassBh;
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
    public void setChangeClassnumber(String changeClassnumber) 
    {
        this.changeClassnumber = changeClassnumber;
    }

    public String getChangeClassnumber() 
    {
        return changeClassnumber;
    }
    public void setChangeClassdate(String changeClassdate) 
    {
        this.changeClassdate = changeClassdate;
    }

    public String getChangeClassdate() 
    {
        return changeClassdate;
    }
    public void setChangeBoxnumber(String changeBoxnumber) 
    {
        this.changeBoxnumber = changeBoxnumber;
    }

    public String getChangeBoxnumber() 
    {
        return changeBoxnumber;
    }
    public void setIschange(Long ischange) 
    {
        this.ischange = ischange;
    }

    public Long getIschange() 
    {
        return ischange;
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
    public void setReceiveOrderZihemail(String receiveOrderZihemail) 
    {
        this.receiveOrderZihemail = receiveOrderZihemail;
    }

    public String getReceiveOrderZihemail() 
    {
        return receiveOrderZihemail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("stationid", getStationid())
            .append("orderNumber", getOrderNumber())
            .append("orderAuditIspass", getOrderAuditIspass())
            .append("orderAuditBelongto", getOrderAuditBelongto())
            .append("orderAuditRemark", getOrderAuditRemark())
            .append("orderAuditCreatedate", getOrderAuditCreatedate())
            .append("orderAuditUserid", getOrderAuditUserid())
            .append("orderAuditUsername", getOrderAuditUsername())
            .append("classId", getClassId())
            .append("classDate", getClassDate())
            .append("classSite", getClassSite())
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
            .append("orderClasscost", getOrderClasscost())
            .append("orderSpecialcost", getOrderSpecialcost())
            .append("orderRoadcost", getOrderRoadcost())
            .append("orderIsticket", getOrderIsticket())
            .append("clientId", getClientId())
            .append("clientUnit", getClientUnit())
            .append("clientContacts", getClientContacts())
            .append("clientTel", getClientTel())
            .append("clientPhone", getClientPhone())
            .append("clientAddress", getClientAddress())
            .append("clientOrderBindingaddress", getClientOrderBindingaddress())
            .append("clientOrderBindingway", getClientOrderBindingway())
            .append("clientOrderRemarks", getClientOrderRemarks())
            .append("containerBoxamount", getContainerBoxamount())
            .append("containerType", getContainerType())
            .append("containerTypeval", getContainerTypeval())
            .append("containerBearing", getContainerBearing())
            .append("containerVolume", getContainerVolume())
            .append("clientOrderRepaycontainer", getClientOrderRepaycontainer())
            .append("shipOrderIsdispatch", getShipOrderIsdispatch())
            .append("shipOrderDispatchcost", getShipOrderDispatchcost())
            .append("shipOrederName", getShipOrederName())
            .append("shipOrederContacts", getShipOrederContacts())
            .append("shipOrederTel", getShipOrederTel())
            .append("shipOrederPhone", getShipOrederPhone())
            .append("shipOrederAddress", getShipOrederAddress())
            .append("shipOrderBinningway", getShipOrderBinningway())
            .append("shipOrderUnloadaddress", getShipOrderUnloadaddress())
            .append("shipOrderUnloadcontacts", getShipOrderUnloadcontacts())
            .append("shipOrderUnloadway", getShipOrderUnloadway())
            .append("shipOrderUnloadwayEmail", getShipOrderUnloadwayEmail())
            .append("shipOrderUnloadtime", getShipOrderUnloadtime())
            .append("shipOrderSendtime", getShipOrderSendtime())
            .append("shipOrderEnIsdispatch", getShipOrderEnIsdispatch())
            .append("shipOrderEnDispatchcost", getShipOrderEnDispatchcost())
            .append("shipOrederEnName", getShipOrederEnName())
            .append("shipOrederEnContacts", getShipOrederEnContacts())
            .append("shipOrederEnTel", getShipOrederEnTel())
            .append("shipOrederEnPhone", getShipOrederEnPhone())
            .append("shipOrederEnAddress", getShipOrederEnAddress())
            .append("shipOrderEnBinningway", getShipOrderEnBinningway())
            .append("shipOrderEnUnloadaddress", getShipOrderEnUnloadaddress())
            .append("shipOrderEnUnloadcontacts", getShipOrderEnUnloadcontacts())
            .append("shipOrderEnUnloadway", getShipOrderEnUnloadway())
            .append("shipOrderEnUnloadtime", getShipOrderEnUnloadtime())
            .append("shipOrderThStatus", getShipOrderThStatus())
            .append("shipOrderEnSendtime", getShipOrderEnSendtime())
            .append("receiveOrderName", getReceiveOrderName())
            .append("receiveOrderContacts", getReceiveOrderContacts())
            .append("receiveOrderTel", getReceiveOrderTel())
            .append("receiveOrderPhone", getReceiveOrderPhone())
            .append("receiveOrderAddress", getReceiveOrderAddress())
            .append("receiveOrderIsclearance", getReceiveOrderIsclearance())
            .append("receiveOrderIspart", getReceiveOrderIspart())
            .append("receiveOrderFbStatus", getReceiveOrderFbStatus())
            .append("receiveOrderPartaddress", getReceiveOrderPartaddress())
            .append("receiveOrderReceiveemail", getReceiveOrderReceiveemail())
            .append("receiveOrderEnName", getReceiveOrderEnName())
            .append("receiveOrderEnContacts", getReceiveOrderEnContacts())
            .append("receiveOrderEnTel", getReceiveOrderEnTel())
            .append("receiveOrderEnPhone", getReceiveOrderEnPhone())
            .append("receiveOrderEnAddress", getReceiveOrderEnAddress())
            .append("receiveOrderEnIsclearance", getReceiveOrderEnIsclearance())
            .append("receiveOrderEnIspart", getReceiveOrderEnIspart())
            .append("receiveOrderEnPartaddress", getReceiveOrderEnPartaddress())
            .append("receiveOrderEnReceiveemail", getReceiveOrderEnReceiveemail())
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
            .append("receiveQgCost", getReceiveQgCost())
            .append("shipJzCost", getShipJzCost())
            .append("receiveShCost", getReceiveShCost())
            .append("sitecost", getSitecost())
            .append("clientTjr", getClientTjr())
            .append("clientTjrId", getClientTjrId())
            .append("receiveDhSite", getReceiveDhSite())
            .append("shipFhSite", getShipFhSite())
            .append("shipHyd", getShipHyd())
            .append("receiveHyd", getReceiveHyd())
            .append("qxdate", getQxdate())
            .append("shipThType", getShipThType())
            .append("shipThTypeId", getShipThTypeId())
            .append("shipZsType", getShipZsType())
            .append("shipZsTypeId", getShipZsTypeId())
            .append("zdyy", getZdyy())
            .append("yuyan", getYuyan())
            .append("tjTime", getTjTime())
            .append("zxNumber", getZxNumber())
            .append("isupdate", getIsupdate())
            .append("historyEditrecord", getHistoryEditrecord())
            .append("isphone", getIsphone())
            .append("shipThCostNo", getShipThCostNo())
            .append("zxThcostCurrency", getZxThcostCurrency())
            .append("pricenum", getPricenum())
            .append("issendemail", getIssendemail())
            .append("pushemailaddress", getPushemailaddress())
            .append("ywNumber", getYwNumber())
            .append("isgetin", getIsgetin())
            .append("dczState", getDczState())
            .append("turncount", getTurncount())
            .append("actualClassdate", getActualClassdate())
            .append("cangdanRelation", getCangdanRelation())
            .append("discountsState", getDiscountsState())
            .append("ywFeedback", getYwFeedback())
            .append("detailedAddress", getDetailedAddress())
            .append("putoffClass", getPutoffClass())
            .append("costVerify", getCostVerify())
            .append("billState", getBillState())
            .append("dcGaidanState", getDcGaidanState())
            .append("receiveemailUserSend", getReceiveemailUserSend())
            .append("sendemailTime", getSendemailTime())
            .append("sendemailNumber", getSendemailNumber())
            .append("receiveShCostId", getReceiveShCostId())
            .append("receiveOrderEmail", getReceiveOrderEmail())
            .append("orderClassBh", getOrderClassBh())
            .append("xgCostcurrency", getXgCostcurrency())
            .append("qgCostcurrency", getQgCostcurrency())
            .append("shCostcurrency", getShCostcurrency())
            .append("sitecostCurrency", getSitecostCurrency())
            .append("changeClassnumber", getChangeClassnumber())
            .append("changeClassdate", getChangeClassdate())
            .append("changeBoxnumber", getChangeBoxnumber())
            .append("ischange", getIschange())
            .append("exameTime", getExameTime())
            .append("gdBoat", getGdBoat())
            .append("gdBoaturl", getGdBoaturl())
            .append("gdCjsremark", getGdCjsremark())
            .append("gdVoucherurl", getGdVoucherurl())
            .append("gdVoucher", getGdVoucher())
            .append("totalturncount", getTotalturncount())
            .append("totalturncountavg", getTotalturncountavg())
            .append("receiveOrderZihemail", getReceiveOrderZihemail())
            .toString();
    }
}
