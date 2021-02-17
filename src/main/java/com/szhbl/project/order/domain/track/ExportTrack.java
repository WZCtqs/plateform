package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExportTrack {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "舱位号")
    @ApiModelProperty(value = "舱位号")
    private String orderNumber;

    /** 箱号 */
    @Excel(name = "箱号")
    @ApiModelProperty(value = "箱号")
    private String boxNum;

    /** 发运日期 */
    @Excel(name = "发运日期", width = 30, dateFormat = "yyy-MM-dd")
    @ApiModelProperty(value = "发运日期")
    private Date actualClassDateValue;

    /** 发运日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueStart;

    /** 发运日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualClassDateValueEnd;

    /** 实际班列号 */
    @Excel(name = "实际班列号")
    @ApiModelProperty(value = "是否加开 非P'' 是P")
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
    @Excel(name = "班列号维护时间", width = 30, dateFormat = "yyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "班列号维护时间")
    private Date classzynoTime;

    /** 货物品名 */
    @Excel(name = "货物品名")
    @ApiModelProperty(value = "货物品名")
    private String goodsName;

    /** 跟单员 */
    @Excel(name = "跟单员")
    @ApiModelProperty(value = "东向跟单员")
    private String orderMerchandiser;

    /** 跟单员 */
    @ApiModelProperty(value = "西向跟单员")
    private String orderMerchandiserW;

    /** 箱量 */
    @Excel(name = "箱量")
    @ApiModelProperty(value = "箱量")
    private String containerBoxamount;

    /** 箱型 */
    @Excel(name = "箱型")
    @ApiModelProperty(value = "箱型")
    private String containerType;

    /**  入场日期*/
    @Excel(name = "入场日期", width = 30, dateFormat = "yyy-MM-dd")
    @ApiModelProperty(value = "入场日期")
    private Date inSpaceDate;

    /**  入场时间*/
    @Excel(name = " 入场时间")
    @ApiModelProperty(value = "入场时间")
    private String inSpaceTime;

    /** 拼箱部正面吊测重结果 */
    @Excel(name = "正面吊测偏结果")
    private String cepianResult;

    /**  进站日期*/
    @Excel(name = "进站日期", width = 30, dateFormat = "yyy-MM-dd")
    @ApiModelProperty(value = "进站日期")
    private Date inStationDate;

    /**  进站时间*/
    @Excel(name = " 进站时间")
    @ApiModelProperty(value = "进站时间")
    private String inStationTime;

    /** 实际发运日期 */
    @ApiModelProperty(value = "实际发运日期 ")
    private String actualClassDate;

    /** 是否一体化 */
    @ApiModelProperty(value = "是否一体化")
    private String isunify;

    /** 关务状态 */
    @Excel(name = "关务状态")
    private String guanwustate;

    /** 托书报关状态 */
    @Excel(name = "托书报关状态")
    private String bgProgress;

    /** 跟单备注 */
    @Excel(name = "跟单备注")
    private String gdRemark;

    /** 班列日期 */
    @Excel(name = "班列日期", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 箱号校验 */
    @Excel(name = "箱号校验")
    @ApiModelProperty(value = "箱号校验")
    private String boxNumCheck;

    /** 备注 */
    @Excel(name = "箱号备注")
    @ApiModelProperty(value = "箱号备注")
    private String remark;

    /** 订舱方 */
    @Excel(name = "订舱方")
    @ApiModelProperty(value = "订舱方")
    private String clientUnit;

    /** 委托ZIH提货 0是 1否  2铁路到货 */
    @Excel(name = "到货方式")
    @ApiModelProperty(value = "0委托ZIH提货 1自送货  2铁路到货")
    private String shipOrderBinningway;

    /** 税号 （国外报关）*/
    @Excel(name = "税号")
    @ApiModelProperty(value = "税号（国外报关）")
    private String goodsReport;

    /** 税号 (国内报关)*/
    @ApiModelProperty(value = "税号 (国内报关)")
    private String goodsInReport;

    /** 重量 */
    @Excel(name = "总重")
    @ApiModelProperty(value = "总重")
    private String goodsKgs;

    /** 箱重 */
    @Excel(name = "箱重")
    @ApiModelProperty(value = "箱重")
    private String boxWeight;

    /** 下货站 */
    @Excel(name = "下货站")
    @ApiModelProperty(value = "下货站")
    private String orderUnloadsite;

    /** 是否申请代码  0否 1是 */
    @Excel(name = "是否申请代码")
    @ApiModelProperty(value = "是否申请代码")
    private String isApplyCode;

    /** 是否偏载 */
    @Excel(name = "是否偏载")
    @ApiModelProperty(value = "是否偏载")
    private String isBalance;

    /** 是否加固 */
    @Excel(name = "是否加固")
    @ApiModelProperty(value = "是否加固")
    private String isStable;

    /** 箱况*/
    @Excel(name = "箱况")
    @ApiModelProperty(value = "箱况")
    private String xk;

    /** 是否维修*/
    @Excel(name = "是否维修")
    @ApiModelProperty(value = "是否维修")
    private String wx;

    /** 是否更换铭牌*/
    @Excel(name = "是否更换铭牌")
    @ApiModelProperty(value = "是否铭牌")
    private String mp;

    /** 船级社证明*/
    @Excel(name = "船级社证明")
    @ApiModelProperty(value = "船级社证明")
    private String cjszm;

    /** 报关单证*/
    @Excel(name = "报关单证")
    @ApiModelProperty(value = "报关单证")
    private String bgdz;

    /** 随车单证*/
    @Excel(name = "随车单证")
    @ApiModelProperty(value = "随车单证")
    private String scdz;

    /** 运单*/
    @Excel(name = "运单")
    @ApiModelProperty(value = "运单")
    private String yd;

    /** 中文收货人名称*/
    @Excel(name = "中文收货人名称")
    @ApiModelProperty(value = "中文收货人名称")
    private String zwshrmc;

    /** 是否办好票*/
    @Excel(name = "是否办好票")
    @ApiModelProperty(value = "是否办好票")
    private String sfbhp;

    /** 箱管部审核状态*/
    @Excel(name = "箱管部审核状态")
    @ApiModelProperty(value = "箱管部审核状态")
    private String xgbshzt;

    /** 箱管部审核时间 */
    @Excel(name = "箱管部审核时间", width = 30, dateFormat = "yyy-MM-dd HH:mm:ss")
    private Date xgtime;

    /** 跟单部审核状态*/
    @Excel(name = "跟单部审核状态")
    @ApiModelProperty(value = "跟单部审核状态")
    private String gdbshzt;

    /** 跟单部审核时间 */
    @Excel(name = "跟单部审核时间", width = 30, dateFormat = "yyy-MM-dd HH:mm:ss")
    private Date gdtime;

    /** 公路部审核状态*/
    @Excel(name = "公路部审核状态")
    @ApiModelProperty(value = "公路部审核状态")
    private String glbshzt;

    /** 公路部审核时间 */
    @Excel(name = "公路部审核时间", width = 30, dateFormat = "yyy-MM-dd HH:mm:ss")
    private Date roadtime;

    /** 发送邮件*/
    @Excel(name = "发送邮件")
    @ApiModelProperty(value = "发送邮件")
    private String fsyj;

    /** 箱损上传*/
    @Excel(name = "箱损上传")
    @ApiModelProperty(value = "箱损上传")
    private String xssc;

    /** 客户推荐人 */
    @Excel(name = "推荐人")
    @ApiModelProperty(value = "推荐人")
    private String clientTjr;

    /** 报关状态*/
    @ApiModelProperty(value = "报关状态")
    private String bgzt;

    /** 状态表箱型 */
    @ApiModelProperty(value = "状态表箱型 ")
    private String boxType;

    /** 班列编号 */
    private String classBh;

    /** 上货站 */
    private String orderUploadsite;

    /** 实际班列id */
    private String actualClassId;

    /** 订单id */
    private String orderId;

    /** 计费体积 */
    private String goodsVolume;

    /** 货物是否上车0是1否 */
    private Integer isGetin;

    /** 是否正常箱0是1否 */
    private Integer isNormal;

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
    
    /** 体积 */
    private String goodsCbm;

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

}
