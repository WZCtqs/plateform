package com.szhbl.project.documentcenter.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 订单单证提醒设置对象 doc_new_remind
 *
 * @author szhbl
 * @date 2020-03-30
 */
@Data
public class DocNewRemind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 单证类型 */
    @Excel(name = "单证类型")
    private String fileTypeKey;

    /** 到期提醒时间 */
    @Excel(name = "到期提醒时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date remindTime;

    /** 删除标志*/
    private Integer deleteflag;

    /**订单编号*/
    private String orderNumber;

    /**班列日期*/
    private Date classDate;

    /**更改日期加减*/
    private Integer newRemind;

    private Date exameTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("fileTypeKey", getFileTypeKey())
            .append("remindTime", getRemindTime())
            .toString();
    }
}
