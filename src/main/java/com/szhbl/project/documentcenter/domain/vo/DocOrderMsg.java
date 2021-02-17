package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DocOrderMsg implements Serializable {

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
    private Date classDate;

    /**
     * 审核通过时间
     */
    private Date exameTime;

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
        return "DocOrderMsg{" +
                "orderNumber='" + orderNumber + '\'' +
                ", classDate=" + classDate +
                ", fileTypeKey='" + fileTypeKey + '\'' +
                ", fileTypeText='" + fileTypeText + '\'' +
                ", fileTypeTextEn='" + fileTypeTextEn + '\'' +
                '}';
    }
}
