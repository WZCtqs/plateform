package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 大口岸箱号随车审核对象 doc_order_conexamine
 *
 * @author szhbl
 * @date 2020-08-19
 */
@Data
public class DocOrderConexamine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 托书ID
     */
    @Excel(name = "托书ID")
    private String orderId;

    /**
     * 箱号
     */
    @Excel(name = "箱号")
    private String container;

    /**
     * 箱号随车审核人
     */
    @Excel(name = "箱号随车审核人")
    private String ConAreviewer_suiche;

    /**
     * 箱号随车状态
     */
    @Excel(name = "箱号随车状态")
    private String ConStatus_suiche;

    /**
     * 项数个数
     */
    @Excel(name = "项数个数")
    private String Number_suiche;

    /**
     * 税号个数
     */
    @Excel(name = "税号个数")
    private String shuihao_suiche;

    /**
     * 项数收费(超过20个，每超出一项10元)
     */
    @Excel(name = "项数收费(超过20个，每超出一项10元)")
    private String Number_cost;

    /**
     * 税号收费(超过13个，每超出一个20美元)
     */
    @Excel(name = "税号收费(超过13个，每超出一个20美元)")
    private String shuihao_cost;

    /**
     * 随车备注
     */
    @Excel(name = "随车备注")
    private String ConRemark_suiche;

}
