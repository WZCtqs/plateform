package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GoodsTrackMq {
    /** 订单id */
    private String orderId;

    /** 箱号 */
    private String boxNum;

    /** 是否申请代码 默认0否(0未上传1已上传2已审核) */
    private String isApplynum;

    /** 实际发运日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValue;

    /** 实际班列号 是否加开 非：'' 是：P*/
    private String isClassAdd;

    /** 班列发运定义的实际班列号 */
    private String classzyNo;

    /** 班列号操作时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classzynoTime;

    /** 申请代码列数 */
    private String lieshu;

    /** 实际班列日期 */
    private String actualClassDate;

    /** 班列号修改记录 */
    private String classzynoRemark;

    //中亚运单
    /** 运单第一栏 发货人 （中文） */
    private String consignorC;

    /** 发货人（英文、俄文） */
    @Excel(name = "发货人", readConverterExp = "英=文、俄文")
    private String consignor;

    /** 发货人（俄文） */
    @Excel(name = "发货人", readConverterExp = "俄=文")
    private String consignorR;

    /** 还箱信息（箱属）俄语 */
    private String boxBelong;

    /** 收货人中文 */
    private String consignee;

    /** 收货人（英/俄文） */
    @Excel(name = "收货人", readConverterExp = "英=/俄文")
    private String consigneeE;

    /** 收货人（俄文） */
    @Excel(name = "收货人", readConverterExp = "俄=文")
    private String consigneeR;

    /** 运单第五栏 专用线信息 */
    @Excel(name = "运单第五栏 专用线信息")
    private String privateline;

    /** 运单第六栏国境口岸 */
    @Excel(name = "运单第六栏国境口岸")
    private String gjkouan;

    /** 货物中文品名 */
    @Excel(name = "货物中文品名")
    private String goodsnameC;

    /** 货物外文释义 */
    @Excel(name = "货物外文释义")
    private String goodsnameE;

    /** 货物税号 */
    @Excel(name = "货物税号")
    private String goodsnamehs;

    /** 货物中文品名及税号及外文释义（多个） */
    @Excel(name = "货物中文品名及税号及外文释义", readConverterExp = "多=个")
    private String goodsnames;

    /** 运单件数 */
    @Excel(name = "运单件数")
    private String yundanNumber;

    /** 运单毛重 */
    @Excel(name = "运单毛重")
    private Double yundanWeight;

    /** 运单箱重 */
    @Excel(name = "运单箱重")
    private Double yundanXweight;

    /** 运单第28栏办理海关及其他行政手续记载 */
    @Excel(name = "运单第28栏办理海关及其他行政手续记载")
    private String formalities;

    /** 发货人声明 */
    @Excel(name = "发货人声明")
    private String consignorstate;

    /** 最终到站名称及站编，运单第五栏 */
    private String arrivalstation;

    /** 到站监管库信息（CBX仓库信息） */
    private String cbxwarehouse;

    /** 承运人区段车站代码 */
    private String carrierstationcode;

    /** 付费代码（俄文） */
    private String paymentcode;

    /** 供货合同号 */
    @Excel(name = "供货合同号")
    private String supplycontractno;

    /** 客户操作时间 */
    @Excel(name = "客户操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date zyunitTime;

    /** 运单总重 */
    @Excel(name = "运单总重")
    private Double yundanTotalweight;

    /** 到站站编运单第五栏 */
    @Excel(name = "到站站编运单第五栏")
    private String arrivalstationhs;
}
