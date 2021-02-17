package com.szhbl.project.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 中亚信息对象 busi_zy_info
 * 
 * @author dps
 * @date 2020-04-27
 */
@Data
public class BusiZyInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String costId;

    /** 货物状态表id */
    @Excel(name = "货物状态表id")
    private Long trackId;

    /** 货物状态表id */
    @Excel(name = "货物状态表id")
    @ApiModelProperty(value = "货物状态表id")
    private Long[] trackIdsArr;

    /** 订单id */
    @Excel(name = "订单id")
    private String orderId;

    /** 托书编号 */
    @Excel(name = "托书编号")
    private String orderNumber;

    /** 箱号 */
    @Excel(name = "箱号")
    private String xianghao;

    /** 箱型 */
    @Excel(name = "箱型")
    private String xiangxing;

    /** 入场日期 */
    @Excel(name = "入场日期")
    private String pxEntryDate;

    /** 入场时间 */
    @Excel(name = "入场时间")
    private String pxEntryTime;

    /** 进站日期 */
    @Excel(name = "进站日期")
    private String pxEnterCar;

    /** 进站时间 */
    @Excel(name = "进站时间")
    private String pxEnterLeadNumber;

    /** 箱管箱况 */
    @Excel(name = "箱管箱况")
    private String xgXiangkuang;

    /** 箱管是否维修 */
    @Excel(name = "箱管是否维修")
    private String xgIsrepair;

    /** 箱管是否更换铭牌 */
    @Excel(name = "箱管是否更换铭牌")
    private String xgIsmingpai;

    /** 箱管船级社证明 */
    @Excel(name = "箱管船级社证明")
    private String xgBoat;

    /** 箱管发送邮件 1已发送 0未发送 */
    @Excel(name = "箱管发送邮件 1已发送 0未发送")
    private String xgSend;

    /** 箱管审核 */
    @Excel(name = "箱管审核")
    private String xgCheck;

    /** 公路部是否办好票 */
    @Excel(name = "公路部是否办好票")
    private String roadIsbill;

    /** 公路部审核状态 */
    @Excel(name = "公路部审核状态")
    private String roadCheck;

    /** $column.columnComment */
    @Excel(name = "公路部审核状态", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "公路部审核状态")
    private String createuserid;

    /** $column.columnComment */
    @Excel(name = "公路部审核状态")
    private String createusername;

    /** 报关单证 0是，1否*/
    @Excel(name = "报关单证")
    private String baoguanDocu;

    /** 随车单证 0是，1否*/
    @Excel(name = "随车单证")
    private String suicheDocu;

    /** 运单 */
    @Excel(name = "运单")
    private String waybill;

    /** 付费代码 */
    @Excel(name = "付费代码")
    private String paystate;

    /** 中文收货人 */
    @Excel(name = "中文收货人")
    private String gdConsignee;

    /** 跟单 */
    @Excel(name = "跟单")
    private String gdRemark;

    /** 跟单审核状态 0是，1否*/
    @Excel(name = "跟单审核状态")
    private String gdCheck;

    /** 发运日期 */
    @Excel(name = "发运日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fayunDate;

    /** 发运状态 1已发运 0未发运 */
    @Excel(name = "发运状态 1已发运 0未发运")
    private String fayunState;

    /** 是否加固 */
    @Excel(name = "是否加固")
    private String isJg;

    /** 是否偏载 */
    @Excel(name = "是否偏载")
    private String isPz;

    /** 箱号更改备注 */
    @Excel(name = "箱号更改备注")
    private String editRemark;

    /** 箱损照片 */
    @Excel(name = "箱损照片")
    private String xgXsphoto;

    /** 箱损是否上传 */
    @Excel(name = "箱损是否上传")
    private String xgXsstate;

    /** 箱管部箱号审核状态 */
    @Excel(name = "箱管部箱号审核状态")
    private String xhCheck;

    /** 是否计划发运 */
    @Excel(name = "是否计划发运")
    private String isplaneddelivery;

    /** 草单 0已出 1未出 */
    @Excel(name = "草单 0已出 1未出")
    private String bgcaodan;

    /** 确认草单 0已确认 1未确认 */
    @Excel(name = "确认草单 0已确认 1未确认")
    private String bgcaodanConfirm;

    /** 单证提供 0已提供 1未提供 */
    @Excel(name = "单证提供 0已提供 1未提供")
    private String documentsGive;

    /** 重量核对 0无误 1异常 */
    @Excel(name = "重量核对 0无误 1异常")
    private String weightCheck;

    /** 报关进度 0布控 1放行 */
    @Excel(name = "报关进度 0布控 1放行")
    private String customsProcess;

    /** 拼箱备注 */
    @Excel(name = "拼箱备注")
    private String bgremark;

    /** 班列发运定义的实际班列号 */
    @Excel(name = "班列发运定义的实际班列号")
    private String classzyNo;
    private String classzyNoS;

    /** 二次进站备注 */
    @Excel(name = "二次进站备注")
    private String jz2Remark;

    /** 入场重量 */
    @Excel(name = "入场重量")
    private String rcWeight;

    /** 进站重量 */
    @Excel(name = "进站重量")
    private String jzWeight;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNumber;

    /** 二次进站异常数据 */
    @Excel(name = "二次进站异常数据")
    private String jz2data;

    /** $column.columnComment */
    @Excel(name = "二次进站异常数据")
    private String zhengbenGive;

    /** 异常返回时间 */
    @Excel(name = "异常返回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yichangDate;

    /** 公路部审核时间 */
    @Excel(name = "公路部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date roadtime;

    /** 跟单部审核时间 */
    @Excel(name = "跟单部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gdtime;

    /** 箱管部审核时间 */
    @Excel(name = "箱管部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date xgtime;

    /** 实际下货站跟单填写 */
    @Excel(name = "实际下货站跟单填写")
    private String realUnloadsite;

    /** 相关铭牌更换是否超期 1超期 0正常 */
    @Excel(name = "相关铭牌更换是否超期 1超期 0正常")
    private String mingpaiGh;

    /** 拼箱部正面吊测重结果 */
    @Excel(name = "拼箱部正面吊测重结果")
    private String cepianResult;

    /** 承运部门 */
    @Excel(name = "承运部门")
    private String transportDept;

    /** 是否长途车进站 0是 1否 */
    @Excel(name = "是否长途车进站 0是 1否")
    private String isjzLdbus;

    /** 不进站原因 */
    @Excel(name = "不进站原因")
    private String causeNojz;

    /** 公路部堆场到达重箱日志备注 */
    @Excel(name = "公路部堆场到达重箱日志备注")
    private String roadlogRemark;

    /** $column.columnComment */
    @Excel(name = "公路部堆场到达重箱日志备注")
    private String actualClassbh;

    /** 箱子位置 */
    @Excel(name = "箱子位置")
    private String xianghaoPlace;

    /** 是否申请代码 默认0否 1是 */
    @Excel(name = "是否申请代码 默认0否 1是")
    private String isApplynum;

    /** 箱重 */
    @Excel(name = "箱重")
    private Long containerKgs;

    /** 申请代码列数 */
    @Excel(name = "申请代码列数")
    private String lieshu;

    /** 关务状态（未报关，报关单放行，报关单查验，出区异常，出区异常已放行） */
    @Excel(name = "关务状态", readConverterExp = "未报关，报关单放行，报关单查验，出区异常，出区异常已放行")
    private String guanwustate;
    private String guanwustateS;

    /** 是否一体化 */
    @Excel(name = "是否一体化")
    private String isunify;

    /** 班列号操作时间 */
    @Excel(name = "班列号操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classzynoTime;

    /** 班列号修改记录 */
    @Excel(name = "班列号修改记录")
    private String classzynoRemark;

    /** 收货人中文 */
    @Excel(name = "收货人中文")
    private String consignee;

    /** 收货人（英/俄文） */
    @Excel(name = "收货人", readConverterExp = "英=/俄文")
    private String consigneeE;

    /** 还箱信息（箱属）俄语 */
    @Excel(name = "还箱信息", readConverterExp = "箱=属")
    private String boxBelong;

    /** 发货人（英文、俄文） */
    @Excel(name = "发货人", readConverterExp = "英=文、俄文")
    private String consignor;

    /** 发货人声明 */
    @Excel(name = "发货人声明")
    private String consignorstate;

    /** 货物税号 */
    @Excel(name = "货物税号")
    private String goodsnamehs;

    /** 最终到站名称及站编，运单第五栏 */
    @Excel(name = "最终到站名称及站编，运单第五栏")
    private String arrivalstation;

    /** 到站监管库信息（CBX仓库信息） */
    @Excel(name = "到站监管库信息（CBX仓库信息）")
    private String cbxwarehouse;

    /** 承运人区段车站代码 */
    @Excel(name = "承运人区段车站代码")
    private String carrierstationcode;

    /** 付费代码（俄文） */
    @Excel(name = "付费代码", readConverterExp = "俄=文")
    private String paymentcode;

    /** 供货合同号 */
    @Excel(name = "供货合同号")
    private String supplycontractno;

    /** 客户操作时间 */
    @Excel(name = "客户操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date zyunitTime;

    /** 运单件数 */
    @Excel(name = "运单件数")
    private String yundanNumber;

    /** 运单毛重 */
    @Excel(name = "运单毛重")
    private Double yundanWeight;

    /** 运单箱重 */
    @Excel(name = "运单箱重")
    private Double yundanXweight;

    /** 运单总重 */
    @Excel(name = "运单总重")
    private Double yundanTotalweight;

    /** 运单第五栏 专用线信息 */
    @Excel(name = "运单第五栏 专用线信息")
    private String privateline;

    /** 收货人（俄文） */
    @Excel(name = "收货人", readConverterExp = "俄=文")
    private String consigneeR;

    /** 发货人（俄文） */
    @Excel(name = "发货人", readConverterExp = "俄=文")
    private String consignorR;

    /** 货物中文品名 */
    @Excel(name = "货物中文品名")
    private String goodsnameC;

    /** 货物外文释义 */
    @Excel(name = "货物外文释义")
    private String goodsnameE;

    /** 货物中文品名及税号及外文释义（多个） */
    @Excel(name = "货物中文品名及税号及外文释义", readConverterExp = "多=个")
    private String goodsnames;

    /** 到站站编运单第五栏 */
    @Excel(name = "到站站编运单第五栏")
    private String arrivalstationhs;

    /** 运单第六栏国境口岸 */
    @Excel(name = "运单第六栏国境口岸")
    private String gjkouan;

    /** 运单第28栏办理海关及其他行政手续记载 */
    @Excel(name = "运单第28栏办理海关及其他行政手续记载")
    private String formalities;

    /** 箱子最大运营总重量 */
    @Excel(name = "箱子最大运营总重量")
    private Long maxTotalweight;

    /** 箱子最大载重 */
    @Excel(name = "箱子最大载重")
    private Long maxLoad;

    /** 箱型的IOS代码 */
    @Excel(name = "箱型的IOS代码")
    private String isoCode;

    /** 随车状态 */
    @Excel(name = "随车状态")
    private String suichestate;

    /** 随车审核人 */
    @Excel(name = "随车审核人")
    private String scOperator;

    /** 随车组操作时间 */
    @Excel(name = "随车组操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date scOperatime;

    /** 实际班列编号 */
    @Excel(name = "实际班列编号")
    private String actualclassBh;

    /** 关务备注 */
    @Excel(name = "关务备注")
    private String gwRemark;
    private String gwRemarkS;

    /** 票数 */
    @Excel(name = "票数")
    private String gwPiaoshu;

    /** 报关员 */
    @Excel(name = "报关员")
    private String gwOperator;

    /** 报关操作时间 */
    @Excel(name = "报关操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gwOperatime;

    /** 公路预计到场时间 */
    @Excel(name = "公路预计到场时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date planunloadtime;

    /** 运单负责人 */
    @Excel(name = "运单负责人")
    private String ydOperator;

    /** $column.columnComment */
    @Excel(name = "随车备注")
    private String scRemark;

    /** 项数个数 */
    @Excel(name = "项数个数")
    private String scXsnum;

    /** 项数超过20个，每超出一项10元人民币 */
    @Excel(name = "项数超过20个，每超出一项10元人民币")
    private String scXsnumfee;

    /** 税号个数 */
    @Excel(name = "税号个数")
    private String scShnum;

    /** 税号个数超过13个，每超出一个20美元 */
    @Excel(name = "税号个数超过13个，每超出一个20美元")
    private String scShnumfee;

    /** 拼箱进站导入的重量 */
    @Excel(name = "拼箱进站导入的重量")
    private String zxWeight;

    /** 多联审核，审核取消运单时间 */
    @Excel(name = "多联审核，审核取消运单时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dlChecktime;

    /** 封号 */
    @Excel(name = "封号")
    private String fenghao;

    /** 监管区更改后封号 */
    @Excel(name = "监管区更改后封号")
    private String gwfenghao;

    /** 施封号 */
    @Excel(name = "施封号")
    private String shifenghao;

    /** 公路部上传的办票货物品名 */
    @Excel(name = "公路部上传的办票货物品名")
    private String goodsname;

    /** 运单第一栏 发货人 （中文） */
    @Excel(name = "运单第一栏 发货人 ", readConverterExp = "中=文")
    private String consignorC;

    /** 封号更新时间 */
    @Excel(name = "封号更新时间", readConverterExp = "$column.readConverterExp()")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date fhtime;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    private String lineTypeid;

    /***监听大口岸系统***/
    //托书id：orderId
    //箱号
    private String container;
    //托书编号
    private String ordernumber;
    //////以托书为单位
    //托书随车负责人
    private String Areviewer_suiche;
    //托书随车备注
    private String Remark_suiche;
    //创建时间
    private Date CreateTime;
    //////以箱号为单位
    //箱号随车状态(0已审核 1已审核特殊单证 2已审核项目多 3未审核特殊单证)
    private String ConStatus_suiche;
    //箱号随车审核人
    private String ConAreviewer_suiche;
    //随车操作时间 scOperatime
    //随车备注
    private String ConRemark_suiche;
    //运单审核状态 (0未审核1已填写2已审核)
    private String is_yundanSate;
    //运单审核时间、取消审核时间
    private Date dl_checktime;
    //运单负责人
    private String yd_Operator;

    /***监听关务系统***/
    //托书id
    private String order_ID;
    //箱号 xianghao
    //////以托书为单位
    //报关负责人
    private String declarant;
    //托书报关票数
    private String DeclareCount;
    //托书报关状态（默认0：单证未提供，1：草单未出，2：草单未确认，3：正本未提供，4.重箱未进站，5.正在报关，6.报关单放行，7.报关单查验，8.查验异常正处理，9.查验已放行，10.出区异常待放行，11.出区异常已放行 ）
    private String declareStatus;
    //托书报关备注
    private String DeclareRemark;
    //////以箱号为单位
    //报关单证
    private String Declareisready_gd;
    //随车单证
    private String Suicheisready_gd;
    //跟单部审核状态
    private String PaicangExamine_gd;
    //跟单部审核时间
    private String PaicangExaminedate_gd;
    //跟单审核备注
    private String PaicangRemark_gd;
    //关务状态
    // (去程0：单证未提供，13单证已提供，14正在审单，1：草单未出，2：草单未确认，3：正本未提供，4.重箱未进站，5.正在报关，6.报关单放行，7.报关单查验，8.查验异常待处理，9.查验已放行，10.出区异常待处理，11.出区异常已放行 ，12正本已提供)
    //（回程0.单据未提供、1.单证已提供、2.正在审单、3.问题未回复、4.草单未确认、5.正本未提供、6.具备清关条件、7.转关提前申报未出号、8.申报未出号、9.转关提前申报出号未缴税、10.出号未缴税、11.保证金未审批、12.暂进未审批、13.转关提前申报报关单查验、14.报关单查验、15.转关提前申报海关系统已放行、16.海关系统已放行、17.海关系统已放行（目的地查验）18.ATA未核注、19.ATA已核注）
    private String containerdeclareStatus;
    //票数
    private String containerDeclareCount;
    //关务备注
    private String containerDeclareRemark;
    //报关操作时间  gwOperatime
    //监管区更改后封号
    private String declare_fenghao;



    /***监听箱管系统***/
    //箱子最大运营总重量 maxTotalweight
    //箱子最大载重 maxLoad
    //箱型的IOS代码 isoCode
    //箱重 containerKgs

    /***监听箱行亚欧系统***/
    //公路预计到场时间  private Date planunloadtime;
    //封号 private String fenghao;
    //封号更新时间 private Date fhtime;
    //施封号  private String shifenghao;
    //公路部上传的办票货物品名 private String goodsname;






}
