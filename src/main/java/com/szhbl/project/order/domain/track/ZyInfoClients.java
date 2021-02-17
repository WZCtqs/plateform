package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.project.client.VO.ProblemFileVo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ZyInfoClients {

    /***************托书列表*******************/
    /** 序号 */
    private int sort;

    /** 托书id */
    private String orderId;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date classDate;

    /** 班列日期开始 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date classDateStart;

    /**
     * 班列日期结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date classDateEnd;

    /**
     * 班列编号
     */
    private String orderClassBh;

    /**
     * 订单号（委托书编号）（舱位号）
     */
    private String orderNumber;

    private String classEastandwest;

    /**
     * 箱号
     */
    private String boxNumbers;

    /**
     * 集装箱箱型
     */
    private String containerType;

    /**
     * 集装箱箱量
     */
    private String containerBoxamount;

    /** 货品中文名称 */
    private String goodsName;

    /** 下货站 */
    private String orderUnloadsite;

    /** 已编辑箱号个数 */
    private int boxEditNumber;

    /** 订舱方ID */
    private String clientId;

    /***************中亚列表*******************/
    /** 箱号 */
    private String xianghao;

    /** 箱号校验） 0正确 1错误*/
    private String boxNumCheck;

    /** 收货人中文 */
    private String consignee;

    /** 还箱信息（箱属）俄语 */
    private String boxBelong;

    /** 到站监管库信息（CBX仓库信息） */
    private String cbxwarehouse;

    /** 最终到站名称及站编，运单第五栏 */
    private String arrivalstation;

    /** 承运人区段车站代码 */
    private String carrierstationcode;

    /** 付费代码（俄文） */
    private String paymentcode;

    /** 是否申请代码 默认0否(0未上传1已上传2已审核) */
    private String isApplynum;

    /** 随车状态 busi_zy_info*/
    private String suichestate;

    /** 是否可编辑 默认0可编辑 1不可编辑 */
    private String isEdit;

    /** 操作状态  */
    private String isOperate;

    /** 拼箱传过来箱型 */
    private String boxType;

    /***************中亚信息上传*******************/
    private String costId;

    /** 运单第一栏 发货人 （中文） */
    private String consignorC;

    /** 发货人（英文、俄文） */
    @Excel(name = "发货人", readConverterExp = "英=文、俄文")
    private String consignor;

    /** 发货人（俄文） */
    @Excel(name = "发货人", readConverterExp = "俄=文")
    private String consignorR;

    /** 还箱信息（箱属）俄语 */

    /** 收货人中文 */

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

    /** 到站监管库信息（CBX仓库信息） */

    /** 承运人区段车站代码 */

    /** 付费代码（俄文） */

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    private String createuserid;

    private String createusername;

    //平台端中亚运单列表
    /** 运单负责人 */
    private String ydOperator;

    /** 发运日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date actualClassDateValue;

    /** 订舱方 */
    private String clientUnit;

    /** 客户推荐人 */
    private String clientTjr;

    /** 跟单姓名 */
    private String orderMerchandiser;

    /** 多联审核，审核取消运单时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dlChecktime;

    /**
     * 计划班列号
     */
    private String classzyNo;

    /**
     * 封号
     */
    private String fenghao;

    /*船级证书*/
    private String ccUrl;

    private String ccFileName;

    // 装箱照
    private List<ProblemFileVo> boxPhotos;
    //铅封号
    private String sealnumber;
    //托书整拼箱
    private String isConsolidation;
    //是否委zih提货
    private String shipOrderBinningWay;
    //箱号审核失败原因
    private String conSealFail;
    //装箱照审核结果（0：待审核，1：审核失败，2：审核成功）
    private Integer conSealStatus;
    //装箱照审核失败原因
    private String boxingphotoFail;
    //装箱照审核结果（0：待审核，1：审核失败，2：审核成功）
    private Integer boxingphotoStatus;
    //单证表id
    private Long docId;

}
