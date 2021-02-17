package com.szhbl.project.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description : 转待审核发送子系统类
 * @Author : wangzhichao
 * @Date: 2020-07-18 15:15
 */
@Data
public class TOCheckOrder implements Serializable {

    /*托书id*/
    private String orderId;
    /*舱位号*/
    private String orderNumber;
    /*托书状态*/
    private String status;
    /*服务类型*/
    private String bookingService;
    /*班列日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date classDate;
    /*更改时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /*更改内容*/
    private String updateContent;

    private String gwczResult;

    private String updateBy;
}
