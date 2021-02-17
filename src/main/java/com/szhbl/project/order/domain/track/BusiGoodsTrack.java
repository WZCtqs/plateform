package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class BusiGoodsTrack {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    private Long[] idsArr;

    private String[] orderIdsArr;

    /** 实际班列id */
    @Excel(name = "实际班列id")
    @ApiModelProperty(value = "实际班列id")
    private String actualClassId;

    /** 订单id */
    @Excel(name = "订单id")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 箱号 */
    @Excel(name = "箱号")
    @ApiModelProperty(value = "箱号")
    private String boxNum;

    /** 计费体积 */
    @Excel(name = "计费体积")
    @ApiModelProperty(value = "计费体积")
    private String goodsVolume;

    /** 货物是否上车0是1否 */
    @Excel(name = "货物是否上车0是1否")
    @ApiModelProperty(value = "货物是否上车0是1否")
    private Integer isGetin;

    /** 实际班列日期 */
    @Excel(name = "实际班列日期(日期+是否加开字符串)")
    @ApiModelProperty(value = "实际班列日期(日期+是否加开字符串)")
    private String actualClassDate;

    /** 实际班列日期 */
    @Excel(name = "实际发运日期")
    @ApiModelProperty(value = "实际发运日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValue;

    /** 实际发运日期 */
    @Excel(name = "实际发运日期开始")
    @ApiModelProperty(value = "实际发运日期开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueStart;

    /** 实际发运日期 */
    @Excel(name = "实际发运日期结束")
    @ApiModelProperty(value = "实际发运日期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueEnd;

    /** 是否申请代码 */
    @Excel(name = "是否加开 非P'' 是P")
    @ApiModelProperty(value = "是否加开 非P'' 是P")
    private String isClassAdd;

    /** 是否正常箱0是1否 */
    @Excel(name = "是否正常箱0是1否")
    @ApiModelProperty(value = "是否正常箱0是1否")
    private Integer isNormal;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 是否申请代码  0否 1是 */
    @Excel(name = "是否申请代码  0否 1是")
    @ApiModelProperty(value = "是否申请代码  0否 1是")
    private String isApplyCode;

    /** 删除标志0正常1删除 */
    @Excel(name = "删除标志0正常1删除 ")
    @ApiModelProperty(value = "删除标志0正常1删除 ")
    private Integer delFlag;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /** 更新者 */
    @Excel(name = "更新者")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "订单号（舱位号）", readConverterExp = "委托书编号")
    @ApiModelProperty(value = "托书编号 （舱位号）")
    private String orderNumber;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDate;

    /** 班列日期 */
    @Excel(name = "班列日期开始", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateStart;

    /** 班列日期 */
    @Excel(name = "班列日期结束", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列日期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateEnd;

    /** 班列编号 */
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    private String classBh;

    /** 0为去程 1为回程 */
    @Excel(name = "0为去程 1为回程")
    @ApiModelProperty(value = "0为去程 1为回程")
    private String classEastandwest;

    /** 线路类型：0中欧 2中亚 3中越 4中俄 */
    @Excel(name = "线路类型：0中欧 2中亚 3中越 4中俄")
    @ApiModelProperty(value = "线路类型：0中欧 2中亚 3中越 4中俄")
    private String lineTypeid;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = " 整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String isconsolidation;

    /**  发运状态 0未发运 1已发运*/
    @Excel(name = " 发运状态 0未发运 1已发运")
    @ApiModelProperty(value = "发运状态 0未发运 1已发运")
    private String isRun;

    /**  运踪状态：在堆场/已进站*/
    @Excel(name = " 运踪状态：0在堆场 1已进站")
    @ApiModelProperty(value = "运踪状态：0在堆场 1已进站")
    private String trackState;

    /**  入场日期*/
    @Excel(name = "入场日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "入场日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inSpaceDate;

    /**  入场时间*/
    @Excel(name = " 入场时间")
    @ApiModelProperty(value = "入场时间")
    private String inSpaceTime;

    /**  进站日期*/
    @Excel(name = "进站日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "进站日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inStationDate;

    /**  进站时间*/
    @Excel(name = " 进站时间")
    @ApiModelProperty(value = "进站时间")
    private String inStationTime;

    /** 箱号校验） 0正确 1错误*/
    @Excel(name = "箱号校验", readConverterExp = "箱号校验")
    @ApiModelProperty(value = "箱号校验 0正确 1错误")
    private String boxNumCheck;

    /** 上货站 */
    @Excel(name = "上货站")
    @ApiModelProperty(value = "上货站")
    private String orderUploadsite;

    /** 下货站 */
    @Excel(name = "下货站")
    @ApiModelProperty(value = "下货站")
    private String orderUnloadsite;

    /** 订舱方名称 */
    @Excel(name = "订舱方名称")
    @ApiModelProperty(value = "订舱方名称")
    private String clientUnit;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    @ApiModelProperty(value = "客户推荐人")
    private String clientTjr;

    /** 跟单姓名 */
    @Excel(name = "跟单姓名")
    @ApiModelProperty(value = "跟单姓名")
    private String orderMerchandiser;

    /** 集装箱箱量 */
    @Excel(name = "托书箱量")
    @ApiModelProperty(value = "托书箱量")
    private String containerBoxamount;

    /** 集装箱箱型 */
    @Excel(name = "托书箱型")
    @ApiModelProperty(value = "托书箱型")
    private String containerType;

    /** 重量 */
    @Excel(name = "托书重量")
    @ApiModelProperty(value = "托书重量")
    private String goodsKgs;

    /** 体积 */
    @Excel(name = "托书体积")
    @ApiModelProperty(value = "托书体积")
    private String goodsCbm;

    /** 托书品名 */
    @Excel(name = "托书品名")
    @ApiModelProperty(value = "托书品名")
    private String goodsName;

    /** 20尺箱子 */
    @Excel(name = "20尺箱子")
    @ApiModelProperty(value = "20尺箱子")
    private String boxAmountSecond;

    /** 40尺箱子 */
    @Excel(name = "40尺箱子")
    @ApiModelProperty(value = "40尺箱子")
    private String boxAmountFour;

    /** 45尺箱子 */
    @Excel(name = "45尺箱子")
    @ApiModelProperty(value = "45尺箱子")
    private String boxAmountFive;

    /** 总体积*/
    @Excel(name = "总体积")
    @ApiModelProperty(value = "总体积")
    private String totalVolumn;

    /** 总重量 */
    @Excel(name = "总重量")
    @ApiModelProperty(value = "总重量")
    private String totalWeight;

    /** 公路部审核时间 */
    @Excel(name = "公路部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "公路部审核时间")
    private Date roadtime;

    /** 公路部审核状态 */
    private String roadCheck;

    /** 跟单部审核时间 */
    @Excel(name = "跟单部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "跟单部审核时间")
    private Date gdtime;

    /** 跟单审核状态 0是，1否*/
    private String gdCheck;

    /** 跟单备注 */
    private String gdRemark;

    /** 箱管部审核时间 */
    @Excel(name = "箱管部审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "箱管部审核时间")
    private Date xgtime;

    /** 箱管部审核状态 */
    private String xgCheck;

    /** 班列发运定义的实际班列号 */
    @Excel(name = "班列发运定义的实际班列号")
    private String classzyNo;

    /** 班列号操作时间 */
    @Excel(name = "班列号操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classzynoTime;

    /** 申请代码列数 */
    @Excel(name = "申请代码列数")
    private String lieshu;

    /** 关务状态（未报关，报关单放行，报关单查验，出区异常，出区异常已放行） */
    @Excel(name = "关务状态", readConverterExp = "未报关，报关单放行，报关单查验，出区异常，出区异常已放行")
    private String guanwustate;
    private List<Map> guanwustateList;

    /** 班列号修改记录 */
    @Excel(name = "班列号修改记录")
    private String classzynoRemark;

    /** 报关负责人 */
    @Excel(name = "报关负责人")
    private String bgPrincipal;

    /** 托书报关票数 */
    @Excel(name = "托书报关票数")
    private String bgVotesNumber;

    /** 托书报关状态 */
    @Excel(name = "托书报关状态")
    private String bgProgress;
    private List<Map> bgProgressList;

    /** 托书报关备注 */
    @Excel(name = "托书报关备注")
    private String bgRemark;

    /** 托书随车负责人 */
    @Excel(name = "随车负责人")
    private String scPrincipal;

    /** 托书随车备注 */
    @Excel(name = "托书随车备注")
    private String scremark;

    /** 随车状态 */
    private String suichestate;

    /** 随车审核人 */
    private String scOperator;

    /** 随车备注 */
    private String scRemarkXh;

    /** 封号 */
    private String fenghao;

    /** 封号更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date fhtime;

    /** 监管区更改后封号 */
    private String gwfenghao;

    /** 托书状态 */
    private String isexamline;

    /**登录者部门编号*/
    private String deptCode;

    /** 用户id*/
    public String userid;

    /** 中亚托书列表权限类别*/
    private String readType;

    /** 拼箱部正面吊测重结果 */
    private String cepianResult;

}
