package com.szhbl.project.documentcenter.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2021-02-17 09:22
 */
@Data
public class DocOrderPickGoodTimeVO implements Serializable {

    private String classEastAndWest;

    private String isConsolidation;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String pickGoodsTimec;

    /**
     * 客户上传提货联系人
     */
    @ApiModelProperty(value = "客户上传提货联系人")
    private String pickGoodsContacts;

    @ApiModelProperty(value = "客户上传提货联系方式")
    private String pickGoodsPhone;
    /**
     * 客户上传提货详细地址
     */
    @ApiModelProperty(value = "客户上传提货详细地址")
    private String pickGoodsAddress;

    private Integer pickGoodsTimecConfirm;

    /**
     * 国外提货、提箱时间
     */
    private String abroadPickTime;
    /**
     * 国外预计提货、提箱时间
     */
    private String abroadPlanPickTime;
    /**
     * 备注（提货人信息）
     */
    private String remark;
    /**
     * 提货人、提箱人
     */
    private String operator;

}
