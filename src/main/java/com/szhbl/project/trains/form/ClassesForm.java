package com.szhbl.project.trains.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClassesForm {
    /** 班列id，主键 */
    @ApiModelProperty(value = "班列id")
    private Long classId;

    /** 班列编号 */
    @Excel(name = "班列编号")
    @ApiModelProperty(value = "班列编号")
    @NotNull(message = "班列编号不能为空")
    private String classBh;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型：0是中欧2是中亚3是中越4是中俄")
    @NotNull(message = "线路类型不能为空")
    private Long lineTypeid;

    /** 线路ID */
    @Excel(name = "线路ID")
    @ApiModelProperty(value = "线路ID")
    @NotNull(message = "线路ID不能为空")
    private String lineId;

    /** 班列号 */
    @Excel(name = "班列号")
    @ApiModelProperty(value = "班列号")
    @NotNull(message = "班列号不能为空")
    private String classNumber;

    /** 开行班列 */
    @Excel(name = "开行班列")
    @ApiModelProperty(value = "开行班列")
    private String classBlocktrain;

    /** 班列类型 */
    @Excel(name = "班列类型")
    @ApiModelProperty(value = "班列类型")
    private String classClasstype;

    /** 0为去(西向) 1为回(东向） */
    @Excel(name = "班列往返：0去(西向) 1回(东向)")
    @ApiModelProperty(value = "班列往返：0去(西向) 1回(东向)")
    @NotNull(message = "班列往返方向不能为空")
    private String classEastandwest;

    /** 始发站编号 */
    @Excel(name = "始发站编号")
    @ApiModelProperty(value = "始发站编号")
    @NotNull(message = "始发站编号不能为空")
    private String classStationofdeparture;

    /** 目的站编号 */
    @Excel(name = "目的站编号")
    @ApiModelProperty(value = "目的站编号")
    @NotNull(message = "目的站编号不能为空")
    private String classStationofdestination;

    /** 始发站名称 */
    @Excel(name = "始发站名称")
    @ApiModelProperty(value = "始发站名称")
    private String classStationofdepartureName;

    /** 目的站名称 */
    @Excel(name = "目的站名称")
    @ApiModelProperty(value = "目的站名称")
    private String classStationofdestinationName;

    /** 运行时间 */
    @Excel(name = "运行时间")
    @ApiModelProperty(value = "运行时间")
    @NotNull(message = "运行时间不能为空")
    private String classTransporttime;

    /** 班列是否接受申请 1是 0否 */
    @Excel(name = "班列是否接受申请(是否发布) 1是 0否")
    @ApiModelProperty(value = "班列是否接受申请(是否发布) 1是 0否")
    private String ispublicly;

    /** 舱位总数 */
    @Excel(name = "舱位总数")
    @ApiModelProperty(value = "舱位总数")
    private Long classSpacenumber;

    /** 整箱舱位数 */
    @Excel(name = "整箱舱位数")
    @ApiModelProperty(value = "整箱舱位数")
    private Long zxcnt;

    /** 拼箱状态（0是未满1是已满） */
    @Excel(name = "拼箱状态", readConverterExp = "0=是未满1是已满")
    @ApiModelProperty(value = "拼箱状态：0=是未满1是已满")
    @NotNull(message = "拼箱状态不能为空")
    private String pxstate;

    /** 拼箱体积数 */
    @Excel(name = "拼箱体积数")
    @ApiModelProperty(value = "拼箱体积数")
    private Long pxcnt;

    /** 拼箱重量数 */
    @Excel(name = "拼箱重量数")
    @ApiModelProperty(value = "拼箱重量数")
    private Long pxkgs;

    /** 计划开车时间 */
    @Excel(name = "计划开车时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "计划开车时间")
    @NotNull(message = "计划开车时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classStime;

    /** 计划到站时间 */
    @Excel(name = "计划到站时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "计划到站时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classEtime;

    /** 车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列 */
    @Excel(name = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列")
    @ApiModelProperty(value = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列")
    @NotNull(message = "车辆状态不能为空")
    private String classState;

    /** 整柜随机接货站点编号 */
    @Excel(name = "整柜随机接货站点编号")
    @ApiModelProperty(value = "整柜随机接货站点编号，英文逗号隔开")
    private String receiveSiteFull;

    /** 拼箱随机接货站点编号 */
    @Excel(name = "拼箱随机接货站点编号")
    @ApiModelProperty(value = "拼箱随机接货站点编号，英文逗号隔开")
    private String receiveSiteLcl;

    /** 创建时间 */
    @Excel(name = "班列记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "班列记录创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    @ApiModelProperty(value = "创建用户ID")
    @NotNull(message = "创建用户ID不能为空")
    private String createuserid;

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    @ApiModelProperty(value = "创建用户姓名")
    @NotNull(message = "创建用户姓名不能为空")
    private String createusername;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 费用是否可修改 0可修改 1不可修改（默认可修改） */
    @ApiModelProperty(value = "费用是否可修改 0可修改 1不可修改（默认可修改）")
    private Long costentryState;

    /** 当天录运踪的时间（每天都在更新） */
    @Excel(name = "当天录运踪的时间", readConverterExp = "每=天都在更新")
    @ApiModelProperty(value = "当天录运踪的时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String distributionTime;

    /** 控制结算模块箱号录入节点 0可修改 1不可修改（默认可修改） */
    @Excel(name = "控制结算模块箱号录入节点 0可修改 1不可修改", readConverterExp = "默=认可修改")
    @ApiModelProperty(value = "控制结算模块箱号录入节点 0可修改 1不可修改")
    private Long xhentryState;

    /** 管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改（默认不可修改） */
    @Excel(name = "管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改", readConverterExp = "默=认不可修改")
    @ApiModelProperty(value = "管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改（默认不可修改）")
    private Long pxentryState;

    /** 美元转人民币 */
    @Excel(name = "美元转人民币")
    @ApiModelProperty(value = "美元转人民币")
    private Double usdtormbe;

    /** 欧元转人民币 */
    @Excel(name = "欧元转人民币")
    @ApiModelProperty(value = "欧元转人民币")
    private Double eurtormbe;

    /** 欧元转美金 */
    @Excel(name = "欧元转美金")
    @ApiModelProperty(value = "欧元转美金")
    private Double euttousde;

    /** 卢布转人民币 */
    @Excel(name = "卢布转人民币")
    @ApiModelProperty(value = "卢布转人民币")
    private Double rurtormbe;

    /** 整柜随机接货站点编号 */
    @Excel(name = "整柜随机接货站点编号数组")
    private String[] receiveSiteFullArr;

    /** 拼箱随机接货站点编号 */
    @Excel(name = "拼箱随机接货站点编号数组")
    private String[] receiveSiteLclArr;
}
