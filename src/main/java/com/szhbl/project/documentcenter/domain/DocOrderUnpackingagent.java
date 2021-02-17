package com.szhbl.project.documentcenter.domain;

import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 拆箱代理数据对象 doc_order_unpackingagent
 *
 * @author szhbl
 * @date 2020-06-08
 */
@Data
public class DocOrderUnpackingagent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String OrderId;

    /** 班列日期 */
    @Excel(name = "班列日期")
    private String ClassDate    ;

    /** 班列编号 */
    @Excel(name = "班列编号")
    private String ClassNumber;

    /** 委托书编号 */
    @Excel(name = "委托书编号")
    private String OrderNumber;

    /** 委托书编号的样式 */
    @Excel(name = "委托书编号的样式")
    private String OrderNum_style;

    /** 箱号 */
    @Excel(name = "箱号")
    private String ContainerNum;

    /** ZIH推荐人 */
    @Excel(name = "ZIH推荐人")
    private String Referrer;

    /** 跟单员 */
    @Excel(name = "跟单员")
    private String Merchandiser;

    /** 费用 */
    @Excel(name = "费用")
    private String Cost;

    /** 箱型 */
    @Excel(name = "箱型")
    private String ContainerType;

    /** 货物中文品名 */
    @Excel(name = "货物中文品名")
    private String GoodsChsName;

    /** 二级站转成一级站 下货站 */
    @Excel(name = "二级站转成一级站 下货站")
    private String UnloadSite;

    /** 下货站 */
    @Excel(name = "下货站")
    private String UnloadSite2;

    /** 到站后由ZIH代理清关 */
    @Excel(name = "到站后由ZIH代理清关")
    private String IsClearance;

    /** 到站后由ZIH分拨方式 */
    @Excel(name = "到站后由ZIH分拨方式")
    private String IsAllocate;

    /** 实际包装方式 */
    @Excel(name = "实际包装方式")
    private String GoodsPackingMethod;

    /** 实到数量 */
    @Excel(name = "实到数量")
    private String GoodsNumber;

    /** 实测规格(长*宽*高*件数) */
    @Excel(name = "实测规格(长*宽*高*件数)")
    private String MeasuSpecifi;

    /** 实测重量 */
    @Excel(name = "实测重量")
    private String GoodsKGs;

    /** 实测毛重 */
    @Excel(name = "实测毛重")
    private String MeasuGroWei;

    /** 实测体积（m3） */
    @Excel(name = "实测体积", readConverterExp = "m=3")
    private String MeasuVolum;

    /** 求和（件数总和+体积总和+毛重总和） */
    @Excel(name = "求和", readConverterExp = "件=数总和+体积总和+毛重总和")
    private String Sum;

    /** 一级站代理 */
    @Excel(name = "一级站代理")
    private String PriStaStorAgen;

    /** 一级站代理Id */
    @Excel(name = "一级站代理Id")
    private String PriAgenId;

    /** 二级站代理 */
    @Excel(name = "二级站代理")
    private String SecStaStorAgen;

    /** 二级站代理Id */
    @Excel(name = "二级站代理Id")
    private String SecAgenId;

    /** 托书备注 */
    @Excel(name = "托书备注")
    private String Remark;

    /** 是否为拆分柜(1--是，0--否） */
    @Excel(name = "是否为拆分柜(1--是，0--否）")
    private String IsSplit;

    /**各个箱子代理报价详情*/
    private String AgentFee;

    /** 自填备注 */
    @Excel(name = "自填备注")
    private String Remark2;

}
