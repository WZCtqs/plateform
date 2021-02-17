package com.szhbl.project.documentcenter.domain.vo;

import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HP
 */
@ApiModel(value = "提醒设置")
@Data
public class DocRemindPage implements Serializable {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNumber;
    /**
     * 班列日期
     */
    @ApiModelProperty(value = "班列日期")
    private Date classDate;

    /**
     * 班列编号
     */
    @ApiModelProperty(value = "班列编号")
    private String orderClassBh;

    /**
     * 审核通过时间
     */
    @ApiModelProperty(value = "审核通过时间")
    private Date auditTime;
    /**
     * 单证类型key
     */
    @ApiModelProperty(value = "单证类型key")
    private String fileTypeKey;
    /**
     * 单证类型名称
     */
    @ApiModelProperty(value = "单证类型名称")
    private String fileTypeText;
    /**
     * 理论提供单证时间
     */
    @ApiModelProperty(value = "理论提供单证时间")
    private Date normalSupplyNode;
    /**
     * 实际提供时间
     */
    @ApiModelProperty(value = "实际提供时间")
    private Date uploadTime;
    /**
     * 是否上传（0：未上传，1：上传）
     */
    @ApiModelProperty(value = "是否上传 0：未上传，1：上传")
    private Integer uploaded;
    /**
     * 邮件发送状态
     */
    @ApiModelProperty(value = "邮件发送状态")
    private Integer emailStatus;
    /**
     * 邮件发送失败原因
     */
    @ApiModelProperty(value = "邮件发送失败原因")
    private String emailfail;

    private DocOrderDocument document;
    /**
     * 数据权限查询条件
     */
    private String tjr;
    private String tjrId;
    private Long userid;
    private String deptCode;
    public String readType;//0，按对应部门编号精准查询,1,2,3,4,5,6
}
