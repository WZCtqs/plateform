package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ImportTrackTime {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "舱位号")
    private String orderNumber;

    /** 箱号 */
    @Excel(name = "箱号")
    private String boxNum;

    /** 发运日期 */
    @Excel(name = "发运日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValue;

    /** 是否加开 非P'' 是P */
    @Excel(name = "实际班列号")
    private String isClassAdd;

    /** 申请代码列数 */
    @Excel(name = "申请代码列数")
    @ApiModelProperty(value = "申请代码列数")
    private String lieshu;

    /** 计划班列号 */
    @Excel(name = "计划班列号")
    @ApiModelProperty(value = "计划班列号")
    private String classzyNo;

    /** 班列号操作时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classzynoTime;

    /** 关务状态（未报关，报关单放行，报关单查验，出区异常，出区异常已放行） */
    @ApiModelProperty(value = "关务状态（未报关，报关单放行，报关单查验，出区异常，出区异常已放行）")
    private String guanwustate;

    /** 班列号修改记录 */
    @ApiModelProperty(value = "班列号修改记录 ")
    private String classzynoRemark;

    /** 是否申请代码  0否 1是 */
    private String isApplyCode;

    /** 货物品名 */
    private String goodsName;

    /** 跟单员 */
    private String orderMerchandiser;

    /** 箱号校验 */
    private String boxNumCheck;

    /** 箱量 */
    private String containerBoxamount;

    /** 箱型 */
    private String containerType;

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

    /** 发运日期 */
    private String actualClassDate;

    /** 订舱方 */
    private String clientUnit;

    /** 重量 */
    private String goodsKgs;

    /** 下货站 */
    private String orderUnloadsite;

    /** 实际班列id */
    private String actualClassId;

    /** 班列编号 */
    private String classBh;

    /** 订单id */
    private String orderId;

    /** 计费体积 */
    private String goodsVolume;

    /** 货物是否上车0是1否 */
    private Integer isGetin;

    /** 实际班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueStart;

    /** 实际班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueEnd;

    /** 是否正常箱0是1否 */
    private Integer isNormal;

    /** 备注 */
    private String remark;

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

    /** 0为去程 1为回程 */
    private String classEastandwest;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    private String lineTypeid;

    /**  整拼箱 0整柜 1拼箱 */
    private String isconsolidation;

    /**  发运状态 0未发运 1已发运*/
    private String isRun;

    /**  运踪状态：0在堆场 1已进站*/
    private String trackState;

    /** 上货站 */
    private String orderUploadsite;

    /** 客户推荐人 */
    private String clientTjr;

    /** 体积 */
    private String goodsCbm;
}
