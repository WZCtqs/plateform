package com.szhbl.project.client.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BusiClientsByTjr {
    @NotNull
    /**以逗号分割*/
    private String clientIds;

    /** 客户id */
    private String clientId;

    /** 新跟单员id */
    private String newMerchandiserId;

    /** 新跟单员姓名 */
    private String newMerchandiser;

    /** 西向跟单员姓名 */
    private String wMerchandiser;

    /** 西向跟单员id */
    private String wMerchandiserId;

    /** 东向跟单员姓名 */
    private String eMerchandiser;

    /** 东向跟单员id */
    private String eMerchandiserId;

    /** 0为西向 1为东向 */
    private String eastAndWest;
}
