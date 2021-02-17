package com.szhbl.project.track.domain.vo;

import lombok.Data;

@Data
public class AbnormalBoxEmailsVo {//异常获取邮箱vo

    /** 业务领导id */
    private String ancestors;

    /** 业务自己邮箱 */
    private String yewuEmail;

    /** 东西向 */
    private String goCome;

    /** 东向跟单员   回  */
    private String eMerchandiserId;

    /** 西向跟单员   去  */
    private String wMerchandiserId;

    /** 发送邮箱 */
    private String sendMails;

    /** 密送邮箱 */
    private String bccMails;

    /** 是否整拼箱0整柜1拼箱 */
    private String isFull;
}
