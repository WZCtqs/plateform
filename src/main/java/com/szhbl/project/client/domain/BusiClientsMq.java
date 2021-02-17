package com.szhbl.project.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BusiClientsMq {
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

    /** 邮件 */
    @Excel(name = "订舱人邮件")
    private String clientEmail;

    /** 推荐人 */
    @Excel(name = "郑州陆港推荐人")
    private String clientTjr;

    /** varchar	推荐人ID */
    private String clientTjrId;

    /** 营业执照 */
    @Excel(name = "营业执照")
    private String clientLicense;

    /** 西向跟单员姓名 */
    @Excel(name = "西向跟单员姓名")
    private String wMerchandiser;

    /** 东向跟单员姓名 */
    @Excel(name = "东向跟单员姓名")
    private String eMerchandiser;

    /** 审核状态 */
    @Excel(name = "是否审核")
    private String isexamline;

    /** 默认0 正常;1帐户注销;2账号删除 */
    @Excel(name = "账号状态")
    private String cancelaccount;

    /** 是否失信（0否，1是） */
    @Excel(name = "是否失信")
    private String isNormal;

    /** 是否直客 */
    @Excel(name = "单位属性")
    private String isDirect;

    /** 是否签订合同（0否，1是） */
    @Excel(name = "是否签署《国际运输物流服务合同》")
    private String isSign;


    /** 税务登记证 */

    private String clientTax;

    /** 登录帐号 */

    private String clientLoginuser;

    /** 登录密码 */

    private String clientLoginpwd;

    /** 有效开始时间 */
    @Excel(name = "有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date clientValiditydate;

    /** 到期时间 */
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date signDate;

    /** $column.columnComment */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
}
