package com.szhbl.project.basic.domain.boxfee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BoxfeeT {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String boxId;

    /** 集装箱类型：0普通箱 1特种箱 */
    private String boxType;

    /** 选择类型：0提箱；1还箱 */
    @Excel(name = "提/还(0提箱 1还箱)")
    private String addressType;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路：0中欧2中亚3中越4中俄,英文逗号隔开")
    private String lineTypeid;

    /** 国内外地点(0国外 1国内) */
    @Excel(name = "国内外地点(0国外 1国内)")
    private String intra;

    /** 提/还箱地点中文 */
    @Excel(name = "中文")
    private String address;

    /** 提/还箱地点英文 */
    @Excel(name = "英文")
    private String addressEn;

    /** 20OT/20HOT */
    @Excel(name = "20OT/20HOT")
    private String ss;

    /** 40OT/40HOT */
    @Excel(name = "40OT/40HOT")
    private String ff;

    /** 提/还箱地点英文 */
    @Excel(name = "20HT/40HT")
    private String sf;

    /** 40RF/45RF */
    @Excel(name = "40RF/45RF")
    private String fff;

    /** 箱型 */
    private String containerType;

    /** 状态 0禁用 1启用*/
    private String state;

    /** 平衡费用 */
    private String cost;

    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    /** 截止时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /** 创建人id */
    private String createuserid;

    /** 创建人用户名 */
    private String createusername;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdate;

}
