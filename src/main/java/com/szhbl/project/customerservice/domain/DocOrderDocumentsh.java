package com.szhbl.project.customerservice.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 单证文件对象 doc_order_document
 * 
 * @author szhbl
 * @date 2020-03-27
 */
public class DocOrderDocumentsh extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单id */
    @Excel(name = "订单id")
    private String orderId;

    /** 托书编号（舱位号） */
    @Excel(name = "托书编号", readConverterExp = "舱=位号")
    private String orderNumber;

    /** 单证类型 */
    @Excel(name = "单证类型")
    private String fileTypeKey;

    /** 单证文件名 */
    @Excel(name = "单证文件名")
    private String fileName;

    /**
     * 单证地址url
     */
    @Excel(name = "单证地址url")
    private String fileUrl;

    /**
     * 客户上传提货时间
     */
    @Excel(name = "客户上传提货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickGoodsTimec;

    /**
     * 客户提货联系人
     */
    @Excel(name = "客户提货联系人")
    private String pickGoodsContacts;

    @Excel(name = "客户提货联系方式")
    private String pickGoodsPhone;
    /**
     * 客户提货详细地址
     */
    @Excel(name = "客户提货详细地址")
    private String pickGoodsAddress;

    /**
     * 箱号
     */
    @Excel(name = "箱号")
    private Long containerNo;

    /**
     * 箱型
     */
    @Excel(name = "箱型")
    private String containerType;

    private String sealnumber;

    /** 发货方、出口商（提单草单） */
//    @Excel(name = "发货方、出口商", readConverterExp = "提=单草单")
//    private String shipperExporter;

    /** 收货方（提单草单） */
//    @Excel(name = "收货方", readConverterExp = "提=单草单")
//    private String consignee;

    /** 通知方（提单草单） */
//    @Excel(name = "通知方", readConverterExp = "提=单草单")
//    private String notifyParty;

    /** 商品描述（提单草单） */
//    @Excel(name = "商品描述", readConverterExp = "提=单草单")
//    private String goodsDescription;

    /**
     * 上传时间
     */
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /**
     * 上传着
     */
    @Excel(name = "上传着")
    private String uploadBy;

    /**
     * 上传者部门
     */
    @Excel(name = "上传者部门")
    private String uploadDept;

    /**
     * 来源系统
     */
    @Excel(name = "来源系统")
    private String formSystem;

    /**
     * 提单草案是否已经提交
     */
    @Excel(name = "提单草案是否已经提交")
    private Long issubmit;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setFileTypeKey(String fileTypeKey) 
    {
        this.fileTypeKey = fileTypeKey;
    }

    public String getFileTypeKey() 
    {
        return fileTypeKey;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
    }
    public void setPickGoodsTimec(Date pickGoodsTimec) 
    {
        this.pickGoodsTimec = pickGoodsTimec;
    }

    public Date getPickGoodsTimec() 
    {
        return pickGoodsTimec;
    }
    public void setPickGoodsContacts(String pickGoodsContacts) 
    {
        this.pickGoodsContacts = pickGoodsContacts;
    }

    public String getPickGoodsContacts() {
        return pickGoodsContacts;
    }

    public void setPickGoodsAddress(String pickGoodsAddress) {
        this.pickGoodsAddress = pickGoodsAddress;
    }

    public String getPickGoodsPhone() {
        return pickGoodsPhone;
    }

    public void setPickGoodsPhone(String pickGoodsPhone) {
        this.pickGoodsPhone = pickGoodsPhone;
    }

    public String getPickGoodsAddress() {
        return pickGoodsAddress;
    }

    public void setContainerNo(Long containerNo) {
        this.containerNo = containerNo;
    }

    public Long getContainerNo() 
    {
        return containerNo;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContainerType() {
        return containerType;
    }

    public String getSealnumber() {
        return sealnumber;
    }

    public void setSealnumber(String sealnumber) {
        this.sealnumber = sealnumber;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() {
        return uploadTime;
    }
    public void setUploadBy(String uploadBy) 
    {
        this.uploadBy = uploadBy;
    }

    public String getUploadBy() 
    {
        return uploadBy;
    }
    public void setUploadDept(String uploadDept) 
    {
        this.uploadDept = uploadDept;
    }

    public String getUploadDept() 
    {
        return uploadDept;
    }
    public void setFormSystem(String formSystem) 
    {
        this.formSystem = formSystem;
    }

    public String getFormSystem() 
    {
        return formSystem;
    }
    public void setIssubmit(Long issubmit) 
    {
        this.issubmit = issubmit;
    }

    public Long getIssubmit() 
    {
        return issubmit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("orderNumber", getOrderNumber())
                .append("fileTypeKey", getFileTypeKey())
                .append("fileName", getFileName())
                .append("fileUrl", getFileUrl())
                .append("pickGoodsTimec", getPickGoodsTimec())
                .append("pickGoodsContacts", getPickGoodsContacts())
                .append("pickGoodsAddress", getPickGoodsAddress())
                .append("containerNo", getContainerNo())
                .append("containerType", getContainerType())
//            .append("shipperExporter", getShipperExporter())
//            .append("consignee", getConsignee())
//            .append("notifyParty", getNotifyParty())
//            .append("goodsDescription", getGoodsDescription())
            .append("uploadTime", getUploadTime())
            .append("uploadBy", getUploadBy())
            .append("uploadDept", getUploadDept())
            .append("formSystem", getFormSystem())
            .append("issubmit", getIssubmit())
            .toString();
    }
}
