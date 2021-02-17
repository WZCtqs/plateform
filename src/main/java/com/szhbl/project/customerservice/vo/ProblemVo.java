package com.szhbl.project.customerservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;

import java.util.Date;

public class ProblemVo {

    /** $column.columnComment */
    private String problemId;


    /** 委托书编号 */
    @Excel(name = "委托书编号")
    private String orderNumber;

    /** 集装箱号 */
    @Excel(name = "集装箱号")
    private String containerNumber;

    /** 是否运费支付0是1否 */
    @Excel(name = "是否运费支付")
    private String isPay;

    /** 状态 0处理中 1已回复 2已结案 */
    private String status;

    /** 签收时间 */

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signTime;

    /** 联系邮箱 */
    @Excel(name = "联系邮箱")
    private String email;

    /** 投诉内容 */
    @Excel(name = "投诉内容")
    private String problemContent;

    /** 客户要求 */
    @Excel(name = "客户要求")
    private String requirement;

    /** 投诉反馈 */
    @Excel(name = "投诉反馈")
    private String feedback;

    /** 最外层包装形式 */
    @Excel(name = "最外层包装形式")
    private String goodsPacking;
    /** 货品中文名称 */
    @Excel(name = "货品中文名称")
    private String goodsName;
    /*订舱方*/
    @Excel(name = "订舱方")
    private String clientUnit;
    /*推荐人*/
    @Excel(name = "推荐人")
    private String clientTjr;
    /*投诉日期*/

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    /*处理时间*/

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date doTime;
    @Excel(name = "签收时间")
    private String st;
    @Excel(name = "投诉日期")
    private String pt;
    @Excel(name = "处理时间")
    private  String dt;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGoodsPacking() {
        return goodsPacking;
    }

    public void setGoodsPacking(String goodsPacking) {
        this.goodsPacking = goodsPacking;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    public String getClientTjr() {
        return clientTjr;
    }

    public void setClientTjr(String clientTjr) {
        this.clientTjr = clientTjr;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }
}
