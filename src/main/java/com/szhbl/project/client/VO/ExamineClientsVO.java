package com.szhbl.project.client.VO;

import lombok.Data;

import java.util.Date;
@Data
public class ExamineClientsVO {
    /** 客户id */
    private String clientId;


    /** 推荐人 */
    private String clientTjr;

    /** varchar	推荐人ID */
    private String clientTjrId;


    /** 登录帐号 */

    private String clientLoginuser;

    /** 登录密码 */
    private String clientLoginpwd;

    /** 有效开始时间 */
    private Date clientValiditydate;

    /** 到期时间 */
    private Date clientDisableddate;

    /** 审核人ID */
    private String clientExampersonid;

    /** 审核人 */
    private String clientExamperson;



    /** 审核信息 */

    private String examinfo;

    /** 审核状态 */

    private String isexamline;

    /** 0 正常，1帐户注销 */

    private String cancelaccount;


    /**
     * 老赖标记(0,是，1否）
     */
    private String deadBeatType;

}
