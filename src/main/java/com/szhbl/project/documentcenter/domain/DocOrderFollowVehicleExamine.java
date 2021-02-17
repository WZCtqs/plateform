package com.szhbl.project.documentcenter.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 大口岸随车审核对象 doc_order_follow_vehicle_examine
 *
 * @author szhbl
 * @date 2020-08-19
 */
@Data
public class DocOrderFollowVehicleExamine extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 托书id
     */
    @Excel(name = "托书id")
    private String orderId;

    /**
     * 舱位号
     */
    @Excel(name = "舱位号")
    private String orderNumber;

    /**
     * 实际班列日期
     */
    @Excel(name = "实际班列日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date Classdate;

    /**
     * 集装箱箱型
     */
    @Excel(name = "集装箱箱型")
    private String containerType;

    /**
     * 集装箱箱量
     */
    @Excel(name = "集装箱箱量")
    private String containerBoxamount;

    /**
     * 业务员
     */
    @Excel(name = "业务员")
    private String clientTjr;

    /**
     * 跟单姓名
     */
    @Excel(name = "跟单姓名")
    private String orderMerchandiser;

    /**
     * 货品中文名称 （订单商品表）
     */
    @Excel(name = "货品中文名称 ", readConverterExp = "订=单商品表")
    private String goodsName;

    /**
     * HS编码
     */
    @Excel(name = "HS编码")
    private String goodsInReport;

    /**
     * 最外层包装数量 (订单货物表)
     */
    @Excel(name = "最外层包装数量 (订单货物表)")
    private String goodsNumber;

    /**
     * 体积 (订单商品表)
     */
    @Excel(name = "体积 (订单商品表)")
    private String goodsCbm;

    /**
     * 班列编号
     */
    @Excel(name = "班列编号")
    private String classBh;

    /**
     * 口岸
     */
    @Excel(name = "口岸")
    private String classPort;

    /**
     * 下货站
     */
    @Excel(name = "下货站")
    private String orderUnloadsite;

    /**
     * 随车审核人
     */
    @Excel(name = "随车审核人")
    private String Areviewer_suiche;

    /**
     * 报关员
     */
    @Excel(name = "报关员")
    private String Areviewer_baoguan;

    /**
     * 票数
     */
    @Excel(name = "票数")
    private String Number;

    /**
     * 报关状态
     */
    @Excel(name = "报关状态")
    private String Status_baoguan;

    /**
     * 报关备注
     */
    @Excel(name = "报关备注")
    private String Remark_baoguan;

    /**
     * 随车组备注
     */
    @Excel(name = "随车组备注")
    private String Remark_suiche;

}
