package com.szhbl.project.order.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订单商品备份对象 busi_shippingorder_goods_backup
 * 
 * @author dps
 * @date 2020-03-24
 */
public class BusiShippingorderGoodsBackup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID，主键 */
    private String goodsId;

    /** 订舱表ID */
    @Excel(name = "订舱表ID")
    private String orderId;

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

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

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

    public void setGoodsInReport(String goodsInReport)
    {
        this.goodsInReport = goodsInReport;
    }
    public String getGoodsInReport()
    {
        return goodsInReport;
    }

    public void setGoodsClearance(String goodsClearance)
    {
        this.goodsClearance = goodsClearance;
    }
    public String getGoodsClearance()
    {
        return goodsClearance;
    }

    public void setGoodsOutClearance(String goodsOutClearance)
    {
        this.goodsOutClearance = goodsOutClearance;
    }
    public String getGoodsOutClearance()
    {
        return goodsOutClearance;
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
            .append("goodsInReport", getGoodsInReport())
            .append("goodsClearance", getGoodsClearance())
            .append("goodsOutClearance", getGoodsOutClearance())
            .append("goodsPacking", getGoodsPacking())
            .append("goodsNumber", getGoodsNumber())
            .append("goodsStandard", getGoodsStandard())
            .append("goodsKgs", getGoodsKgs())
            .append("goodsCbm", getGoodsCbm())
            .append("goodsIsscheme", getGoodsIsscheme())
            .append("remark", getRemark())
            .append("createdate", getCreatedate())
            .append("goodsHistoryEditrecord", getGoodsHistoryEditrecord())
            .append("goodsbz", getGoodsbz())
            .append("hsbz", getHsbz())
            .append("radioaction", getRadioaction())
            .append("kpState", getKpState())
            .toString();
    }
}
