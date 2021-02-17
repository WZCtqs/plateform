package com.szhbl.project.inquiry.dto;

import lombok.Data;

/**
 * 省市区结合数据
 *
 * @author shahy
 * @date 2020-10-02
 */
@Data
public class PcaDTO
{

    /**
     * 省份code
     */
    private String provinceCode;
    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 省份名称英文
     */
    private String provinceNameEn;

    /**
     * 城市code
     */
    private String cityCode;
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市名称英文
     */
    private String cityNameEn;

    /**
     * 区域code
     */
    private String areaCode;
    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域名称英文
     */
    private String areaNameEn;


}
