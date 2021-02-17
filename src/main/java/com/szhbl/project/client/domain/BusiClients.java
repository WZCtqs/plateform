package com.szhbl.project.client.domain;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户管理对象 busi_clients
 * 
 * @author jhm
 * @date 2020-01-06
 */
public class BusiClients extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private String clientId;

    /** 法人代表 */
    @Excel(name = "法人代表")
    private String clientLegalperson;

    /** 法人代码证 */
    @Excel(name = "组织机构代码")
    private String clientCode;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String clientUnit;

    /** 单位地址 */
    @Excel(name = "单位地址")
    private String clientAddress;


    /** 联系人 */
    @Excel(name = "订舱人")
    private String clientContacts;

    /** 联系电话1 */
    @Excel(name = "座机号")
    private String clientTel;



    /** 部门 */
    @Excel(name = "部门")
    private String clientDept;

    /** 职位 */
    @Excel(name = "职位")
    private String clientZw;

    /** 联系电话2 */
    @Excel(name = "手机号")
    private String clientPhone;

    /**
     * 邮件
     */
    @Excel(name = "订舱人邮件")
    private String clientEmail;

    /**
     * 推荐人
     */
    @Excel(name = "郑州陆港推荐人")
    private String clientTjr;

    /**
     * varchar	推荐人ID
     */
    private String clientTjrId;

    /**
     * 营业执照
     */
    @Excel(name = "营业执照")
    private String clientLicense;

    /**
     * 西向跟单员姓名
     */
    @Excel(name = "西向跟单员姓名")
    private String wMerchandiser;

    /**
     * 东向跟单员姓名
     */
    @Excel(name = "东向跟单员姓名")
    private String eMerchandiser;

    /*0:西向跟单员清空--适用于平台端修改客户时使用*/
    private Integer wMerclear;
    /*0:东向跟单员清空--适用于平台端修改客户时使用*/
    private Integer eMerclear;

    /**
     * 审核状态
     */
    @Excel(name = "是否审核", readConverterExp = "0=待审核,1=审核通过,2=审核不通过")
    private String isexamline;

    /**
     * 帐户状态（0启用 1锁定 2删除 3注销 ）
     *  //锁定之后可以再启用，不能注册
     */
    @Excel(name = "账号状态", readConverterExp = "0=启用,1=锁定,2=账号删除,3=注销")
    private String cancelaccount;

    /**
     * 是否失信（0否，1是）
     */
    @Excel(name = "是否失信", readConverterExp = "0=否,1=是")
    private String isNormal;

    /**
     * 是否直客
     */
    @Excel(name = "单位属性", readConverterExp = "0=货代,1=直客,2=其他")
    private String isDirect;

    /**
     * 是否签订合同（0否，1是）
     */
    @Excel(name = "是否签署《国际运输物流服务合同》", readConverterExp = "0=否,1=是")
    private String isSign;


    /**
     * 税务登记证
     */

    private String clientTax;

    /**
     * 登录帐号
     */

    private String clientLoginuser;

    /** 登录密码 */

    private String clientLoginpwd;

    /** 有效开始时间 */
    @Excel(name = "有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date clientValiditydate;

    /** 到期时间 */
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date clientDisableddate;

    /** 审核人ID */
    private String clientExampersonid;

    /** 审核人 */
    private String clientExamperson;

    /** 用户iD */
    private String userinfoId;

    /** 用户名称 */
    private String userinfoName;

    /** 审核信息 */
    private String examinfo;






    /** 创建时间 */
    private Date createdate;

    /** 创建人ID */
    private String createuserid;

    /** 创建人姓名 */
    private String createusername;

    /** $column.columnComment */
    private String clientJb;

    /** 国内外客户，0是国内，1国外 */
    private String engChinese;

    /** $column.columnComment */
    private String enPrename;

    /** $column.columnComment */

    private String enGender;

    /** $column.columnComment */

    private String enNo;

    /** $column.columnComment */

    private String enPlace;

    /** $column.columnComment */

    private String registerPhone;

    /** $column.columnComment */

    private String alias;

    /** $column.columnComment */

    private String state;



    /** 西向跟单员id */
    private String wMerchandiserId;



    /** 东向跟单员id */
    private String eMerchandiserId;



    /** $column.columnComment */
    private String isVip;

    /** $column.columnComment */
    private Date signDate;

    /** $column.columnComment */
    private Date signDisableddate;

    /** $column.columnComment */
    private String clientGrade;

    /** $column.columnComment */
    private String totalturncountavg;
    /**
     * 老赖标记(0,是，1否）
     */
    private String deadBeatType;

    /**
     * 西向跟单工号
     */
    private String wMerchandiserNumber;
    /**
     * 东向跟单工号
     */
    private String eMerchandiserNumber;
    /**
     * 注册开始时间
     */
    private String registerStartTime;
    /**
     * 注册结束时间
     */
    private String registerEndTime;

    private String clientIds;//以逗号隔开

    private String[] clients;

    /**
     * 客户列表权限
     *
     */

    public String readType;//0，按对应部门编号精准查询,1,2,3,4,5,6
    /**
     * 部门编号
     */
    public String deptCode;
    public String userid;
    public String deptName;
    public String nickName;

    //权限类型，0所有权限，1只有询价权限
    public Integer powerType;

    //合同编号，contract_num
    public String contractNum;

    //合同名称，contract_name
    public String contractName;

    //合同主题，contract_subject
    public String contractSubject;

    //合同有效期，contract_time
    public String contractTime;

    //到期提醒时间，remind_time
    public String remindTime;

    //合同状态,,0正常，1即将到期，2过期，contract_status
    public Integer contractStatus;

    //合同附件地址，contract_annex
    public String contractAnnex;

    //合同备注，contract_remark
    public String contractRemark;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractSubject() {
        return contractSubject;
    }

    public void setContractSubject(String contractSubject) {
        this.contractSubject = contractSubject;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractAnnex() {
        return contractAnnex;
    }

    public void setContractAnnex(String contractAnnex) {
        this.contractAnnex = contractAnnex;
    }

    public String getContractRemark() {
        return contractRemark;
    }

    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }
    public String getUserid()
    {
        return userid;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientId()
    {
        return clientId;
    }
    public void setClientUnit(String clientUnit)
    {
        this.clientUnit = clientUnit;
    }

    public String getClientUnit()
    {
        return clientUnit;
    }
    public void setClientAddress(String clientAddress)
    {
        this.clientAddress = clientAddress;
    }

    public String getClientAddress()
    {
        return clientAddress;
    }
    public void setClientLegalperson(String clientLegalperson)
    {
        this.clientLegalperson = clientLegalperson;
    }

    public String getClientLegalperson()
    {
        return clientLegalperson;
    }
    public void setClientContacts(String clientContacts)
    {
        this.clientContacts = clientContacts;
    }

    public String getClientContacts()
    {
        return clientContacts;
    }
    public void setClientTel(String clientTel)
    {
        this.clientTel = clientTel;
    }

    public String getClientTel()
    {
        return clientTel;
    }
    public void setClientPhone(String clientPhone)
    {
        this.clientPhone = clientPhone;
    }

    public String getClientPhone()
    {
        return clientPhone;
    }
    public void setClientEmail(String clientEmail)
    {
        this.clientEmail = clientEmail;
    }

    public String getClientEmail()
    {
        return clientEmail;
    }
    public void setClientDept(String clientDept)
    {
        this.clientDept = clientDept;
    }

    public String getClientDept()
    {
        return clientDept;
    }
    public void setClientZw(String clientZw)
    {
        this.clientZw = clientZw;
    }

    public String getClientZw()
    {
        return clientZw;
    }
    public void setClientTjr(String clientTjr)
    {
        this.clientTjr = clientTjr;
    }

    public String getClientTjr()
    {
        return clientTjr;
    }
    public void setClientTjrId(String clientTjrId)
    {
        this.clientTjrId = clientTjrId;
    }

    public String getClientTjrId()
    {
        return clientTjrId;
    }
    public void setClientLicense(String clientLicense)
    {
        this.clientLicense = clientLicense;
    }

    public String getClientLicense()
    {
        return clientLicense;
    }
    public void setClientCode(String clientCode)
    {
        this.clientCode = clientCode;
    }

    public String getClientCode()
    {
        return clientCode;
    }
    public void setClientTax(String clientTax)
    {
        this.clientTax = clientTax;
    }

    public String getClientTax()
    {
        return clientTax;
    }
    public void setClientLoginuser(String clientLoginuser)
    {
        this.clientLoginuser = clientLoginuser;
    }

    public String getClientLoginuser()
    {
        return clientLoginuser;
    }
    public void setClientLoginpwd(String clientLoginpwd)
    {
        this.clientLoginpwd = clientLoginpwd;
    }

    public String getClientLoginpwd()
    {
        return clientLoginpwd;
    }
    public void setClientValiditydate(Date clientValiditydate)
    {
        this.clientValiditydate = clientValiditydate;
    }

    public Date getClientValiditydate()
    {
        return clientValiditydate;
    }
    public void setClientDisableddate(Date clientDisableddate)
    {
        this.clientDisableddate = clientDisableddate;
    }

    public Date getClientDisableddate()
    {
        return clientDisableddate;
    }
    public void setClientExampersonid(String clientExampersonid)
    {
        this.clientExampersonid = clientExampersonid;
    }

    public String getClientExampersonid()
    {
        return clientExampersonid;
    }
    public void setClientExamperson(String clientExamperson)
    {
        this.clientExamperson = clientExamperson;
    }

    public String getClientExamperson()
    {
        return clientExamperson;
    }
    public void setUserinfoId(String userinfoId)
    {
        this.userinfoId = userinfoId;
    }

    public String getUserinfoId()
    {
        return userinfoId;
    }
    public void setUserinfoName(String userinfoName)
    {
        this.userinfoName = userinfoName;
    }

    public String getUserinfoName()
    {
        return userinfoName;
    }
    public void setExaminfo(String examinfo)
    {
        this.examinfo = examinfo;
    }

    public String getExaminfo()
    {
        return examinfo;
    }
    public void setIsexamline(String isexamline)
    {
        this.isexamline = isexamline;
    }

    public String getIsexamline()
    {
        return isexamline;
    }
    public void setCancelaccount(String cancelaccount)
    {
        this.cancelaccount = cancelaccount;
    }

    public String getCancelaccount()
    {
        return cancelaccount;
    }
    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate()
    {
        return createdate;
    }
    public void setCreateuserid(String createuserid)
    {
        this.createuserid = createuserid;
    }

    public String getCreateuserid()
    {
        return createuserid;
    }
    public void setCreateusername(String createusername)
    {
        this.createusername = createusername;
    }

    public String getCreateusername()
    {
        return createusername;
    }
    public void setClientJb(String clientJb)
    {
        this.clientJb = clientJb;
    }

    public String getClientJb()
    {
        return clientJb;
    }
    public void setEngChinese(String engChinese)
    {
        this.engChinese = engChinese;
    }

    public String getEngChinese()
    {
        return engChinese;
    }
    public void setEnPrename(String enPrename)
    {
        this.enPrename = enPrename;
    }

    public String getEnPrename()
    {
        return enPrename;
    }
    public void setEnGender(String enGender)
    {
        this.enGender = enGender;
    }

    public String getEnGender()
    {
        return enGender;
    }
    public void setEnNo(String enNo)
    {
        this.enNo = enNo;
    }

    public String getEnNo()
    {
        return enNo;
    }
    public void setEnPlace(String enPlace)
    {
        this.enPlace = enPlace;
    }

    public String getEnPlace()
    {
        return enPlace;
    }
    public void setRegisterPhone(String registerPhone)
    {
        this.registerPhone = registerPhone;
    }

    public String getRegisterPhone()
    {
        return registerPhone;
    }
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setIsNormal(String isNormal)
    {
        this.isNormal = isNormal;
    }

    public String getIsNormal()
    {
        return isNormal;
    }
    public void setIsDirect(String isDirect)
    {
        this.isDirect = isDirect;
    }

    public String getIsDirect()
    {
        return isDirect;
    }
    public void setIsSign(String isSign)
    {
        this.isSign = isSign;
    }

    public String getIsSign()
    {
        return isSign;
    }
    public void setwMerchandiserId(String wMerchandiserId)
    {
        this.wMerchandiserId = wMerchandiserId;
    }

    public String getwMerchandiserId()
    {
        return wMerchandiserId;
    }
    public void setwMerchandiser(String wMerchandiser)
    {
        this.wMerchandiser = wMerchandiser;
    }

    public String getwMerchandiser()
    {
        return wMerchandiser;
    }
    public void seteMerchandiserId(String eMerchandiserId)
    {
        this.eMerchandiserId = eMerchandiserId;
    }

    public String geteMerchandiserId()
    {
        return eMerchandiserId;
    }
    public void seteMerchandiser(String eMerchandiser)
    {
        this.eMerchandiser = eMerchandiser;
    }

    public String geteMerchandiser()
    {
        return eMerchandiser;
    }
    public void setIsVip(String isVip)
    {
        this.isVip = isVip;
    }

    public String getIsVip()
    {
        return isVip;
    }
    public void setSignDate(Date signDate)
    {
        this.signDate = signDate;
    }

    public Date getSignDate()
    {
        return signDate;
    }
    public void setSignDisableddate(Date signDisableddate)
    {
        this.signDisableddate = signDisableddate;
    }

    public Date getSignDisableddate()
    {
        return signDisableddate;
    }
    public void setClientGrade(String clientGrade)
    {
        this.clientGrade = clientGrade;
    }

    public String getClientGrade()
    {
        return clientGrade;
    }
    public void setTotalturncountavg(String totalturncountavg)
    {
        this.totalturncountavg = totalturncountavg;
    }

    public String getTotalturncountavg()
    {
        return totalturncountavg;
    }

    public String getDeadBeatType() {
        return deadBeatType;
    }

    public void setDeadBeatType(String deadBeatType) {
        this.deadBeatType = deadBeatType;
    }

    public String getwMerchandiserNumber() {
        return wMerchandiserNumber;
    }

    public void setwMerchandiserNumber(String wMerchandiserNumber) {
        this.wMerchandiserNumber = wMerchandiserNumber;
    }

    public String geteMerchandiserNumber() {
        return eMerchandiserNumber;
    }

    public void seteMerchandiserNumber(String eMerchandiserNumber) {
        this.eMerchandiserNumber = eMerchandiserNumber;
    }

    public String getRegisterStartTime() {
        return registerStartTime;
    }

    public void setRegisterStartTime(String registerStartTime) {
        this.registerStartTime = registerStartTime;
    }

    public String getRegisterEndTime() {
        return registerEndTime;
    }

    public void setRegisterEndTime(String registerEndTime) {
        this.registerEndTime = registerEndTime;
    }

    public String getClientIds() {
        return clientIds;
    }

    public void setClientIds(String clientIds) {
        this.clientIds = clientIds;
    }

    public String[] getClients() {
        return clients;
    }

    public void setClients(String[] clients) {
        this.clients = clients;
    }


    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getwMerclear() {
        return wMerclear;
    }

    public void setwMerclear(Integer wMerclear) {
        this.wMerclear = wMerclear;
    }

    public Integer geteMerclear() {
        return eMerclear;
    }

    public void seteMerclear(Integer eMerclear) {
        this.eMerclear = eMerclear;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("clientId", getClientId())
                .append("clientUnit", getClientUnit())
                .append("clientAddress", getClientAddress())
                .append("clientLegalperson", getClientLegalperson())
                .append("clientContacts", getClientContacts())
                .append("clientTel", getClientTel())
                .append("clientPhone", getClientPhone())
                .append("clientEmail", getClientEmail())
                .append("clientDept", getClientDept())
                .append("clientZw", getClientZw())
                .append("clientTjr", getClientTjr())
                .append("clientTjrId", getClientTjrId())
                .append("remark", getRemark())
                .append("clientLicense", getClientLicense())
                .append("clientCode", getClientCode())
                .append("clientTax", getClientTax())
                .append("clientLoginuser", getClientLoginuser())
                .append("clientLoginpwd", getClientLoginpwd())
                .append("clientValiditydate", getClientValiditydate())
                .append("clientDisableddate", getClientDisableddate())
                .append("clientExampersonid", getClientExampersonid())
                .append("clientExamperson", getClientExamperson())
                .append("userinfoId", getUserinfoId())
                .append("userinfoName", getUserinfoName())
                .append("examinfo", getExaminfo())
                .append("isexamline", getIsexamline())
                .append("cancelaccount", getCancelaccount())
                .append("createdate", getCreatedate())
                .append("createuserid", getCreateuserid())
                .append("createusername", getCreateusername())
                .append("clientJb", getClientJb())
                .append("engChinese", getEngChinese())
                .append("enPrename", getEnPrename())
                .append("enGender", getEnGender())
                .append("enNo", getEnNo())
                .append("enPlace", getEnPlace())
                .append("registerPhone", getRegisterPhone())
                .append("alias", getAlias())
                .append("state", getState())
                .append("isNormal", getIsNormal())
                .append("isDirect", getIsDirect())
                .append("isSign", getIsSign())
                .append("wMerchandiserId", getwMerchandiserId())
                .append("wMerchandiser", getwMerchandiser())
                .append("eMerchandiserId", geteMerchandiserId())
                .append("eMerchandiser", geteMerchandiser())
                .append("isVip", getIsVip())
                .append("signDate", getSignDate())
                .append("signDisableddate", getSignDisableddate())
                .append("clientGrade", getClientGrade())
                .append("totalturncountavg", getTotalturncountavg())
                .append("userid", getUserid())
                .append("powerType", getPowerType())
                .append("contractNum", getContractNum())
                .append("contractName", getContractName())
                .append("contractSubject", getContractSubject())
                .append("contractTime", getContractTime())
                .append("remindTime", getRemindTime())
                .append("contractStatus", getContractStatus())
                .append("contractAnnex", getContractAnnex())
                .append("contractRemark", getContractRemark())
                .toString();
    }
}
