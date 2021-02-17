package com.szhbl.project.documentcenter.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-12-07 16:15
 */
@Data
public class DcSettlementBO implements Serializable {

    private String ORDERID;

    private String ORDERNUMBER;

    private String CONTAINERNO;

    private Double PXVOLUME;

    private Double PXSETTLEMENTVOLUME;

    private Double PXLONGANDWIDE;

}
