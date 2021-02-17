package com.szhbl.project.cabinarrangement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订单商品对象 busi_shippingorder_goods
 * 
 * @author szhbl
 * @date 2020-01-15
 */
public class BusiShippingorderGoodsPc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String goodsId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsMark;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsEnName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsReport;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsClearance;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsPacking;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsStandard;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsKgs;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsCbm;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsIsscheme;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsHistoryEditrecord;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isDg;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsbz;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String hsbz;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String radioaction;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String isPxupload;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderIspart1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String baojiano;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String customsreq;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderPartaddress1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveShCost1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shCostcurrency1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderCname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderCaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEaddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderCcontacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String receiveOrderEcontacts;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String etxCompany;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String eduty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String eemail;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ephone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String kpState;

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setGoodsMark(String goodsMark) 
    {
        this.goodsMark = goodsMark;
    }

    public String getGoodsMark() 
    {
        return goodsMark;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setGoodsEnName(String goodsEnName) 
    {
        this.goodsEnName = goodsEnName;
    }

    public String getGoodsEnName() 
    {
        return goodsEnName;
    }
    public void setGoodsReport(String goodsReport) 
    {
        this.goodsReport = goodsReport;
    }

    public String getGoodsReport() 
    {
        return goodsReport;
    }
    public void setGoodsClearance(String goodsClearance) 
    {
        this.goodsClearance = goodsClearance;
    }

    public String getGoodsClearance() 
    {
        return goodsClearance;
    }
    public void setGoodsPacking(String goodsPacking) 
    {
        this.goodsPacking = goodsPacking;
    }

    public String getGoodsPacking() 
    {
        return goodsPacking;
    }
    public void setGoodsNumber(String goodsNumber) 
    {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsNumber() 
    {
        return goodsNumber;
    }
    public void setGoodsStandard(String goodsStandard) 
    {
        this.goodsStandard = goodsStandard;
    }

    public String getGoodsStandard() 
    {
        return goodsStandard;
    }
    public void setGoodsKgs(String goodsKgs) 
    {
        this.goodsKgs = goodsKgs;
    }

    public String getGoodsKgs() 
    {
        return goodsKgs;
    }
    public void setGoodsCbm(String goodsCbm) 
    {
        this.goodsCbm = goodsCbm;
    }

    public String getGoodsCbm() 
    {
        return goodsCbm;
    }
    public void setGoodsIsscheme(String goodsIsscheme) 
    {
        this.goodsIsscheme = goodsIsscheme;
    }

    public String getGoodsIsscheme() 
    {
        return goodsIsscheme;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setGoodsHistoryEditrecord(String goodsHistoryEditrecord) 
    {
        this.goodsHistoryEditrecord = goodsHistoryEditrecord;
    }

    public String getGoodsHistoryEditrecord() 
    {
        return goodsHistoryEditrecord;
    }
    public void setIsDg(String isDg) 
    {
        this.isDg = isDg;
    }

    public String getIsDg() 
    {
        return isDg;
    }
    public void setGoodsbz(String goodsbz) 
    {
        this.goodsbz = goodsbz;
    }

    public String getGoodsbz() 
    {
        return goodsbz;
    }
    public void setHsbz(String hsbz) 
    {
        this.hsbz = hsbz;
    }

    public String getHsbz() 
    {
        return hsbz;
    }
    public void setRadioaction(String radioaction) 
    {
        this.radioaction = radioaction;
    }

    public String getRadioaction() 
    {
        return radioaction;
    }
    public void setIsPxupload(String isPxupload) 
    {
        this.isPxupload = isPxupload;
    }

    public String getIsPxupload() 
    {
        return isPxupload;
    }
    public void setReceiveOrderIspart1(String receiveOrderIspart1) 
    {
        this.receiveOrderIspart1 = receiveOrderIspart1;
    }

    public String getReceiveOrderIspart1() 
    {
        return receiveOrderIspart1;
    }
    public void setBaojiano(String baojiano) 
    {
        this.baojiano = baojiano;
    }

    public String getBaojiano() 
    {
        return baojiano;
    }
    public void setCustomsreq(String customsreq) 
    {
        this.customsreq = customsreq;
    }

    public String getCustomsreq() 
    {
        return customsreq;
    }
    public void setReceiveOrderPartaddress1(String receiveOrderPartaddress1) 
    {
        this.receiveOrderPartaddress1 = receiveOrderPartaddress1;
    }

    public String getReceiveOrderPartaddress1() 
    {
        return receiveOrderPartaddress1;
    }
    public void setReceiveShCost1(String receiveShCost1) 
    {
        this.receiveShCost1 = receiveShCost1;
    }

    public String getReceiveShCost1() 
    {
        return receiveShCost1;
    }
    public void setShCostcurrency1(String shCostcurrency1) 
    {
        this.shCostcurrency1 = shCostcurrency1;
    }

    public String getShCostcurrency1() 
    {
        return shCostcurrency1;
    }
    public void setReceiveOrderCname(String receiveOrderCname) 
    {
        this.receiveOrderCname = receiveOrderCname;
    }

    public String getReceiveOrderCname() 
    {
        return receiveOrderCname;
    }
    public void setReceiveOrderEname(String receiveOrderEname) 
    {
        this.receiveOrderEname = receiveOrderEname;
    }

    public String getReceiveOrderEname() 
    {
        return receiveOrderEname;
    }
    public void setReceiveOrderCaddress(String receiveOrderCaddress) 
    {
        this.receiveOrderCaddress = receiveOrderCaddress;
    }

    public String getReceiveOrderCaddress() 
    {
        return receiveOrderCaddress;
    }
    public void setReceiveOrderEaddress(String receiveOrderEaddress) 
    {
        this.receiveOrderEaddress = receiveOrderEaddress;
    }

    public String getReceiveOrderEaddress() 
    {
        return receiveOrderEaddress;
    }
    public void setReceiveOrderCcontacts(String receiveOrderCcontacts) 
    {
        this.receiveOrderCcontacts = receiveOrderCcontacts;
    }

    public String getReceiveOrderCcontacts() 
    {
        return receiveOrderCcontacts;
    }
    public void setReceiveOrderEcontacts(String receiveOrderEcontacts) 
    {
        this.receiveOrderEcontacts = receiveOrderEcontacts;
    }

    public String getReceiveOrderEcontacts() 
    {
        return receiveOrderEcontacts;
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
    public void setEemail(String eemail) 
    {
        this.eemail = eemail;
    }

    public String getEemail() 
    {
        return eemail;
    }
    public void setEphone(String ephone) 
    {
        this.ephone = ephone;
    }

    public String getEphone() 
    {
        return ephone;
    }
    public void setKpState(String kpState) 
    {
        this.kpState = kpState;
    }

    public String getKpState() 
    {
        return kpState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("goodsId", getGoodsId())
            .append("orderId", getOrderId())
            .append("goodsMark", getGoodsMark())
            .append("goodsName", getGoodsName())
            .append("goodsEnName", getGoodsEnName())
            .append("goodsReport", getGoodsReport())
            .append("goodsClearance", getGoodsClearance())
            .append("goodsPacking", getGoodsPacking())
            .append("goodsNumber", getGoodsNumber())
            .append("goodsStandard", getGoodsStandard())
            .append("goodsKgs", getGoodsKgs())
            .append("goodsCbm", getGoodsCbm())
            .append("goodsIsscheme", getGoodsIsscheme())
            .append("remark", getRemark())
            .append("createdate", getCreatedate())
            .append("goodsHistoryEditrecord", getGoodsHistoryEditrecord())
            .append("isDg", getIsDg())
            .append("goodsbz", getGoodsbz())
            .append("hsbz", getHsbz())
            .append("radioaction", getRadioaction())
            .append("isPxupload", getIsPxupload())
            .append("receiveOrderIspart1", getReceiveOrderIspart1())
            .append("baojiano", getBaojiano())
            .append("customsreq", getCustomsreq())
            .append("receiveOrderPartaddress1", getReceiveOrderPartaddress1())
            .append("receiveShCost1", getReceiveShCost1())
            .append("shCostcurrency1", getShCostcurrency1())
            .append("receiveOrderCname", getReceiveOrderCname())
            .append("receiveOrderEname", getReceiveOrderEname())
            .append("receiveOrderCaddress", getReceiveOrderCaddress())
            .append("receiveOrderEaddress", getReceiveOrderEaddress())
            .append("receiveOrderCcontacts", getReceiveOrderCcontacts())
            .append("receiveOrderEcontacts", getReceiveOrderEcontacts())
            .append("etxCompany", getEtxCompany())
            .append("eduty", getEduty())
            .append("eemail", getEemail())
            .append("ephone", getEphone())
            .append("kpState", getKpState())
            .toString();
    }
}
