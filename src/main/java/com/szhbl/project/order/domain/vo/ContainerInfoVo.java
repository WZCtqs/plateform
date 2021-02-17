package com.szhbl.project.order.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 箱型箱量
 */
@Data
public class ContainerInfoVo implements Serializable {

    /**
     * 箱型
     */
    private String type;

    /**
     * 箱量
     */
    private String num;
}
