package com.szhbl.project.client.dto;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户管理对象 busi_clients
 * 
 * @author jhm
 * @date 2020-01-06
 */
@Data
public class BusiClientsDto extends BaseEntity
{

    @Excel(name = "客户名称")
    private String clientUnit;

    //合同编号，contract_num
    @Excel(name = "合同编号")
    public String contractNum;

    //合同名称，contract_name
    @Excel(name = "合同名称")
    public String contractName;

    //合同主题，contract_subject
    @Excel(name = "合同主题")
    public String contractSubject;

    /** 联系人 */
    @Excel(name = "联系人")
    private String clientContacts;

    @Excel(name = "业务部")
    public String deptName;

    @Excel(name = "推荐人")
    public String nickName;

    @Excel(name = "合同签订时间")
    private Date signDate;

    //合同有效期，contract_time
    @Excel(name = "合同有效时长")
    public String contractTime;

    @Excel(name = "合同到期时间")
    private Date signDisableddate;

    //到期提醒时间，remind_time
    @Excel(name = "到期提醒时间")
    public String remindTime;

    //合同状态,0正常，contract_status
    @Excel(name = "合同状态", readConverterExp = "0=正常,1=过期")
    public Integer contractStatus;

    //合同附件地址，contract_annex
    //@Excel(name = "订舱人")
    public String contractAnnex;

    //合同备注，contract_remark
    @Excel(name = "备注")
    public String contractRemark;




}

