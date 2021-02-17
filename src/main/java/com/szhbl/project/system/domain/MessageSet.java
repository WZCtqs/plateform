package com.szhbl.project.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 消息设置对象 message_set
 * 
 * @author lzd
 * @date 2020-04-17
 */
@Data
public class MessageSet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 消息类型 0单证 1其它 */
    @Excel(name = "消息类型 0单证 1其它")
    private Integer messageType;

    /** 邮件id */
    private Long emailId;

    /** 单证类型id */
    private Integer documentId;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String typeName;

    /** 邮件模板主题 */
    private String emailSubject;

    /** 邮件模板收件人 */
    private String emailRecipient;

    /** 邮件模板内容 */
    private String emailContent;

    /** 站内模板主题 */
    private String siteSubject;

    /** 站内模板收件人 */
    private String siteRecipient;

    /** 站内模板内容 */
    private String siteContent;

    /** 消息发送是否选用邮件 0是 1否*/
    private Integer emailSend;
    /** 消息发送是否选用站内信 0是 1否*/
    private Integer siteSend;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("messageType", getMessageType())
            .append("emailId", getEmailId())
            .append("documentId", getDocumentId())
            .append("typeName", getTypeName())
            .append("emailSubject", getEmailSubject())
            .append("emailRecipient", getEmailRecipient())
            .append("emailContent", getEmailContent())
            .append("siteSubject", getSiteSubject())
            .append("siteRecipient", getSiteRecipient())
            .append("siteContent", getSiteContent())
            .append("emailSend", getEmailSend())
            .append("siteSend", getSiteSend())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
