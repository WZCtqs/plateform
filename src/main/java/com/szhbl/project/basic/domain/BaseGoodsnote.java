package com.szhbl.project.basic.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;

import javax.validation.constraints.NotNull;

/**
 * 特殊单证物品对象 base_goodsnote
 * 
 * @author dps
 * @date 2020-01-15
 */
public class BaseGoodsnote
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 去回程：0西向 1东向  0|1东西向 */
    @Excel(name = "去回程：0西向 1东向  0|1东西向")
    @ApiModelProperty(value = "箱去回程：0西向 1东向  0|1东西向")
    @NotNull(message = "去回程不能为空")
    private String eastandwest;

    /** 货物品名 */
    @Excel(name = "货物品名")
    @ApiModelProperty(value = "货物品名")
    private String goodsname;

    /** 国内HS */
    @Excel(name = "国内HS")
    @ApiModelProperty(value = "国内HS")
    private String inhs;

    /** 上/下货站 */
    @Excel(name = "上/下货站")
    @ApiModelProperty(value = "上/下货站")
    private String unloadsite;

    /** 口岸 */
    @Excel(name = "口岸")
    @ApiModelProperty(value = "口岸")
    private String station;

    /** 特殊备注 */
    @Excel(name = "特殊备注")
    @ApiModelProperty(value = "特殊备注")
    private String specialnotes;

    /** 特殊备注 */
    @Excel(name = "特殊备注英文")
    @ApiModelProperty(value = "特殊备注")
    private String specialnotesEn;

    /** 放射性 否/是 */
    @Excel(name = "放射性 否/是")
    @ApiModelProperty(value = "放射性 否/是")
    private String radioaction;

    /** 商品类别0是特殊单证1是有色金属 */
    @Excel(name = "商品类别0是特殊单证1是有色金属")
    @ApiModelProperty(value = "商品类别 0特殊单证 1有色金属")
    @NotNull(message = "商品类别不能为空")
    private String goodsClass;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEastandwest(String eastandwest) 
    {
        this.eastandwest = eastandwest;
    }

    public String getEastandwest() 
    {
        return eastandwest;
    }
    public void setGoodsname(String goodsname) 
    {
        this.goodsname = goodsname;
    }

    public String getGoodsname() 
    {
        return goodsname;
    }
    public void setInhs(String inhs) 
    {
        this.inhs = inhs;
    }

    public String getInhs() 
    {
        return inhs;
    }
    public void setUnloadsite(String unloadsite) 
    {
        this.unloadsite = unloadsite;
    }

    public String getUnloadsite() 
    {
        return unloadsite;
    }
    public void setStation(String station) 
    {
        this.station = station;
    }

    public String getStation() 
    {
        return station;
    }

    public void setSpecialnotes(String specialnotes) 
    {
        this.specialnotes = specialnotes;
    }
    public String getSpecialnotes()
    {
        return specialnotes;
    }

    public void setSpecialnotesEn(String specialnotesEn)
    {
        this.specialnotesEn = specialnotesEn;
    }
    public String getSpecialnotesEn()
    {
        return specialnotesEn;
    }

    public void setRadioaction(String radioaction) 
    {
        this.radioaction = radioaction;
    }

    public String getRadioaction() 
    {
        return radioaction;
    }
    public void setGoodsClass(String goodsClass) 
    {
        this.goodsClass = goodsClass;
    }

    public String getGoodsClass() 
    {
        return goodsClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("eastandwest", getEastandwest())
            .append("goodsname", getGoodsname())
            .append("inhs", getInhs())
            .append("unloadsite", getUnloadsite())
            .append("station", getStation())
            .append("specialnotes", getSpecialnotes())
            .append("specialnotesEn", getSpecialnotesEn())
            .append("radioaction", getRadioaction())
            .append("goodsClass", getGoodsClass())
            .toString();
    }
}
