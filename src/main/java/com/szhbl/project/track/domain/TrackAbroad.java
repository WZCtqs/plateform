package com.szhbl.project.track.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 运踪_中亚境外对象 track_abroad
 * 
 * @author lzd
 * @date 2020-03-26
 */
@Data
public class TrackAbroad extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 订单id */
    private String orderId;

    /** 班列id */
    private String classId;

    /** 订单号（委托书编号）（舱位号） */
    private String orderNumber;

    /** 发车日期 */
    @Excel(name = "发车日期")
    private String departureDate;

    /** 箱号 */
    @Excel(name = "箱号")
    private String boxNum;

    /** 集装箱箱型 */
    private String boxType;

    /** 客户接收邮箱 */
    private String customerEmails;

    /** 跟单接收邮箱 */
    private String documentaryEmails;

    /** 业务接收邮箱 */
    private String businessEmails;

    /** 下货站 */
    private String downSite;

    /** 驶离时间 */
    @Excel(name = "驶离霍尔果斯时间")
    private String leaveTime;

    /** 换装时间 */
    @Excel(name = "换装/日期")
    private String changeTime;

    /** 换装车板号 */
    @Excel(name = "换装车号")
    private String changeNum;

    /** 运踪信息 */
    @Excel(name = "运踪内容")
    private String abroadContents;

    /** 运踪时间 */
    private String trackTime;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    //出入境日期
    private String portDate;

    /** 货物品名 */
    private String goodsName;
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("classId", getClassId())
            .append("orderNumber", getOrderNumber())
            .append("boxNum", getBoxNum())
            .append("boxType", getBoxType())
            .append("customerEmails", getCustomerEmails())
            .append("documentaryEmails", getDocumentaryEmails())
            .append("businessEmails", getBusinessEmails())
            .append("downSite", getDownSite())
            .append("departureDate", getDepartureDate())
            .append("leaveTime", getLeaveTime())
            .append("changeTime", getChangeTime())
            .append("changeNum", getChangeNum())
            .append("abroadContents", getAbroadContents())
            .append("trackTime", getTrackTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("portDate", getPortDate())
            .toString();
    }
}
