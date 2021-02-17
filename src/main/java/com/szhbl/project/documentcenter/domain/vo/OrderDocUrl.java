package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDocUrl implements Serializable {
    /**
     * 数据操作类型：update,delete,insert
     */
    private String type;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 箱号
     */
    private String containerNo;
    /**
     * 箱型
     */
    private String containerType;
    /**
     * 文件url
     */
    private String fileUrl;
    /**
     * 班列编号
     */
    private String classNum;

    //发送邮件字段
    private String clientId;
    private String clientEmail;
    private String merchandiser;
    private String merchandiserId;

    @Override
    public String toString() {
        return "OrderDoc{" +
                "type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
