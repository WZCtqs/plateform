package com.szhbl.project.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DangerousGoodsImport {
    /** 主键 */
    private String goodsId;

    /** 物品名称中文 */
    @Excel(name = "中文品名")
    private String goodsName;

    /** 物品名称英文 */
    @Excel(name = "英文品名")
    private String goodsEnName;

    /** 报关hs编码 */
    @Excel(name = "报关hs编码")
    private String goodsReport;

    /** 清关hs编码 */
    @Excel(name = "清关hs编码")
    private String goodsClearance;

    /** 特殊备注 */
    @Excel(name = "特殊备注")
    private String specialremark;
    /** 特殊备注 */
    @Excel(name = "英文特殊备注")
    private String specialEnRemark;

    /** 等级 0危险品 1疑似危险品 */
    @Excel(name = "等级 0危险品 1疑似危险品")
    private String noteLevel;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;
}
