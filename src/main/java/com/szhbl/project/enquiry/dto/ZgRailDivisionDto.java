package com.szhbl.project.enquiry.dto;


import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class ZgRailDivisionDto {
    /** 线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄）） */
    @Excel(name = "线路(0郑欧、2中亚、3中越、4中俄)")
    private String lineType;

    /** 上货站点 */
    @Excel(name = "上货站")
    private String orderUploadSite;

    /** 下货站点 */
    @Excel(name = "下货站")
    private String orderUnloadSite;


    /** 箱子类型 */
    @Excel(name = "箱型")
    public String containerType;

    /** 箱型值 */
    @Excel(name = "箱型值")
    private String containerTypeValue;

    /** 是否自备箱,（0是货主自备箱SOC，1是承运人自备箱COC） */
    @Excel(name = "(0是货主自备箱SOC，1是承运人自备箱COC)")
    private String isContainer;

    /** 铁路运费 */
    @Excel(name = "铁路运费")
    private Long railCost;

    /** 去回程,0为去程 1为回程*/
    @Excel(name = "东/西向(0西向，1东向)")
    private String eastOrWest;

    /**
     * 有效开始日期
     */
    @Excel(name = "有效开始日期")
    public Date validStartDate;


    /**
     * 有效结束日期
     */
    @Excel(name = "有效结束日期")
    public Date validEndDate;




}
