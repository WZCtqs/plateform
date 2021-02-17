package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 回程进站统计表对象 doc_order_instation
 *
 * @author szhbl
 * @date 2020-05-25
 */
@Data
public class DocOrderInstation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * orderid
     */
    private String orderId;

    private String orderNumber;

    /**
     * 封号
     */
    private String sealnum;

    /**
     * 报关员
     */
    private String customsbroker;

    /**
     * 箱型
     */
    private String containerType;

    /**
     * 箱号
     */
    private String containerNo;

    /**
     * 进站时间
     */
    private String instationTime;
    /**
     * 入仓时间重箱进站表
     */
    private String arriveTime;

    /**
     * js--集输系统；gw--关务系统；gwcz--国外场站
     */
    private String fromSystem;

}
