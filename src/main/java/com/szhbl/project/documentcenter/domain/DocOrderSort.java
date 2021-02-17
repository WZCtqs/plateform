package com.szhbl.project.documentcenter.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单单证提醒次序
 *
 * @author hp
 * @date 2020-04-13
 */
@Data
public class DocOrderSort implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单id-单证类型key */
    private String orderidDoctype;

    private String orderId;

    private String fileTypeKey;

    @Override
    public String toString() {
        return "DocOrderSort{" +
                "id=" + id +
                ", orderidDoctype='" + orderidDoctype + '\'' +
                '}';
    }
}
