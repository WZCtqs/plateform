package com.szhbl.project.trains.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

/**
 * 班列对象 busi_classes
 * 
 * @author dps
 * @date 2020-01-13
 */
@Data
public class BusiClasses
{
    private static final long serialVersionUID = 1L;

    /** 班列id，主键 */
    private String classId;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String classBh;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "线路类型：0是中欧2是中亚3是中越4是中俄")
    private Long lineTypeid;

    /** 线路ID */
    @Excel(name = "线路ID")
    private String lineId;

    /** 线路ID */
    @Excel(name = "线路名称")
    private String nameCn;

    /** 班列模板ID */
    @Excel(name = "班列模板ID")
    private String classTId;

    /** 班列号 */
    @Excel(name = "班列号")
    private String classNumber;

    /** 开行班列 */
    @Excel(name = "开行班列")
    private String classBlocktrain;

    /** 班列类型 */
    @Excel(name = "班列类型")
    private String classClasstype;

    /** 0为去(西向) 1为回(东向） */
    @Excel(name = "0为去(西向) 1为回(东向）")
    private String classEastandwest;

    /** 始发站编号 */
    @Excel(name = "始发站编号")
    private String classStationofdeparture;

    /** 目的站编号 */
    @Excel(name = "目的站编号")
    private String classStationofdestination;

    /** 始发站名称 */
    @Excel(name = "始发站名称")
    private String classStationofdepartureName;

    /** 目的站名称 */
    @Excel(name = "目的站名称")
    private String classStationofdestinationName;

    /** 运行时间 */
    @Excel(name = "运行时间")
    private String classTransporttime;

    /** 班列是否接受申请 1是 0否 */
    @Excel(name = "班列是否接受申请(是否发布) 1是 0否")
    private String ispublicly;

    /** 舱位总数 */
    @Excel(name = "舱位总数")
    private Long classSpacenumber;

    /** 整箱舱位数 */
    @Excel(name = "整箱舱位数")
    private Long zxcnt;

    /** 拼箱状态（0是未满1是已满） */
    @Excel(name = "拼箱状态", readConverterExp = "0=是未满1是已满")
    private String pxstate;

    /** 拼箱体积数 */
    @Excel(name = "拼箱体积数")
    private Long pxcnt;

    /** 拼箱重量数 */
    @Excel(name = "拼箱重量数")
    private Long pxkgs;

    /** 计划开车时间 */
    @Excel(name = "计划开车时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classStime;
    private Date classStimeEnd;

    /** 计划到站时间 */
    @Excel(name = "计划到站时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classEtime;

    /** 车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列 */
    @Excel(name = "车辆状态：0未开车 1已开车 2已到终点站3取消班列4中亚实际发运班列")
    private String classState;

    /** 整柜随机接货站点编号 */
    @Excel(name = "整柜随机接货站点编号")
    private String receiveSiteFull;

    /** 拼箱随机接货站点编号 */
    @Excel(name = "拼箱随机接货站点编号")
    private String receiveSiteLcl;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private String createuserid;

    /** 创建用户姓名 */
    @Excel(name = "创建用户姓名")
    private String createusername;

    /** 备注 */
    private String remark;

    /** 费用是否可修改 0可修改 1不可修改（默认可修改） */
    @Excel(name = "费用是否可修改 0可修改 1不可修改", readConverterExp = "默=认可修改")
    private Long costentryState;

    /** 当天录运踪的时间（每天都在更新） */
    @Excel(name = "当天录运踪的时间", readConverterExp = "每=天都在更新")
    private String distributionTime;

    /** 控制结算模块箱号录入节点 0可修改 1不可修改（默认可修改） */
    @Excel(name = "控制结算模块箱号录入节点 0可修改 1不可修改", readConverterExp = "默=认可修改")
    private Long xhentryState;

    /** 管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改（默认不可修改） */
    @Excel(name = "管理部控制拼箱录入拼箱体积表0是不可修改，1是可修改", readConverterExp = "默认不可修改")
    private Long pxentryState;

    /** 美元转人民币 */
    @Excel(name = "美元转人民币")
    private Double usdtormbe;

    /** 欧元转人民币 */
    @Excel(name = "欧元转人民币")
    private Double eurtormbe;

    /** 欧元转美金 */
    @Excel(name = "欧元转美金")
    private Double euttousde;

    /** 卢布转人民币 */
    @Excel(name = "卢布转人民币")
    private Double rurtormbe;

    /** 是否删除 */
    @Excel(name = "是否删除 0否 1是")
    private String isDelete;

    /** 删除的id */
    @Excel(name = "删除的id")
    private String deleteIdStr;

    /** 当前时间 */
    @Excel(name = "当前时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date nowDate;

    /** 整柜随机接货站点编号 */
    @Excel(name = "整柜随机接货站点编号数组")
    private String[] receiveSiteFullArr;

    /** 拼箱随机接货站点编号 */
    @Excel(name = "拼箱随机接货站点编号数组")
    private String[] receiveSiteLclArr;

    /** 口岸编号 */
    @Excel(name = "口岸编号")
    private String classPortNum;

    /** 是否可选 */
    @Excel(name = "是否可选")
    private String isChoose;

    @Override
    public String toString() {
        return "BusiClasses{" +
                "classId='" + classId + '\'' +
                ", classBh='" + classBh + '\'' +
                ", lineTypeid=" + lineTypeid +
                ", lineId='" + lineId + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", classTId='" + classTId + '\'' +
                ", classNumber='" + classNumber + '\'' +
                ", classBlocktrain='" + classBlocktrain + '\'' +
                ", classClasstype='" + classClasstype + '\'' +
                ", classEastandwest='" + classEastandwest + '\'' +
                ", classStationofdeparture='" + classStationofdeparture + '\'' +
                ", classStationofdestination='" + classStationofdestination + '\'' +
                ", classStationofdepartureName='" + classStationofdepartureName + '\'' +
                ", classStationofdestinationName='" + classStationofdestinationName + '\'' +
                ", classTransporttime='" + classTransporttime + '\'' +
                ", ispublicly='" + ispublicly + '\'' +
                ", classSpacenumber=" + classSpacenumber +
                ", zxcnt=" + zxcnt +
                ", pxstate='" + pxstate + '\'' +
                ", pxcnt=" + pxcnt +
                ", pxkgs=" + pxkgs +
                ", classStime=" + classStime +
                ", classStimeEnd=" + classStimeEnd +
                ", classEtime=" + classEtime +
                ", classState='" + classState + '\'' +
                ", receiveSiteFull='" + receiveSiteFull + '\'' +
                ", receiveSiteLcl='" + receiveSiteLcl + '\'' +
                ", createdate=" + createdate +
                ", createuserid='" + createuserid + '\'' +
                ", createusername='" + createusername + '\'' +
                ", remark='" + remark + '\'' +
                ", costentryState=" + costentryState +
                ", distributionTime='" + distributionTime + '\'' +
                ", xhentryState=" + xhentryState +
                ", pxentryState=" + pxentryState +
                ", usdtormbe=" + usdtormbe +
                ", eurtormbe=" + eurtormbe +
                ", euttousde=" + euttousde +
                ", rurtormbe=" + rurtormbe +
                ", isDelete='" + isDelete + '\'' +
                ", deleteIdStr='" + deleteIdStr + '\'' +
                ", nowDate=" + nowDate +
                ", receiveSiteFullArr=" + Arrays.toString(receiveSiteFullArr) +
                ", receiveSiteLclArr=" + Arrays.toString(receiveSiteLclArr) +
                ", classPortNum='" + classPortNum + '\'' +
                ", isChoose='" + isChoose + '\'' +
                '}';
    }
}
