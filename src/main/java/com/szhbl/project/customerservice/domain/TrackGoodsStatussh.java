package com.szhbl.project.customerservice.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪_货物状态--以舱位号为单位，标记是否发车对象 track_goods_status
 * 
 * @author b
 * @date 2020-03-28
 */
public class TrackGoodsStatussh extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 实际班列id */
    @Excel(name = "实际班列id")
    private String actualClassId;

    /** 订单id */
    @Excel(name = "订单id")
    private String orderId;

    /** 箱号 */
    @Excel(name = "箱号")
    private String boxNum;

    /** 货物是否上车0是1否 */
    @Excel(name = "货物是否上车0是1否")
    private Integer isGetin;

    /** 实际班列日期 */
    @Excel(name = "实际班列日期")
    private String actualClassDate;

    /** 是否正常箱0是1否 */
    @Excel(name = "是否正常箱0是1否")
    private Integer isNormal;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setActualClassId(String actualClassId) 
    {
        this.actualClassId = actualClassId;
    }

    public String getActualClassId() 
    {
        return actualClassId;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setBoxNum(String boxNum) 
    {
        this.boxNum = boxNum;
    }

    public String getBoxNum() 
    {
        return boxNum;
    }
    public void setIsGetin(Integer isGetin) 
    {
        this.isGetin = isGetin;
    }

    public Integer getIsGetin() 
    {
        return isGetin;
    }
    public void setActualClassDate(String actualClassDate) 
    {
        this.actualClassDate = actualClassDate;
    }

    public String getActualClassDate() 
    {
        return actualClassDate;
    }
    public void setIsNormal(Integer isNormal) 
    {
        this.isNormal = isNormal;
    }

    public Integer getIsNormal() 
    {
        return isNormal;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("actualClassId", getActualClassId())
            .append("orderId", getOrderId())
            .append("boxNum", getBoxNum())
            .append("isGetin", getIsGetin())
            .append("actualClassDate", getActualClassDate())
            .append("isNormal", getIsNormal())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
