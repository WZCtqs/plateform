package com.szhbl.project.kh.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import com.szhbl.project.client.domain.BusiClients;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户端用户对象 kh_user
 *
 * @author jhm
 * @date 2020-03-21
 */
public class KhUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 客户id
     */
    @Excel(name = "客户id")
    private String clientId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String userName;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 删除标志（0正常，2删除）
     */
    private String delFlag;

    private String permissionName;

    private Long mpId;

    private String jobNumber;

    /*订舱方邮箱*/
    private String orderEmail;
    /*联系人*/
    private String orderContacts;
    /*联系方式*/
    private String orderContactInfo;
    /*联系地址*/
    private String orderContactAddress;

    private BusiClients busiClients;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }

    public String getOrderContacts() {
        return orderContacts;
    }

    public void setOrderContacts(String orderContacts) {
        this.orderContacts = orderContacts;
    }

    public String getOrderContactInfo() {
        return orderContactInfo;
    }

    public void setOrderContactInfo(String orderContactInfo) {
        this.orderContactInfo = orderContactInfo;
    }

    public String getOrderContactAddress() {
        return orderContactAddress;
    }

    public void setOrderContactAddress(String orderContactAddress) {
        this.orderContactAddress = orderContactAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("clientId", getClientId())
                .append("userName", getUserName())
                .append("password", getPassword())
                .append("phone", getPhone())
                .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getMpId() {
        return mpId;
    }

    public void setMpId(Long mpId) {
        this.mpId = mpId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public BusiClients getBusiClients() {
        return busiClients;
    }

    public void setBusiClients(BusiClients busiClients) {
        this.busiClients = busiClients;
    }
}
