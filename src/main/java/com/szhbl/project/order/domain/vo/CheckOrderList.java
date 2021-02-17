package com.szhbl.project.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CheckOrderList {
    /** 订单id,主键 */
    @Excel(name = "订单id")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号", readConverterExp = "委托书编号")
    @ApiModelProperty(value = "订单号 托书编号")
    private String orderNumber;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    @ApiModelProperty(value = "订舱方名称")
    private String clientUnit;

    /** 订舱方联系人 */
    @Excel(name = "订舱方联系人")
    @ApiModelProperty(value = "订舱方联系人")
    private String clientContacts;

    /** 发货方 */
    @Excel(name = "发货方")
    @ApiModelProperty(value = "发货方")
    private String shipOrederName;

    /** 发货方联系人 */
    @Excel(name = "发货方联系人")
    @ApiModelProperty(value = "发货方联系人")
    private String shipOrederContacts;

    /** 收货方 */
    @Excel(name = "收货方")
    @ApiModelProperty(value = "收货方")
    private String receiveOrderName;

    /** 收货方联系人 */
    @Excel(name = "收货方联系人")
    @ApiModelProperty(value = "收货方联系人")
    private String receiveOrderContacts;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列编号 （班列表）*/
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    private String orderClassBh;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程(西向) 1为回程(东向)")
    private String classEastandwest;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 （班列表）*/
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型：0是中欧2是中亚3是中越4是中俄")
    private Long lineTypeid;

    /** 线路名称*/
    @Excel(name = "线路名称")
    @ApiModelProperty(value = "线路名称")
    private String lineName;

    /** 跟单姓名 */
    @Excel(name = "跟单姓名")
    @ApiModelProperty(value = "跟单姓名")
    private String orderMerchandiser;

    /** 部门名编号*/
    @Excel(name = "业务部门编号")
    @ApiModelProperty(value = "业务部门编号")
    private String organizationCode;

    /** 部门名称 */
    @Excel(name = "登录者部门编号")
    @ApiModelProperty(value = "登录者部门编号")
    private String deptCode;

    /** 跟单ID */
    @Excel(name = "跟单ID")
    @ApiModelProperty(value = "跟单ID")
    private String orderMerchandiserId;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 0未审核 1已审核通过 2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    @ApiModelProperty(value = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    private String isexamline;

    /** 客户每次托书提交时间 */
    @Excel(name = "客户每次托书提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户每次托书提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjTime;

    /** 托书操作创建时间 */
    @Excel(name = "托书操作创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "托书操作创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    /** 托书操作创建人ID */
    @Excel(name = "托书操作创建人ID")
    @ApiModelProperty(value = "托书操作创建人ID")
    private String createuserid;

    /** 托书操作创建人名称 */
    @Excel(name = "托书操作创建人名称")
    @ApiModelProperty(value = "托书操作创建人名称")
    private String createusername;

    /** 转待审核原因 */
    @Excel(name = "转待审核原因")
    @ApiModelProperty(value = "转待审核原因")
    private String exmaInfo;

    /** 托书列表权限类别*/
    @Excel(name = "托书列表权限类别")
    @ApiModelProperty(value = "托书列表权限类别")
    private String readType;

    public String userid;

    public String isread;

    /** 驳回原因*/
    @Excel(name = "驳回原因")
    @ApiModelProperty(value = "驳回原因")
    private String refuseInfo;

    /** 箱管部驳回原因*/
    @Excel(name = "箱管部驳回原因")
    @ApiModelProperty(value = "箱管部驳回原因")
    private String xgRefuseInfo;

    /** 货品中文名称 */
    @Excel(name = "货品中文名称")
    private String goodsName;

    /** 订舱方ID */
    @Excel(name = "订舱方ID")
    @ApiModelProperty(value = "订舱方ID")
    private String clientId;

    /** 订舱方子账号id */
    @Excel(name = "订舱方子账号id")
    @ApiModelProperty(value = "订舱方子账号id")
    private Long khId;

    /** 业务员编码 */
    @Excel(name = "业务员编码")
    @ApiModelProperty(value = "业务员编码")
    private String ywNumber;

    /** 业务编码 */
    @Excel(name = "业务编码")
    @ApiModelProperty(value = "业务编码")
    private String clientYwNumber;

    /** 业务编码 */
    private String orderUploadcode;
}
