package com.szhbl.project.order.domain.vo;


import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;


@Data
public class OrderExamExcelVo {

    @Excel(name = "托书编号")
    private String orderNumber;

    @Excel(name = "转待审核次数")
    private Integer size;

}
