package com.szhbl.project.trains.form;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClassesTemplateForm {
    /** $column.columnComment */
    private String classTId;

    /** $column.columnComment 班列号*/
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "班列号")
    @NotNull(message = "班列号不能为空")
    private String classTNumber;

    /** 开行班列 */
    @Excel(name = "开行班列中文")
    @ApiModelProperty(value = "开行班列中文")
    @NotNull(message = "开行班列中文不能为空")
    private String classTBlocktrainCn;

    /** 开行班列 */
    @Excel(name = "开行班列英文")
    @ApiModelProperty(value = "开行班列英文")
    @NotNull(message = "开行班列英文不能为空")
    private String classTBlocktrainEn;

    /** $column.columnComment */
    @Excel(name = "班列类型")
    @ApiModelProperty(value = "班列类型")
    @NotNull(message = "班列类型不能为空")
    private String classTClasstype;

    /** 0往 1返 */
    @Excel(name = "0往 1返")
    @ApiModelProperty(value = "方向：0往 1返")
    @NotNull(message = "方向不能为空")
    private String classTEastandwest;

    /** 始发站代码 */
    @Excel(name = "始发站代码")
    @ApiModelProperty(value = "始发站代码")
    @NotNull(message = "始发站代码不能为空")
    private String classTStationofdepartureCode;

    /** 始发站 */
    @Excel(name = "始发站")
    @ApiModelProperty(value = "始发站")
    private String classTStationofdeparture;

    /** 始发站英文 */
    @Excel(name = "始发站英文")
    @ApiModelProperty(value = "始发站英文")
    private String classTStationofdepartureEn;

    /** 目的站代码 */
    @Excel(name = "目的站代码")
    @ApiModelProperty(value = "目的站代码")
    @NotNull(message = "目的站代码不能为空")
    private String classTStationofdestinationCode;

    /** 目的站 */
    @Excel(name = "目的站")
    @ApiModelProperty(value = "目的站")
    private String classTStationofdestination;

    /** 目的站英文 */
    @Excel(name = "目的站英文")
    @ApiModelProperty(value = "目的站英文")
    private String classTStationofdestinationEn;

    /** 开发频次 */
    @Excel(name = "开发频次")
    @ApiModelProperty(value = "开发频次")
    @NotNull(message = "开发频次不能为空")
    private String classTTransporttime;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    @ApiModelProperty(value = "创建用户id")
    @NotNull(message = "创建用户id不能为空")
    private String createuserid;

    /** 创建用户名 */
    @Excel(name = "创建用户名")
    @ApiModelProperty(value = "创建用户名")
    @NotNull(message = "创建用户名不能为空")
    private String createusername;

    /** 路线id */
    @Excel(name = "路线id")
    @ApiModelProperty(value = "路线id")
    @NotNull(message = "路线id不能为空")
    private Long lineId;

    /** 路线名称 */
    @Excel(name = "路线名称")
    @ApiModelProperty(value = "路线名称")
    private String lineName;

    /** 0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路类型 0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型 0是中欧2是中亚3是中越4是中俄")
    @NotNull(message = "线路类型不能为空")
    private Long lineTypeid;

}
