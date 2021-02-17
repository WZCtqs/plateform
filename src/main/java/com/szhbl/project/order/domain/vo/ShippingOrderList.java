package com.szhbl.project.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ShippingOrderList {
    /** 订单id,主键 */
    @Excel(name = "订单id")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 部门名称 */
    @Excel(name = "登录者部门编号")
    @ApiModelProperty(value = "登录者部门编号")
    private String deptCode;

    /** 部门名称 */
    @Excel(name = "登录者用户姓名")
    @ApiModelProperty(value = "登录者用户姓名")
    private String nickName;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    @ApiModelProperty(value = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    @Excel(name = "客户推荐人id")
    @ApiModelProperty(value = "客户推荐人id")
    private String clientTjrId;

    /** 跟单姓名 */
    @Excel(name = "跟单姓名")
    @ApiModelProperty(value = "跟单姓名(搜索条件)")
    private String orderMerchandiser;

    /** 跟单ID */
    @Excel(name = "跟单ID")
    @ApiModelProperty(value = "跟单ID")
    private String orderMerchandiserId;

    /** 订舱方ID */
    @Excel(name = "订舱方ID")
    @ApiModelProperty(value = "订舱方ID")
    private String clientId;

    /** 订舱方子账号id */
    @Excel(name = "订舱方子账号id")
    @ApiModelProperty(value = "订舱方子账号id")
    private Long khId;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    @ApiModelProperty(value = "订舱方名称")
    private String clientUnit;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列号 */
    @Excel(name = "班列号")
    @ApiModelProperty(value = "班列号")
    private String classNumber;

    /** 班列ID */
    @Excel(name = "班列ID")
    @ApiModelProperty(value = "班列ID")
    private String classId;

    /** 班列编号 （班列表）*/
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    private String orderClassBh;

    /** 0未审核 1已审核通过 2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败 */
    @Excel(name = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    @ApiModelProperty(value = "0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败")
    private String isexamline;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程(西向) 1为回程(东向)")
    private String classEastandwest;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 （班列表）*/
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型：0是中欧2是中亚3是中越4是中俄")
    private Long lineTypeid;

    /** 站点ID,回程入货通知书上堆场地址 (busi_station表)*/
    @Excel(name = "车站名称,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "车站名称,回程入货通知书上堆场地址")
    private String station;

    /** 语言（0是中文订单1是英文） */
    @Excel(name = "语言", readConverterExp = "0=是中文订单1是英文")
    @ApiModelProperty(value = "语言 （0是中文订单1是英文）")
    private String yuyan;

    /** 0手机下单 */
    @Excel(name = "0手机下单，1pc端下单")
    @ApiModelProperty(value = "0手机下单，1pc端下单")
    private String isphone;

    /** 部门名称 */
    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orderDeptName;

    /** 部门名编号*/
    @Excel(name = "业务部门编号")
    @ApiModelProperty(value = "业务部门编号")
    private String organizationCode;

    /** 托书列表权限类别*/
    @Excel(name = "托书列表权限类别")
    @ApiModelProperty(value = "托书列表权限类别")
    private String readType;

    /** 委托ZIH提货 0是 1否  2铁路到货 */
    @Excel(name = "委托ZIH提货 0是 1否  2铁路到货")
    @ApiModelProperty(value = "委托ZIH提货 0是 1否  2铁路到货")
    private String shipOrderBinningway;

    /** 是否由ZIH代理送货  0否 1是 */
    private String receiveOrderIspart;

    /** 是否有改单 */
    @Excel(name = "是否有改单")
    @ApiModelProperty(value = "是否有改单")
    private String dcGaidanState;

    /** 货品中文名称 （订单商品表）*/
    @Excel(name = "货品中文名称")
    @ApiModelProperty(value = "货品中文名称")
    private String goodsName;

    /** 业务员编码 */
    @Excel(name = "业务员编码")
    @ApiModelProperty(value = "业务员编码")
    private String ywNumber;

    /** 业务编码 */
    @Excel(name = "业务编码")
    @ApiModelProperty(value = "业务编码")
    private String clientYwNumber;

    /** 业务编码 */
    @Excel(name = "查询站点")
    @ApiModelProperty(value = "查询站点")
    private String[] siteCode;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "订单号 托书编号")
    private String orderNumber;

    /** 客户首次提交托书时间 */
    @Excel(name = "客户首次提交托书时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户首次提交托书时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjFTime;

    /** 客户每次托书提交时间 */
    @Excel(name = "客户每次托书提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "客户每次托书提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date tjTime;

    /** 集装箱箱型 */
    @Excel(name = "集装箱箱型")
    @ApiModelProperty(value = "集装箱箱型")
    private String containerType;

    /** 集装箱箱量 */
    @Excel(name = "集装箱箱量")
    @ApiModelProperty(value = "集装箱箱量")
    private String containerBoxamount;

    /** 体积 (订单商品表)*/
    @Excel(name = "体积(订单商品表)")
    @ApiModelProperty(value = "体积")
    private String goodsCbm;

    /** 重量 (订单商品表)*/
    @Excel(name = "重量")
    @ApiModelProperty(value = "重量")
    private String goodsKgs;

    /** 上货站 */
    @Excel(name = "上货站")
    @ApiModelProperty(value = "上货站")
    private String orderUploadsite;

    /** 上货编号 */
    private String orderUploadcode;

    /** 下货站 */
    @Excel(name = "下货站")
    @ApiModelProperty(value = "下货站")
    private String orderUnloadsite;

    /** 发货方 */
    @Excel(name = "发货方")
    @ApiModelProperty(value = "发货方")
    private String shipOrederName;

    /** 每日转待审核次数（每日两次） */
    @Excel(name = "每日转待审核次数", readConverterExp = "每=日两次")
    @ApiModelProperty(value = "每日转待审核次数")
    private Long turncount;

    /** 3个月内客户转待审核次数平均数 */
    @Excel(name = "3个月内客户转待审核次数平均数")
    @ApiModelProperty(value = "3个月内客户转待审核次数平均数")
    private String totalturncountavg;

    /** 1有修改记录0没有修改记录 */
    @Excel(name = "1有修改记录0没有修改记录")
    @ApiModelProperty(value = "1有修改记录0没有修改记录")
    private String isupdate;

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

    /** 操作类别*/
    @Excel(name = "操作类别:0审核 1编辑")
    @ApiModelProperty(value = "操作类别：0审核 1编辑")
    private String operateType;

    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "服务：0门到门 1门到站 2站到站 3站到门", readConverterExp = "委=托书编号")
    @ApiModelProperty(value = "服务：0门到门 1门到站 2站到站 3站到门")
    private String bookingService;

    /** 总体积*/
    @Excel(name = "总体积")
    @ApiModelProperty(value = "总体积")
    private String totalVolumn;

    /** 总重量 */
    @Excel(name = "总重量")
    @ApiModelProperty(value = "总重量")
    private String totalWeight;

    /** 是否标记 0否1是 */
    @Excel(name = "是否标记 0否1是")
    @ApiModelProperty(value = "是否标记 0否1是")
    private String isClientSign;

    /** 站点ID,回程入货通知书上堆场地址 */
    @Excel(name = "站点ID,回程入货通知书上堆场地址")
    @ApiModelProperty(value = "站点ID,回程入货通知书上堆场地址")
    private String stationid;

    public String userid;

    /** 询价结果id */
    @ApiModelProperty(value = "询价结果id")
    private String inquiryRecordId;

}
