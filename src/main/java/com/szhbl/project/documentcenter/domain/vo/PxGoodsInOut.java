package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 拼箱出入库表
 * @author HP
 */
@Data
public class PxGoodsInOut implements Serializable {

    private Integer id;
    /**
     *订单ID
     */
    public String order_id;

    private String order_number;
    /**
     *箱号
     */
    public String containerNum;
    /**
     *从中心站调回时间
     */
    public String Repatriate_time;
    /**
     *计划拆箱时间
     */
    public String PlanCX_time;
    /**
     *拆箱时间
     */
    public String CX_time;
    /**
     *库内位置
     */
    public String Store_station;
    /**
     *车牌号
     */
    public String car_number;
    /**
     *实际提货时间
     */
    public String REPick_time;
    /**
     *车辆入区时间
     */
    public String Car_intime;
    /**
     *车辆出区时间
     */
    public String Car_outtime;
    /**
     *装车开票
     */
    public String Load_KP;
    /**
     *堆存时间
     */
    public String Storage_days;

    public Date createTime;

}
