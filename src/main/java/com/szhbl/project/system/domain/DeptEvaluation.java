package com.szhbl.project.system.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 部门考核管理对象 dept_evaluation
 *
 * @author szhbl
 * @date 2020-01-07
 */
@Data
@ApiModel(description = "部门考核管理")
public class DeptEvaluation extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 评价id
     */
    @ApiModelProperty(value = "评价id")
    private String evaluationId;

    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价id")
    @Excel(name = "评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationTime;

    /**
     * 评价人姓名
     */
    @ApiModelProperty(value = "评价id")
    @Excel(name = "评价人姓名")
    private String evaluationName;

    /**
     * 评价人编号
     */
    @Excel(name = "评价人编号")
    private String evaluationNo;

    /**
     * 评价人信息
     */
    @Excel(name = "评价人信息")
    private String evaluationtor;

    /**
     * 效率
     */
    @ApiModelProperty(value = "效率")
    @Excel(name = "管理部效率")
    private Double managedeEfficiency;

    /**
     * 质量
     */
    @ApiModelProperty(value = "质量")
    @Excel(name = "质量")
    private Double managedeQuality;

    /**
     * 责任
     */
    @ApiModelProperty(value = "责任")
    @Excel(name = "责任")
    private Double managedeDuty;

    /**
     * 态度
     */
    @ApiModelProperty(value = "态度")
    @Excel(name = "态度")
    private Double managedeAttitude;

    /**
     * 团队
     */
    @ApiModelProperty(value = "团队")
    @Excel(name = "团队")
    private Double managedeTeam;

    /**
     * 总计
     */
    @ApiModelProperty(value = "总计")
    @Excel(name = "总计")
    private Double managedeTotal;

    /**
     * 平均
     */
    @ApiModelProperty(value = "平均")
    @Excel(name = "平均")
    private Double managedeAve;

    /**
     * 评分依据
     */
    @ApiModelProperty(value = "评分依据")
    @Excel(name = "客户不满意事项及其他建议")
    private String managedegrading;

    /**
     * 效率
     */
//    @Excel(name = "结算部效率")
    private Double jsbEfficiency;

    /**
     * 质量
     */
//    @Excel(name = "质量")
    private Double jsbQuality;

    /**
     * 责任
     */
//    @Excel(name = "责任")
    private Double jsbDuty;

    /**
     * 态度
     */
//    @Excel(name = "态度")
    private Double jsbAttitude;

    /**
     * 团队
     */
//    @Excel(name = "团队")
    private Double jsbTeam;

    /**
     * 总计
     */
//    @Excel(name = "总计")
    private Double jsbTotal;

    /**
     * 平均
     */
//    @Excel(name = "平均")
    private Double jsbAve;

    /**
     * 评分依据
     */
//    @Excel(name = "客户不满意事项及其他建议")
    private String jsbgrading;

    /**
     * 效率
     */
    @Excel(name = "操作一效率")
    private Double cz1Efficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double cz1Quality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double cz1Duty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double cz1Attitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double cz1Team;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double cz1Total;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double cz1Ave;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String cz1grading;

    /**
     * 效率
     */
//    @Excel(name = "操作三效率")
    private Double cz3Efficiency;

    /**
     * 质量
     */
//    @Excel(name = "质量")
    private Double cz3Quality;

    /**
     * 责任
     */
//    @Excel(name = "责任")
    private Double cz3Duty;

    /**
     * 态度
     */
//    @Excel(name = "态度")
    private Double cz3Attitude;

    /**
     * 团队
     */
//    @Excel(name = "团队")
    private Double cz3Team;

    /**
     * 总计
     */
//    @Excel(name = "总计")
    private Double cz3Total;

    /**
     * 平均
     */
//    @Excel(name = "平均")
    private Double cz3Ave;

    /**
     * 评分依据
     */
//    @Excel(name = "客户不满意事项及其他建议")
    private String cz3grading;

    /**
     * 效率
     */
    @Excel(name = "集疏部效率")
    private Double jsEfficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double jsQuality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double jsDuty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double jsAttitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double jsTeam;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double jsTotal;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double jsAve;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String jsgrading;

    /**
     * 效率
     */
    @Excel(name = "箱管一效率")
    private Double xg1Efficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double xg1Quality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double xg1Duty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double xg1Attitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double xg1Team;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double xg1Total;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double xg1Ave;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String xg1grading;

    /**
     * 效率
     */
    @Excel(name = "箱管二效率")
    private Double xg2Efficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double xg2Quality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double xg2Duty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double xg2Attitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double xg2Team;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double xg2Total;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double xg2Ave;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String xg2grading;

    /**
     * 效率
     */
    @Excel(name = "拼箱部效率")
    private Double pxEfficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double pxQuality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double pxDuty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double pxAttitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double pxTeam;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double pxTotal;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double pxAve;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String pxgrading;

    /**
     * 效率
     */
    @Excel(name = "多式联运效率")
    private Double dslyEfficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double dslyQuality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double dslyDuty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double dslyAttitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double dslyTeam;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double dslyTotal;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double dslyAve;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String dslygrading;

    /**
     * 效率
     */
    @Excel(name = "公路部效率")
    private Double roadEfficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double roadQuality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double roadDuty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double roadAttitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double roadTeam;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double roadTotal;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double roadAve;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String roadgrading;

    /**
     * 效率
     */
    @Excel(name = "报关1效率")
    private Double bg1Efficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double bg1Quality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double bg1Duty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double bg1Attitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double bg1Team;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double bg1Total;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double bg1Ave;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String bg1grading;

    /**
     * 效率
     */
    @Excel(name = "报关2效率")
    private Double bg2Efficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double bg2Quality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double bg2Duty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double bg2Attitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double bg2Team;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double bg2Total;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double bg2Ave;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String bg2grading;

    /**
     * 效率
     */
    @Excel(name = "跟单效率")
    private Double gdEfficiency;

    /**
     * 质量
     */
    @Excel(name = "质量")
    private Double gdQuality;

    /**
     * 责任
     */
    @Excel(name = "责任")
    private Double gdDuty;

    /**
     * 态度
     */
    @Excel(name = "态度")
    private Double gdAttitude;

    /**
     * 团队
     */
    @Excel(name = "团队")
    private Double gdTeam;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Double gdTotal;

    /**
     * 平均
     */
    @Excel(name = "平均")
    private Double gdAve;

    /**
     * 评分依据
     */
    @Excel(name = "客户不满意事项及其他建议")
    private String gdgrading;

}
