package com.szhbl.project.system.domain.vo;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

@Data
public class DeptEvaluationExportSimple {

    @Excel(name = "部门", width = 30)
    private String evaluationDept;

    @Excel(name = "分数", width = 30)
    private Double score;

    /**
     * 操作一
     */
    private String CZ1;
    /**
     * 集输部
     */
    private String JS;
    /**
     * 箱管1
     */
    private String XG1;
    /**
     * 拼箱
     */
    private String PX;
    /**
     * 多式联运
     */
    private String DSLY;
    /**
     * 公路
     */
    private String ROAD;
    /**
     * 箱管2
     */
    private String XG2;
    /**
     * 报关1
     */
    private String BG1;
    /**
     * 报关2
     */
    private String BG2;
}
