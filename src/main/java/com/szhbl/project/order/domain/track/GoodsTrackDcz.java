package com.szhbl.project.order.domain.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class GoodsTrackDcz {

    /** 序号 */
    @Excel(name = "序号",height = 18,width = 5)
    private Integer sort;

    /** 货物状态表id */
    private Integer id;

    /** 订单id */
    private String orderId;

    /** 班列日期 */
    @Excel(name = "班列日期",height = 18, width = 12, dateFormat = "yyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date classDate;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateStart;

    /** 班列日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date classDateEnd;

    /** 订舱组备注 */
    @Excel(name = "订舱组备注",height = 18, width = 15)
    private String classzyNo;

    /** 订舱组备注修改时间 */
    private Date classzynoTime;

    /** 班列号修改记录 */
    @Excel(name = "班列号修改记录",height = 18, width = 20)
    private String classzynoRemark;

    /** 班列编号 */
    @Excel(name = "班列编号",height = 18, width = 15)
    private String classBh;

    /** 口岸 */
    @Excel(name = "口岸",height = 18, width = 20)
    private String lineName;

    /** 订单号（委托书编号）（舱位号） */
    @Excel(name = "舱位号",height = 18, width = 20)
    private String orderNumber;

    /** 箱号 */
    @Excel(name = "箱号",height = 18, width = 12)
    private String boxNum;

    /**  整拼箱 0整柜 1拼箱 */
    @Excel(name = "整拼箱", readConverterExp = "0=整柜,1=拼箱",height = 18, width = 7)
    private String isconsolidation;

    /** 0为去程 1为回程 */
    private String classEastandwest;

    /** 集装箱箱量 */
    @Excel(name = " 箱量",height = 18, width = 5)
    private String containerBoxamount;

    /** 集装箱箱型 */
    @Excel(name = " 箱型 ",height = 18, width = 10)
    private String containerType;

    /** 集装箱箱属 :0委托ZIH 1自备 2自备铁路箱3 自备非铁路箱 */
    private String orderAuditBelongto;

    /**  入场日期*/
    @Excel(name = "入场日期",dateFormat = "yyyy-MM-dd",height = 18, width = 12)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inSpaceDate;

    /**  入场时间*/
    @Excel(name = " 入场时间",height = 18, width = 10)
    @ApiModelProperty(value = "入场时间")
    private String inSpaceTime;

    /** 拼箱部正面吊测偏结果 */
    private String cepianResult;

    /**  进站日期*/
    @Excel(name = "进站日期", dateFormat = "yyyy-MM-dd",height = 18, width = 12)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inStationDate;

    /**  进站时间*/
    @Excel(name = " 进站时间",height = 18, width = 10)
    private String inStationTime;

    /** 随车审核人 busi_zy_info*/
    @Excel(name = "随车审核人",height = 18, width = 10)
    private String scOperator;

    /** 随车状态 busi_zy_info*/
    @Excel(name = "随车状态",height = 18, width = 12)
    private String suichestate;

    /** 随车备注 busi_zy_info*/
    @Excel(name = "随车备注",height = 18, width = 20)
    private String scRemarkZy;

    /** 托书随车备注 track_goods_status*/
    private String scremark;

    /** 托书随车负责人 */
    private String scPrincipal;

    /** 报关员 busi_zy_info*/
    @Excel(name = "报关员",height = 18, width = 10)
    private String gwOperator; //不用
    private String bgPrincipal;

    /** 托书报关状态 track_goods_status*/
    @Excel(name = "托书报关状态",height = 18, width = 20)
    private String bgProgress;
    private List<Map> bgProgressList;

    /** 托书票数 track_goods_status*/
    @Excel(name = "托书票数",height = 18, width = 8)
    private String bgVotesNumber;

    /** 托书报关备注 track_goods_status*/
    @Excel(name = "托书报关备注",height = 18, width = 20)
    private String bgRemark;

    /** 关务状态 （未报关，报关单放行，报关单查验，出区异常，出区异常已放行） busi_zy_info */
    @Excel(name = "关务状态",height = 18, width = 15)
    private String guanwustate;
    private List<Map> guanwustateList;

    /** 票数 busi_zy_info*/
    @Excel(name = "票数",height = 18, width = 8)
    private String gwPiaoshu;

    /** 关务备注 busi_zy_info*/
    @Excel(name = "关务备注",height = 18, width = 20)
    private String gwRemark;

    /** 拼箱备注 busi_zy_info*/
    @Excel(name = "拼箱备注",height = 18, width = 20)
    private String bgremarkPx;

    /** 客户推荐人 */
    @Excel(name = "推荐人",height = 18, width = 15)
    private String clientTjr;

    /** 订舱方*/
    @Excel(name = "订舱方",height = 18, width = 20)
    private String clientUnit;

    /** 跟单员 */
    @Excel(name = "跟单员",height = 18, width = 15)
    private String orderMerchandiser;

    /** 货物品名 */
    @Excel(name = "品名",height = 18, width = 15)
    private String goodsName;

    /** 公路部是否办票 */
    private String roadIsbill;

    /** 公路预计到场时间 */
    @Excel(name = "提货预计到场时间", dateFormat = "yyyy-MM-dd",height = 20, width = 15)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planunloadtime;

    /** 封号 */
    private String fenghao;

    /** 封号更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date fhtime;

    /** 监管区更改后封号 */
    private String gwfenghao;

    /** 箱号校验） 0正确 1错误*/
    private String boxNumCheck;

    /** 下货站 */
    private String orderUnloadsite;

    /** 到货方式 委托ZIH提货 0是 1否  2铁路到货*/
    private String shipOrderBinningway;

    /** 箱重 */
    private String boxWeight;

    /** 运单箱重 */
    private Double yundanXweight;

    /** 线路类型：0是中欧2是中亚3是中越4是中俄 （班列表）*/
    private String lineTypeid;

    /** 托书状态 */
    private String isexamline;

    /** 总体积*/
    private String totalVolumn;

    /** 总重量 */
    private String totalWeight;

    /** 20尺箱子(箱量统计) */
    private String boxAmountSecond;

    /** 重量 */
    private String goodsKgs;

    /** 体积 */
    private String goodsCbm;

    /** 班列目的地 */
    private String classEndStationName;

    /** 限重 */
    private String maxLoad;

    /** 查询类型 0列表 1导出 */
    private String type;





























}
