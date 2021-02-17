package com.szhbl.project.order.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 货物知识库对象 busi_cargobase
 *
 * @author szhbl
 * @date 2020-08-18
 */
public class BusiCargobase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 税号
     */
    @Excel(name = "税号")
    private String cargoReport;

    /**
     * 货物品名
     */
    @Excel(name = "货物品名")
    private String cargoName;

    /**
     * 下货站
     */
    @Excel(name = "下货站")
    private String unloadSite;

    /**
     * 搜索结果
     */
    @Excel(name = "查询结果")
    private String searchResult;

    /**
     * 是否可铁路运输
     */
    @Excel(name = "是否可铁路运输（是/否）")
    private String isRailway;

    /**
     * 原产国
     */
    @Excel(name = "货物原产国")
    private String countryOrigin;

    /**
     * 东西向
     */
    @Excel(name = "去回程（去程、回程）")
    private String eastorwest;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCargoReport(String cargoReport) {
        this.cargoReport = cargoReport;
    }

    public String getCargoReport() {
        return cargoReport;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setUnloadSite(String unloadSite) {
        this.unloadSite = unloadSite;
    }

    public String getUnloadSite() {
        return unloadSite;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setIsRailway(String isRailway) {
        this.isRailway = isRailway;
    }

    public String getIsRailway() {
        return isRailway;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setEastorwest(String eastorwest) {
        this.eastorwest = eastorwest;
    }

    public String getEastorwest() {
        return eastorwest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("cargoReport", getCargoReport())
                .append("cargoName", getCargoName())
                .append("unloadSite", getUnloadSite())
                .append("searchResult", getSearchResult())
                .append("isRailway", getIsRailway())
                .append("countryOrigin", getCountryOrigin())
                .append("eastorwest", getEastorwest())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
