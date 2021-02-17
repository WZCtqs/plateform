package com.szhbl.project.order.domain;

import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订舱询价暂存对象 booking_inquiry_apply
 * 
 * @author dps
 * @date 2020-05-20
 */
@Data
public class BookingInquiryApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 托书id */
    @Excel(name = "托书id")
    private String orderId;

    /** 询价结果id */
    @Excel(name = "询价id")
    private Long inquiryId;

    /** 询价结果id */
    @Excel(name = "询价结果id")
    private Long inquiryRecordId;

    /** 发货地省份 */
    @Excel(name = "发货地省份")
    private String senderProvince;

    /** 发货地城市 */
    @Excel(name = "发货地城市")
    private String senderCity;

    /** 发货地区域 */
    @Excel(name = "发货地区域")
    private String senderArea;

    /** 发货地 */
    @Excel(name = "发货地")
    private String shipmentPlace;

    /** 上货站 */
    @Excel(name = "上货站")
    private String uploadStation;

    /** 收货地省份 */
    @Excel(name = "收货地省份")
    private String receiveProvince;

    /** 收货地城市 */
    @Excel(name = "收货地城市")
    private String receiveCity;

    /** 收货地区域 */
    @Excel(name = "收货地区域")
    private String receiveArea;

    /** 收货地 */
    @Excel(name = "收货地")
    private String receiptPlace;

    /** 下货站 */
    @Excel(name = "下货站")
    private String dropStation;

    /** 服务（0：门到门；1：门到站；2：站到站；3：站到门） */
    @Excel(name = "服务", readConverterExp = "0=：门到门；1：门到站；2：站到站；3：站到门")
    private String bookingService;

    /** 是否委托zih提货（0：是；1：否） */
    @Excel(name = "是否委托zih提货", readConverterExp = "0=：是；1：否")
    private String isPickUp;

    /** 是否由ZIH代理送货  （0否 1是） */
    @Excel(name = "是否由ZIH代理送货  ", readConverterExp = "0=否,1=是")
    private String isDelivery;

    /** 是否委托zih代理报关（0：是；1：否） */
    @Excel(name = "是否委托zih代理报关", readConverterExp = "0=：是；1：否")
    private String isOrderCustoms;

    /** 是否委托zih代理清关（0：否；1：是） */
    @Excel(name = "是否委托zih代理清关", readConverterExp = "0=：否；1：是")
    private String isClearCustoms;

    /** 箱属（0：自备；1：zih） */
    @Excel(name = "箱属", readConverterExp = "0=：自备；1：zih")
    private String containerBelong;

    /** 货物类型（0：整柜；1：拼箱） */
    @Excel(name = "货物类型", readConverterExp = "0=：整柜；1：拼箱")
    private String goodsType;

    /** 箱型 */
    @Excel(name = "箱型")
    private String containerType;

    /** 箱量 */
    @Excel(name = "箱量")
    private Integer containerNum;

    /** 包装方式 */
    @Excel(name = "包装方式")
    private String packageType;

    /** 是否可堆叠（0否：1是） */
    @Excel(name = "是否可堆叠", readConverterExp = "0=否：1是")
    private String isStack;

    /** 总数量 */
    @Excel(name = "总数量")
    private String totalAmount;

    /** 总重量(kg) */
    @Excel(name = "总重量(kg)")
    private String totalWeight;

    /** 总体积(m³) */
    @Excel(name = "总体积(m³)")
    private String totalVolume;

    /** 询价时间 */
    @Excel(name = "询价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inquiryTime;

    /** 客户id */
    @Excel(name = "客户id")
    private String clientId;

    /** 删除标识（0未删除；1：已删除） */
    private String delFlag;

    /** 线路线路类型（0郑欧（中欧）、2郑中亚（中亚）、3郑东盟(中越)）、4郑俄（中俄） */
    @Excel(name = "线路线路类型", readConverterExp = "0=郑欧（中欧")
    private String lineType;

    /** 还箱地 */
    @Excel(name = "还箱地")
    private String hxAddress;

    /** 去回程,0为去程 1为回程 */
    @Excel(name = "去回程,0为去程 1为回程")
    private String eastOrWest;

    /** 上货站编号 */
    @Excel(name = "上货站编号")
    private String uploadStationNum;

    /** 下货站编号 */
    @Excel(name = "下货站编号")
    private String dropStationNum;

    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车 */
    @Excel(name = "国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车")
    private String truckType;

    /** 时效（箱型亚欧，0普通，1加急） */
    @Excel(name = "时效", readConverterExp = "箱=型亚欧，0普通，1加急")
    private String limitation;

    /** 提货方式（0整柜到堆场，1散货到堆场）整柜和站到站同时选择 */
    @Excel(name = "提货方式", readConverterExp = "0=整柜到堆场，1散货到堆场")
    private String deliveryType;

    /** 客户类型 0货代 1直客 2贸易公司 */
    @Excel(name = "客户类型 0货代 1直客 2贸易公司")
    private String clientType;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientUnit;

    /** 是否易碎（1是0否） */
    @Excel(name = "是否易碎", readConverterExp = "1=是0否")
    private String goodsFragile;

    /** 询价状态,1（报价中）、2（待审核）、3（已报价）、4（驳回） */
    @Excel(name = "询价状态,1", readConverterExp = "报=价中")
    private String inquiryState;

    /** 还箱地id */
    @Excel(name = "还箱地id")
    private String hxaddressId;

    /** 清关费用标准 */
    @Excel(name = "清关费用标准")
    private String qgfy;

    /** 场站费用标准 */
    @Excel(name = "场站费用标准")
    private String czfy;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String turndownReason;

    /** 货物详情 */
    @Excel(name = "货物详情")
    private String goodsDetail;

}
