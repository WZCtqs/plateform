package com.szhbl.project.inquiry.domain;

import java.io.Serializable;

/**
 * <p>
 * 城市表
 * </p>
 *
 * @author shahy123
 * @since 2019-12-25
 */
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
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
     * 中心坐标
     */
    private String cityCenter;
    /**
     * 省份code
     */
    private String provinceCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCenter() {
        return cityCenter;
    }

    public void setCityCenter(String cityCenter) {
        this.cityCenter = cityCenter;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        return "City{" +
        "id=" + id +
        ", cityCode=" + cityCode +
        ", cityName=" + cityName +
        ", cityCenter=" + cityCenter +
        ", provinceCode=" + provinceCode +
        "}";
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }
}
