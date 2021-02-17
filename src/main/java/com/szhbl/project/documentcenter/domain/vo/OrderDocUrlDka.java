package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

/**
 * @author HP
 */
@Data
public class OrderDocUrlDka extends OrderDocUrl {

    /**
     * 订单编号
     */
    private String orderNumbers;

    /*提单、正式提单（电放提单、正本提单）*/
    /**
     * url
     */
    private String url;

    private String IsDelete;
}
