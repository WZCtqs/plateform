package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 入仓核实单、到货信息
 * @author HP
 */
@Data
public class PxInBoxCheck implements Serializable {

    private Integer id;
    //订单ID
    public String order_id;

    private String order_number;

    //入货日期
    public String px_arriveYardTime;

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

    //标签粘贴
    public String px_Checkman;

    //接货员
    public String px_enter_time;

    //托书数量
    public String px_order_number;

    //托书总体积
    public String px_order_ztj;

    //托书总重量
    public String px_order_zzl;

    // 跟单员
    public String px_merchandiser;

    public Date createTime;

}
