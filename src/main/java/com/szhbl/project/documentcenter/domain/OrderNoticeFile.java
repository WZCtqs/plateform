package com.szhbl.project.documentcenter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "入货通知书查询页面")
@Data
public class OrderNoticeFile implements Serializable {

    /**
     * 订舱托书id
     */
    @ApiModelProperty(value = "订舱托书id")
    private String orderId;

    /**
     * 查询传参使用字段
     */
    private String[] orderIds;

    /**
     * 托书编号
     */
    @ApiModelProperty(value = "托书编号")
    private String orderNumber;

    /**
     * 站点
     */
    @ApiModelProperty(value = "站点")
    private String classSite;

    /**
     * 班列编号
     */
    @ApiModelProperty(value = "班列编号")
    private String classNumber;

    /**
     * 班列日期
     */
    @ApiModelProperty(value = "班列日期")
    private String classDate;

    /**
     * 班列方向 0：去程西向，1：回程东向
     */
    @ApiModelProperty(value = "班列方向")
    private String classEastAndWest;

    /**
     * 订舱方
     */
    @ApiModelProperty(value = "订舱方")
    private String clientUnit;

    /**
     * 发货方
     */
    @ApiModelProperty(value = "发货方")
    private String consignor;

    /**
     * 发货方邮箱
     */
    private String clientEmail;

    /**
     * 收获方
     */
    @ApiModelProperty(value = "收获方")
    private String consignee;

    /**
     * 整拼箱：0：整箱，1：拼箱
     */
    @ApiModelProperty(value = "整拼箱")
    private String isConsolidation;

    /**
     * 托书状态（0未审核 1已审核通过
     * 2已审核未通过 3已取消的委托
     * 4转待审核  5草稿 6转待失败）
     */
    @ApiModelProperty(value = "托书状态")
    private String isExamline;

    /**
     * 发送状态
     */
    @ApiModelProperty(value = "发送状态")
    private String isSendFile;

    /**
     * 合作方是否签署协议
     */
    @ApiModelProperty(value = "合作方是否签署协议")
    private String isSign;

    /**
     * 入货通知书地址
     */
    private String fileUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 数据权限查询条件
     */
    private String tjr;
    private String tjrId;
    private Long userid;
    private String deptCode;
    public String readType;//0，按对应部门编号精准查询,1,2,3,4,5,6
}
