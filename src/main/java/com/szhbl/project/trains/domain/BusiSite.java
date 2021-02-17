package com.szhbl.project.trains.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 站点对象 busi_site
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public class BusiSite
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 国家代码 */
    @Excel(name = "国家代码")
    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    /** 所在国家中文名 */
    @Excel(name = "所在国家中文名")
    @ApiModelProperty(value = "所在国家中文名")
    private String countryCn;

    /** 所在国家英文名 */
    @Excel(name = "所在国家英文名")
    @ApiModelProperty(value = "所在国家英文名")
    private String countryEn;

    /** 城市代码 */
    @Excel(name = "城市代码")
    @ApiModelProperty(value = "城市代码")
    private String code;

    /** 城市中文名 */
    @Excel(name = "城市中文名")
    @ApiModelProperty(value = "城市中文名")
    private String nameCn;

    /** 城市英文名 */
    @Excel(name = "城市英文名")
    @ApiModelProperty(value = "城市英文名")
    private String nameEn;

    /** 0是中欧2是中亚3是中越4是中俄 */
    @Excel(name = "0是中欧2是中亚3是中越4是中俄")
    @ApiModelProperty(value = "线路类型 0是中欧2是中亚3是中越4是中俄")
    private String lineTypeid;

    /** 0禁用  1启用 */
    @Excel(name = "0禁用  1启用")
    @ApiModelProperty(value = "状态：0禁用  1启用")
    private String state;

    /** 0禁用  1启用 */
    @Excel(name = "用途 城市/站点")
    @ApiModelProperty(value = "用途 城市/站点")
    private String type;

    /** 城市中文名 */
    @Excel(name = "时区")
    @ApiModelProperty(value = "时区")
    private String timeZone;

    /** 服务：0门到门 1门到站 2站到站 3站到门 */
    @Excel(name = "服务：0门到门 1门到站 2站到站 3站到门")
    @ApiModelProperty(value = "服务：0门到门 1门到站 2站到站 3站到门")
    private String bookingService;

    /** 整拼箱 0整柜 1拼箱 */
    @Excel(name = "整拼箱 0整柜 1拼箱")
    @ApiModelProperty(value = "整拼箱 0整柜 1拼箱")
    private String goodsType;

    /** 城市中文名 */
    @Excel(name = "去回程 0为去程 1为回程")
    @ApiModelProperty(value = "去回程 0为去程 1为回程")
    private String eastWest;

    /** 城市代码列表 */
    @Excel(name = "城市代码列表")
    @ApiModelProperty(value = "城市代码列表")
    private String siteCodes;

    /** 口岸编号 */
    @Excel(name = "口岸编号")
    @ApiModelProperty(value = "口岸编号")
    private String portCode;

    /** 口岸编号 */
    @Excel(name = "选择以外口岸编号")
    @ApiModelProperty(value = "选择以外口岸编号")
    private String[] otherPortCode;

    /** 修改人 */
    @Excel(name = "修改人")
    @ApiModelProperty(value = "修改人")
    private String updatename;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatetime;

    /** 1-国内 0-国外 */
    @Excel(name = "1-国内 0-国外")
    @ApiModelProperty(value = "1-国内 0-国外")
    private String intra;

    public void setIntra(String intra) 
    {
        this.intra = intra;
    }

    public String getIntra() 
    {
        return intra;
    }

    public void setSiteCodes(String siteCodes) 
    {
        this.siteCodes = siteCodes;
    }
    public String getSiteCodes() 
    {
        return siteCodes;
    }

    public void setOtherPortCode(String[] otherPortCode)
    {
        this.otherPortCode = otherPortCode;
    }
    public String[] getOtherPortCode()
    {
        return otherPortCode;
    }

    public void setPortCode(String portCode)
    {
        this.portCode = portCode;
    }
    public String getPortCode()
    {
        return portCode;
    }

    public void setUpdatename(String updatename)
    {
        this.updatename = updatename;
    }
    public String getUpdatename()
    {
        return updatename;
    }

    public void setUpdatetime(Date updatetime){this.updatetime = updatetime; }
    public Date getUpdatetime(){return updatetime;}

    public void setId(Long id) 
    {
        this.id = id;
    }
    public Long getId() 
    {
        return id;
    }

    public void setCountryCode(String countryCode) 
    {
        this.countryCode = countryCode;
    }

    public String getCountryCode() 
    {
        return countryCode;
    }
    public void setCountryCn(String countryCn) 
    {
        this.countryCn = countryCn;
    }

    public String getCountryCn() 
    {
        return countryCn;
    }
    public void setCountryEn(String countryEn) 
    {
        this.countryEn = countryEn;
    }

    public String getCountryEn() 
    {
        return countryEn;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setNameCn(String nameCn) 
    {
        this.nameCn = nameCn;
    }

    public String getNameCn() 
    {
        return nameCn;
    }
    public void setNameEn(String nameEn) 
    {
        this.nameEn = nameEn;
    }

    public String getNameEn() 
    {
        return nameEn;
    }

    public void setLineTypeid(String lineTypeid)
    {
        this.lineTypeid = lineTypeid;
    }
    public String getLineTypeid()
    {
        return lineTypeid;
    }

    public void setState(String state){ this.state = state; }
    public String getState(){return state;}

    public void setType(String type){ this.type = type; }
    public String getType(){return type;}

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }
    public String getTimeZone()
    {
        return timeZone;
    }

    public void setBookingService(String bookingService)
    {
        this.bookingService = bookingService;
    }
    public String getBookingService()
    {
        return bookingService;
    }

    public void setGoodsType(String goodsType)
    {
        this.goodsType = goodsType;
    }
    public String getGoodsType()
    {
        return goodsType;
    }

    public void setEastWest(String eastWest)
    {
        this.eastWest = eastWest;
    }
    public String getEastWest(){return eastWest;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("siteCodes", getSiteCodes())
            .append("portCode", getPortCode())
            .append("otherPortCode", getOtherPortCode())
            .append("intra", getIntra())
            .append("updatename", getUpdatename())
            .append("updatetime", getUpdatetime())
            .append("countryCode", getCountryCode())
            .append("countryCn", getCountryCn())
            .append("countryEn", getCountryEn())
            .append("code", getCode())
            .append("nameCn", getNameCn())
            .append("nameEn", getNameEn())
            .append("lineTypeid", getLineTypeid())
            .append("state", getState())
            .append("type", getType())
            .append("bookingService", getBookingService())
            .append("goodsType", getGoodsType())
            .append("eastWest", getEastWest())
            .append("timeZone", getTimeZone())
            .toString();
    }
}
