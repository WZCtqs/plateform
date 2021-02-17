package com.szhbl.project.documentcenter.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "上货站查询")
@Data
public class BusiSiteDto implements Serializable {
    /** 城市代码 */
    @ApiModelProperty(value = "城市代码")
    private String code;

    /** 城市中文名 */
    @ApiModelProperty(value = "城市中文名")
    private String nameCn;

    /** 城市英文名 */
    @ApiModelProperty(value = "城市英文名")
    private String nameEn;
}
