package com.szhbl.project.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 消息提醒对象 message
 *
 * @author szhbl
 * @date 2020-04-07
 */
@Data
public class SysMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id  */
    private Long id;

    /** 订单id*/
    private String orderId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date classDate;
    @Excel(name = "班列日期")
    private String cd;
    /** 客户id */
    private String clientId;

    /** 委托书编号 */
    @Excel(name = "委托书编号")
    private String orderNumber;

    /** 标题 */

    private String messageTitle;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String messageType;

    /** 内容 */

    private String messageContent;

    /** 状态 0 未处理 1已处理 */

    private String msgStatus;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 发送结果（0：成功 1：失败） */

    private Integer smsStatus;

    private Integer emailStatus;
    @Excel(name = "状态")
    private String zt;
    private String smsfail;

    private String emailfail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String fileTypeKey;
    @Override
    public String toString() {
        return "SysMessage{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                ", messageType='" + messageType + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", msgStatus='" + msgStatus + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", smsStatus=" + smsStatus +
                ", emailStatus=" + emailStatus +
                '}';
    }
}
