package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExportTrackDl {
	private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 序号 */
    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer sort;

    /** 箱型 */
    @Excel(name = "箱型")
    @ApiModelProperty(value = "箱型")
    private String containerType;

    /** 状态表箱型 */
    @ApiModelProperty(value = "状态表箱型 ")
    private String boxType;

    /** 箱号 */
    @Excel(name = "箱号")
    @ApiModelProperty(value = "箱号")
    private String boxNum;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "箱号校验")
    @ApiModelProperty(value = "箱号校验 0正确 1错误")
    private String boxNumCheck;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "舱位号")
    @ApiModelProperty(value = "托书编号 （舱位号）")
    private String orderNumber;

    /** 箱量 */
    @Excel(name = "箱量")
    @ApiModelProperty(value = "箱量")
    private String containerBoxamount;

    /** 跟单员 */
    @Excel(name = "跟单员")
    @ApiModelProperty(value = "跟单员")
    private String orderMerchandiser;

    /** 品名 */
    @Excel(name = "品名")
    @ApiModelProperty(value = "品名")
    private String goodsName;

    /** 国内报关 商编 */
    @Excel(name = "商编")
    @ApiModelProperty(value = "商编")
    private String goodsInReport;

    /** 到站 */
    @Excel(name = "到站")
    @ApiModelProperty(value = "到站")
    private String orderUnloadsite;

    /** 出口/过境 */
    @Excel(name = "出口/过境")
    @ApiModelProperty(value = "出口/过境")
    private String isInOut;

    /** 总重量 */
    @Excel(name = "总重量")
    @ApiModelProperty(value = "总重量")
    private String totalWeight;

    /** 口岸 */
    @Excel(name = "口岸")
    @ApiModelProperty(value = "口岸")
    private String classPort;

    /** 发货方地址 */
    @Excel(name = "发货方地址&电话")
    @ApiModelProperty(value = "发货方地址")
    private String shipOrederAddress;

    /** 发货方电话 */
    private String shipOrederPhone;

    /** 收货方地址 */
    @Excel(name = "收货方地址&电话")
    @ApiModelProperty(value = "收货方地址&电话")
    private String receiveOrderAddress;

    /** 收货方电话 */
    private String receiveOrderPhone;

    /** 发票金额 */
    @Excel(name = "发票金额")
    @ApiModelProperty(value = "发票金额")
    private String ticketCost;

    /** 订舱方名称 */
    @Excel(name = "客户")
    @ApiModelProperty(value = "客户")
    private String clientUnit;

    /** 订舱方名称 */
    @Excel(name = "车数")
    @ApiModelProperty(value = "车数")
    private String carNum;

    /** 运单号 */
    @Excel(name = "运单号")
    @ApiModelProperty(value = "运单号")
    private String traficNum;

    /** 是否加重 */
    @Excel(name = "是否加重")
    @ApiModelProperty(value = "是否加重")
    private String isWeight;

    /** 实际班列id */
    private String actualClassId;

    /** 订单id */
    private String orderId;

    /** 计费体积 */
    private String goodsVolume;

    /** 货物是否上车0是1否 */
    private Integer isGetin;

    /** 实际班列日期 */
    private String actualClassDate;

    /** 实际班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValue;

    /** 实际班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueStart;

    /** 实际班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueEnd;

    /** 是否加开 非P'' 是P */
    private String isClassAdd;

    /** 是否正常箱0是1否 */
    private Integer isNormal;

    /** 备注 */
    private String remark;

    /** 是否申请代码  0否 1是 */
    private String isApplyCode;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateStart;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateEnd;

    /** 班列编号 */
    private String classBh;

    /** 0为去程 1为回程 */
    private String classEastandwest;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    private String lineTypeid;

    /**  整拼箱 0整柜 1拼箱 */
    private String isconsolidation;

    /**  发运状态 0未发运 1已发运*/
    private String isRun;

    /**  运踪状态：在堆场/已进站*/
    private String trackState;

    /**  入场日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inSpaceDate;

    /**  入场时间*/
    private String inSpaceTime;

    /**  进站日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inStationDate;

    /**  进站时间*/
    private String inStationTime;

    /** 上货站 */
    private String orderUploadsite;

    /** 客户推荐人 */
    private String clientTjr;

    /** 重量 */
    private String goodsKgs;

    /** 体积 */
    private String goodsCbm;

    /** 申请代码列数 */
    @Excel(name = "申请代码列数")
    private String lieshu;

    /** 托书状态 */
    private String isexamline;

    /** 箱管部审核状态 */
    private String xgCheck;

    /** 跟单审核状态 0是，1否*/
    private String gdCheck;

    /** 公路部审核状态 */
    private String roadCheck;

    /**登录者部门编号*/
    private String deptCode;

    /** 用户id*/
    public String userid;

    /** 中亚托书列表权限类别*/
    private String readType;

    /** 随车状态 */
    private String suichestate;

    /** 随车审核人 */
    private String scOperator;

    /** 随车备注 */
    private String scRemarkXh;

    /** 托书随车负责人 */
    private String scPrincipal;

    /** 托书随车备注 */
    private String scremark;

    /** 关务状态 */
    private String guanwustate;

    /** 托书报关状态 */
    private String bgProgress;

    
}
