package com.szhbl.project.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 提单邮件提醒客户跟单
 * @Author : wangzhichao
 * @Date: 2020-10-09 09:17
 */
@Data
public class LatterNoticeDto implements Serializable {

    private String orderId;

    private String orderNumber;

    private String clientEmail;

    private Integer isChoose;

    private Integer isSubmit;

    private String[] gdEmail;

    private String merchandiserId;
}
