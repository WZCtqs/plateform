package com.szhbl.project.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 售后对象 customer_service_message
 *
 * @author szhbl
 * @date 2020-04-09
 */
public class CustomerServiceMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 舱位号 */
    @Excel(name = "舱位号")
    private String orderNumber;

    /** 业务部 */
    @Excel(name = "业务部")
    private String ywb;
    /** 订单id */
    private String orderId;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    private String clientTjrId;

    /** 总助 */
    @Excel(name = "负责人")
    private String general;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    private String clientUnit;

    /** 货品中文名称 */
    @Excel(name = "货品中文名称")
    private String goodsName;

    /** 货品英文名称 */
    private String goodsEnName;

    /** 最外层包装形式 */
    @Excel(name = "包装形式")
    private String goodsPacking;
    /** 0为去程 1为回程 */
    @Excel(name = "去/回程")
    private String classEastandwest;
    /** 收发方式：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "收发方式")
    private String bookingService;
    /** 投诉日期 */
    @Excel(name = "投诉日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date complaintsTime;

    /** 运费支付0是1否 */
    @Excel(name = "运费支付")
    private String freightPay;

    /** 运费金额 */
    @Excel(name = "运费金额")
    private String freightNum;

    /** 投诉类型:0货损1包装破损2货少3延期4费用争议5操作失误6其他 */
    @Excel(name = "投诉类型")
    private String complaintsType;

    /** 是否投诉过期0是1否 */
    @Excel(name = "是否投诉过期")
    private String isOverdue;

    /** 投诉基本情况 */
    @Excel(name = "投诉基本情况")
    private String situation;


    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 结案时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date endDate;




    /** 客户 */
    @Excel(name = "客户")
    private String client;

    /** 公路部 */
    @Excel(name = "公路部")
    private String road;

    /** 拼箱部 */
    @Excel(name = "拼箱部")
    private String lcl;

    /** 操作一部 */
    @Excel(name = "操作一部")
    private String operation;

    /** 集疏部等其他部门 */
    @Excel(name = "集疏部等其他部门")
    private String jishu;

    /** 下一步计划 */
    @Excel(name = "下一步计划")
    private String plan;

    /** 处理方案 */
    @Excel(name = "处理方案")
    private String programme;

    /** 改善流程和措施 */
    @Excel(name = "改善流程和措施")
    private String measure;
    /** 状态：0处理中1预结案2已结案3赔款跟踪 */
    @Excel(name = "状态")
    private String status;


    /**
     * 数据权限查询条件
     */
    private String deptCode;
    private String readType;
    private String userId;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
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
    public void setGeneral(String general)
    {
        this.general = general;
    }

    public String getGeneral()
    {
        return general;
    }
    public void setClientUnit(String clientUnit)
    {
        this.clientUnit = clientUnit;
    }

    public String getClientUnit()
    {
        return clientUnit;
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
    public void setGoodsPacking(String goodsPacking)
    {
        this.goodsPacking = goodsPacking;
    }

    public String getGoodsPacking()
    {
        return goodsPacking;
    }
    public void setBookingService(String bookingService)
    {
        this.bookingService = bookingService;
    }

    public String getBookingService()
    {
        return bookingService;
    }
    public void setClassEastandwest(String classEastandwest)
    {
        this.classEastandwest = classEastandwest;
    }

    public String getClassEastandwest()
    {
        return classEastandwest;
    }
    public void setFreightPay(String freightPay)
    {
        this.freightPay = freightPay;
    }

    public String getFreightPay()
    {
        return freightPay;
    }
    public void setFreightNum(String freightNum)
    {
        this.freightNum = freightNum;
    }

    public String getFreightNum()
    {
        return freightNum;
    }
    public void setComplaintsType(String complaintsType)
    {
        this.complaintsType = complaintsType;
    }

    public String getComplaintsType()
    {
        return complaintsType;
    }
    public void setIsOverdue(String isOverdue)
    {
        this.isOverdue = isOverdue;
    }

    public String getIsOverdue()
    {
        return isOverdue;
    }
    public void setSituation(String situation)
    {
        this.situation = situation;
    }

    public String getSituation()
    {
        return situation;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setComplaintsTime(Date complaintsTime)
    {
        this.complaintsTime = complaintsTime;
    }

    public Date getComplaintsTime()
    {
        return complaintsTime;
    }
    public void setYwb(String ywb)
    {
        this.ywb = ywb;
    }

    public String getYwb()
    {
        return ywb;
    }
    public void setClient(String client)
    {
        this.client = client;
    }

    public String getClient()
    {
        return client;
    }
    public void setRoad(String road)
    {
        this.road = road;
    }

    public String getRoad()
    {
        return road;
    }
    public void setLcl(String lcl)
    {
        this.lcl = lcl;
    }

    public String getLcl()
    {
        return lcl;
    }
    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public String getOperation()
    {
        return operation;
    }
    public void setJishu(String jishu)
    {
        this.jishu = jishu;
    }

    public String getJishu()
    {
        return jishu;
    }
    public void setPlan(String plan)
    {
        this.plan = plan;
    }

    public String getPlan()
    {
        return plan;
    }
    public void setProgramme(String programme)
    {
        this.programme = programme;
    }

    public String getProgramme()
    {
        return programme;
    }
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderNumber", getOrderNumber())
                .append("orderId", getOrderId())
                .append("clientTjr", getClientTjr())
                .append("clientTjrId", getClientTjrId())
                .append("general", getGeneral())
                .append("clientUnit", getClientUnit())
                .append("goodsName", getGoodsName())
                .append("goodsEnName", getGoodsEnName())
                .append("goodsPacking", getGoodsPacking())
                .append("bookingService", getBookingService())
                .append("classEastandwest", getClassEastandwest())
                .append("freightPay", getFreightPay())
                .append("freightNum", getFreightNum())
                .append("complaintsType", getComplaintsType())
                .append("isOverdue", getIsOverdue())
                .append("situation", getSituation())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("endDate", getEndDate())
                .append("complaintsTime", getComplaintsTime())
                .append("ywb", getYwb())
                .append("client", getClient())
                .append("road", getRoad())
                .append("lcl", getLcl())
                .append("operation", getOperation())
                .append("jishu", getJishu())
                .append("plan", getPlan())
                .append("programme", getProgramme())
                .toString();
    }
}
