package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 装柜清单（件重尺）
 * @author HP
 */
@Data
public class PxBoxingList implements Serializable {

    private Integer id;

    // orderId
    private String order_id;

    private String order_number;

    //包装方式12
    public String px_packing;

    //数量13
    public String px_number;

    //规格（长*宽*高*件数）14
    public String px_box_type;

    //单件重量15 毛重
    public String px_box_number;

    //总重16
    public String px_LongAndWide;

    //实际体积（m3）
    public String px_volume;

    //结算体积（m3）
    public String px_Settlement_volume;

    //特殊货物收费（USD）
    public String px_car_number;

    //其他费用人民币
    public String px_otherCharge;

    //备注
    public String px_lead_number;

    //箱号
    public String xianghao;

    //箱型
    public String xiangxing;

    //责任人
    public String px_weight;

    //到货情况
    public String px_person_charge;

    public Date createTime;

    public String isDelect;
}
