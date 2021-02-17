package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class EmailRecordsVo {//邮件记录查询vo

    //委托书编号
    private String orderNum;
    //发送邮箱
    private String sendEmail;
    // 发送人
    private String sendName;
    //发送时间
    private String sendTime;


}
