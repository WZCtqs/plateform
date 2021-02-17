package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-09-25 14:06
 */
@Data
public class OrderDataForDoc implements Serializable {

    private String classEastAndWest;

    private String isConsolidation;

    private Date shipOrderUnloadtime;

    private String shipOrderUnloadcontacts;

    private String shipOrderUnloadway;

    private String shipOrderUnloadaddress;

    private String detailedAddress;

    private String orderNumber;
    /**
     * 货品中文名称
     */
    private String goodsName;

    /**
     * 货品英文名称
     */
    private String goodsEnName;

    /**
     * 重量
     */
    private String goodsKgs;

    /**
     * 体积
     */
    private String goodsCbm;
}
