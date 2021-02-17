package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocOrderMsg2 implements Serializable {

    private Integer sort;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 班列日期
     */
    private String classDate;


    /**
     * 审核通过时间
     */
    private String exameTime;

    /**
     * 单证类型
     */
    private String fileTypeKey;

    /**
     * 单证类型
     */
    private String fileTypeText;

    /**
     * 单证类型英文
     */
    private String fileTypeTextEn;

    /**
     * 节点维护的设置id
     */
    private Long newRemindId;

    @Override
    public String toString() {
        return "DocOrderMsg2{" +
                "orderNumber='" + orderNumber + '\'' +
                ", classDate=" + classDate +
                ", fileTypeKey='" + fileTypeKey + '\'' +
                ", fileTypeText='" + fileTypeText + '\'' +
                ", fileTypeTextEn='" + fileTypeTextEn + '\'' +
                '}';
    }
}
