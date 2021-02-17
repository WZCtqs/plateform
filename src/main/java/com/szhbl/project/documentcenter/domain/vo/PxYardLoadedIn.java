package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 堆场重箱进展表
 * @author HP
 */
@Data
public class PxYardLoadedIn implements Serializable {

    private Integer id;
    /**
     * orderid
     */
    public String order_id;
    /**
     * 舱位号
     */
    private String order_number;
    /**
     * 箱号
     */
    public String xianghao;

    public String SealNum;

    /**
     * 箱型
     */
    public String xiangxing;
    /**
     * 入场日期
     */
    public String xianghao_place;
    /**
     * 入场日期
     */
    public String px_entry_date;
    /**
     * 入场时间
     */
    public String px_entry_time;
    /**
     * 进站日期/出场日期
     */
    public String px_enter_car;
    /**
     * 进站时间/出场时间
     */
    public String px_enter_lead_number;
    /**
     * 备注
     */
    public String edit_remark;
    /**
     * 正面吊测偏结果
     */
    public String cepian_result;
    /**
     * 是否加固
     */
    public String is_jg;
    /**
     * 是否直接进站
     */
    public String ISJZ_LDbus;

    //是否删除  0否   1是
    public String isDelect;
    //是否加固
    public String needRooting;
    //位置
    public String Position;

    public Date createTime;
    //重量
    public String zx_weight;

}
