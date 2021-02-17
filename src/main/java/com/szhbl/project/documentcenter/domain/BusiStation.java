package com.szhbl.project.documentcenter.domain;

import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 场站地址对象 busi_station
 *
 * @author szhbl
 * @date 2020-01-08
 */
@Data
public class BusiStation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String stationId;

    /** 上货站唯一码 */
    @Excel(name = "上货站唯一码")
    private String classCode;

    /** 上货站名称 */
    @Excel(name = "上货站名称")
    private String classSite;

    /** 车站名称 */
    @Excel(name = "车站名称")
    private String statioin;

    /** 截港时间 */
    @Excel(name = "截港时间")
    private String cuntofftime;

    /** 整箱地址 */
    @Excel(name = "整箱地址")
    private String zxAddress;

    /** 车站地址 */
    @Excel(name = "车站地址")
    private String pxYstype;

    /** 拼箱地址 */
    @Excel(name = "拼箱地址")
    private String pxAddress;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contacts;

    /** 0 去程 1回程 */
    @Excel(name = "0 去程 1回程")
    private String isconsolidation;

    /** 创建日期 */
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createuser;

    /** 默认0，启用，1是禁用 */
    @Excel(name = "默认0，启用，1是禁用")
    private String isenabled;

    @Excel(name = "是否报关")
    private String isReport;

    /** 0，整箱报关，1 不选 */
    @Excel(name = "整箱报关")
    private Integer fclCustoms;

    /** 0，整箱不报关，1 不选 */
    @Excel(name = "整箱不报关")
    private Integer fclCustomsNot;

    /** 0，拼箱报关，1 不选 */
    @Excel(name = "拼箱报关")
    private Integer lclCustoms;

    /** 0，拼箱不报关，1 不选 */
    @Excel(name = "拼箱不报关")
    private Integer lclCustomsNot;

    @Excel(name = "整箱报关截港时间")
    private String fclCustomsTime;

    @Excel(name = "整箱不报关截港时间")
    private String fclCustomsNotTime;

    @Excel(name = "拼箱报关截港时间")
    private String lclCustomsTime;

    @Excel(name = "拼箱不报关截港时间")
    private String lclCustomsNotTime;

    @Override
    public String toString() {
        return "BusiStation{" +
                "stationId='" + stationId + '\'' +
                ", classCode='" + classCode + '\'' +
                ", classSite='" + classSite + '\'' +
                ", statioin='" + statioin + '\'' +
                ", cuntofftime='" + cuntofftime + '\'' +
                ", zxAddress='" + zxAddress + '\'' +
                ", pxYstype='" + pxYstype + '\'' +
                ", pxAddress='" + pxAddress + '\'' +
                ", contacts='" + contacts + '\'' +
                ", isconsolidation='" + isconsolidation + '\'' +
                ", createdate=" + createdate +
                ", createuser='" + createuser + '\'' +
                ", isenabled='" + isenabled + '\'' +
                ", isReport='" + isReport + '\'' +
                ", fclCustoms=" + fclCustoms +
                ", fclCustomsNot=" + fclCustomsNot +
                ", lclCustoms=" + lclCustoms +
                ", lclCustomsNot=" + lclCustomsNot +
                ", fclCustomsTime='" + fclCustomsTime + '\'' +
                ", fclCustomsNotTime='" + fclCustomsNotTime + '\'' +
                ", lclCustomsTime='" + lclCustomsTime + '\'' +
                ", lclCustomsNotTime='" + lclCustomsNotTime + '\'' +
                '}';
    }
}
