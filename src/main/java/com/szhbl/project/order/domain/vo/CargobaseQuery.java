package com.szhbl.project.order.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 货物知识库对象 busi_cargobase 回程导入对象
 *
 * @author szhbl
 * @date 2020-08-18
 */
@Data
public class CargobaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 税号
     */
    private String Cargo_Report;

    /**
     * 货物品名
     */
    private String Cargo_Name;

    /**
     * 搜索结果
     */
    private String eastorwest;

    /**
     * 是否可铁路运输
     */
    private String UnloadSite;

    /**
     * 原产国
     */
    private String CountryOrigin;


    private String IsRailway;

    private String SearchResult;


    @Override
    public String toString() {
        return "CargobaseQuery{" +
                "Cargo_Report='" + Cargo_Report + '\'' +
                ", Cargo_Name='" + Cargo_Name + '\'' +
                ", eastorwest='" + eastorwest + '\'' +
                ", UnloadSite='" + UnloadSite + '\'' +
                ", CountryOrigin='" + CountryOrigin + '\'' +
                '}';
    }
}
