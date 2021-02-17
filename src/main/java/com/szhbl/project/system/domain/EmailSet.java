package com.szhbl.project.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 邮件配置对象 email_set
 * 
 * @author lzd
 * @date 2020-04-17
 */
@Data
public class EmailSet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** SMTP服务器 */
    @Excel(name = "SMTP服务器")
    private String smtpSever;

    /** SMTP端口号 */
    @Excel(name = "SMTP端口号")
    private String smtpPort;

    /** 邮件发送认证 0需要 1不需要 */
    @Excel(name = "邮件发送认证 0需要 1不需要")
    private Integer emailAuthentication;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 删除标志0正常1删除 */
    private Integer delFlag;

    /** 去回程 0去 1回  */
    private Integer goCome;

    /** 拼整箱  0整柜 1拼箱 */
    private Integer consolidationType;

    /** 奇偶数 0偶数 1奇数 */
    private Integer oddEven;

    /** 客户和线上使用 0客户 1线上 */
    private Integer isCustom;

    /** 线路类型：0中欧中越 1中亚中俄  2异常箱 */
    private Integer lineType;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("smtpSever", getSmtpSever())
            .append("smtpPort", getSmtpPort())
            .append("emailAuthentication", getEmailAuthentication())
            .append("name", getName())
            .append("account", getAccount())
            .append("password", getPassword())
            .append("goCome", getGoCome())
            .append("consolidationType", getConsolidationType())
            .append("oddEven", getOddEven())
            .append("isCustom", getIsCustom())
            .append("lineType", getLineType())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
