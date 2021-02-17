package com.szhbl.project.order.domain.vo;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 货物知识库对象 busi_cargobase 回程导入对象
 *
 * @author szhbl
 * @date 2020-08-18
 */
@Data
public class CargobaseEast implements Serializable {
    private static final long serialVersionUID = 1L;

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


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cargoReport", getCargoReport())
                .append("cargoName", getCargoName())
                .append("searchResult", getSearchResult())
                .append("isRailway", getIsRailway())
                .append("countryOrigin", getCountryOrigin())
                .toString();
    }
}
