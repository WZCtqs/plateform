package com.szhbl.project.enquiry.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 非标准送货地与还箱地对应表
 * 
 * @author shy
 * @date 2020-06-18
 */
@Getter
@Setter
public class ZgReturnNonStandard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 还箱地 */
    @Excel(name = "还箱地")
    private String returnBoxAddress;

    /** 还箱费 */
    private String returnBoxFee;

    /** 距离 */
    @Excel(name = "百度地图距离")
    private Long distance;

    /** 送货费 */
    @Excel(name = "送货费")
    private Long deliveryFee;

    /** 白卡送货费 */
    @Excel(name = "白卡送货费")
    private Long whiteCardDeliveryFee;

    /**
     * 货源地（收货地），默认发货地郑州
     */
    @Excel(name = "货源地城市")
    private String receiptPlace;

    /** 国内公路运输车辆类型，0普通车、1普通卡车、2白卡专车*/
    private String truckType;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("province", getProvince())
            .append("returnBoxAddress", getReturnBoxAddress())
            .append("returnBoxFee", getReturnBoxFee())
            .append("distance", getDistance())
            .append("deliveryFee", getDeliveryFee())
            .append("whiteCardDeliveryFee", getWhiteCardDeliveryFee())
            .append("receiptPlace", getReceiptPlace())
            .toString();
    }

}
