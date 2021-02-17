package com.szhbl.project.system.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.szhbl.framework.aspectj.lang.annotation.Excel.Type;
import com.szhbl.framework.aspectj.lang.annotation.Excels;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author szhbl
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    private String dcUserId;


    /** 部门ID */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登陆IP */
    @Excel(name = "最后登陆IP", type = Type.EXPORT)
    private String loginIp;

    /** 最后登陆时间 */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    /*@JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginDate;

    /** 部门对象 */
    @Excels({
        @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    /** 岗位组 */
    private List<SysPost> posts;

    /** 工号 */
    /*@NotBlank*/
    @Excel(name = "工号")
    private String jobNumber;

    /** 推荐人id */
    private String tjrId;

    /** 推荐人下所有邮箱以逗号隔开 */
    private String referenceOtherMails;

    /** 推荐人状态(0,否，1是) */
    private String referenceType;
    /** 职位*/
    private String post;

    private String deptCode;//部门编号

    /** 开票申请人 */
    @Excel(name = "开票申请人")
    private String kpapplicant;

    /** 开票业务部门 */
    @Excel(name = "开票业务部门")
    private String kporganization;

    /** 开票跟单员 */
    @Excel(name = "开票跟单员")
    private String kpmerchandiser;

    /** 开票申请部门 */
    @Excel(name = "开票申请部门")
    private String kpapplydepartment;

    /** 开票申请人部门经理 */
    @Excel(name = "开票申请人部门经理")
    private String kpdepartmanager;

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public String getDcUserId() {
        return dcUserId;
    }

    public void setDcUserId(String dcUserId) {
        this.dcUserId = dcUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    //@Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 255, message = "邮箱长度不能超过255个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getTjrId() {
        return tjrId;
    }

    public void setTjrId(String tjrId) {
        this.tjrId = tjrId;
    }

    public String getReferenceOtherMails() {
        return referenceOtherMails;
    }

    public void setReferenceOtherMails(String referenceOtherMails) {
        this.referenceOtherMails = referenceOtherMails;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<SysPost> getPosts() {
        return posts;
    }

    public void setPosts(List<SysPost> posts) {
        this.posts = posts;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getKpapplicant() {
        return kpapplicant;
    }

    public void setKpapplicant(String kpapplicant) {
        this.kpapplicant = kpapplicant;
    }

    public String getKporganization() {
        return kporganization;
    }

    public void setKporganization(String kporganization) {
        this.kporganization = kporganization;
    }

    public String getKpmerchandiser() {
        return kpmerchandiser;
    }

    public void setKpmerchandiser(String kpmerchandiser) {
        this.kpmerchandiser = kpmerchandiser;
    }

    public String getKpapplydepartment() {
        return kpapplydepartment;
    }

    public void setKpapplydepartment(String kpapplydepartment) {
        this.kpapplydepartment = kpapplydepartment;
    }

    public String getKpdepartmanager() {
        return kpdepartmanager;
    }

    public void setKpdepartmanager(String kpdepartmanager) {
        this.kpdepartmanager = kpdepartmanager;
    }
}
