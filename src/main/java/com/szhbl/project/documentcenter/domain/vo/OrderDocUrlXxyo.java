package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

/**
 * @author HP
 */
@Data
public class OrderDocUrlXxyo extends OrderDocUrl {

    /**
     * 签收单
     */
    private String receiptGoods;

    /**
     * 进站集装箱拍照
     */
    private String arrivalStation;

    /**
     * 装箱照
     */
    private String boxingPhoto;
}
