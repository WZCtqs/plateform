package com.szhbl.common.utils.sms;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsResult implements Serializable {

    private Integer code;
    private String msg;
    private Integer count;
    private String fee;
    private String unit;
    private String mobile;
    private Integer sid;
}
