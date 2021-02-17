package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2021-02-09 17:06
 */
@Data
public class TrackAbroadTimeVO implements Serializable {

    /**
     * orderid
     */
    private String orderId;
    /**
     * 预计提货时间、预计提箱时间
     */
    private String time;
    /**
     * 提货人、提箱人
     */
    private String operator;
    /**
     * 备注（含有提货人，提箱人）
     */
    private String remark;

    private Integer sort;
}
