package com.szhbl.project.order.domain.vo;

import lombok.Data;

@Data
public class Merchandiser {

    /** 客户跟单姓名 */
    private String merchandiser;

    /** 客户跟单ID */
    private String merchandiserId;

    /** 往返 0=西向,1=东向*/
    private String classEastandwest;

    /** 订舱方ID*/
    private String clientId;

    /** 托书跟单姓名 */
    private String orderMerchandiser;

    /** 托书跟单ID */
    private String orderMerchandiserId;
}
