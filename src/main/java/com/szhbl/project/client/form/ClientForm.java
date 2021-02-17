package com.szhbl.project.client.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientForm {
    @NotNull
    /**以逗号分割*/
    private String clientIds;

    /**原西向跟单员id*/
    private String oldWMerchandiserId;

    /**原西向跟单员姓名*/
    private String oldWMerchandiser;

    /**原西向跟单员工号*/
    private String oldWMerchandiserNumber;

    /**原东向跟单员id*/
    private String oldEMerchandiserId;

    /**原东向跟单员姓名*/
    private String oldEMerchandiser;

    /**原东向跟单员工号*/
    private String oldEMerchandiserNumber;

    /** 新西向跟单员id */
    @JsonProperty(value = "wMerchandiserId")
    private String newWMerchandiserId;

    /** 新西向跟单员姓名 */
    @JsonProperty(value = "wMerchandiser")
    private String newWMerchandiser;

    /**新西向跟单工号*/
    @JsonProperty(value = "wMerchandiserNumber")
    private String newWMerchandiserNumber;

    /** 新东向跟单员id */
    @JsonProperty(value = "eMerchandiserId")
    private String newEMerchandiserId;

    /** 新东向跟单员姓名 */
    @JsonProperty(value = "eMerchandiser")
    private String newEMerchandiser;

    /**新东向跟单工号*/
    @JsonProperty(value = "eMerchandiserNumber")
    private String newEMerchandiserNumber;
    /**
     * (0,西向，1东向)
     */
    @NotBlank
    private String type;
}
