package com.szhbl.project.documentcenter.domain.vo;

import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.DocOrderInstation;
import com.szhbl.project.documentcenter.domain.DocOrderUnpackingagent;
import com.szhbl.project.documentcenter.domain.LadingBill;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

/**
 * 单证页面托书list查询用
 */
@Data
@ApiModel(description = "订单单证列表")
public class OrderDocuments implements Serializable {

    /**
     * 订舱托书id
     */
    @ApiModelProperty(value = "订舱托书id")
    private String orderId;

    private String orderIds;

    /**
     * 班列编号
     */
    @ApiModelProperty(value = "班列编号")
    private String orderClassBh;

    /**
     * 托书编号
     */
    @ApiModelProperty(value = "托书编号")
    private String orderNumber;

    /**
     * 班列日期
     */
    @ApiModelProperty(value = "班列日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String classDate;

    /**
     * 班列方向 0：去程西向，1：回程东向
     */
    @ApiModelProperty(value = "班列方向 0：去程西向，1：回程东向")
    private String classEastAndWest;

    /**
     * 订舱方
     */
    @ApiModelProperty(value = "订舱方")
    private String clientUnit;

    private String clientId;

    /**
     * 整拼箱：0：整箱，1：拼箱
     */
    @ApiModelProperty(value = "整拼箱：0：整箱，1：拼箱")
    private String isConsolidation;
    // 0： 委托，1：不委托
    private String shipOrderBinningWay;
    //箱量
    private Integer containerCount;

    /**
     * 是否超节点
     */
    private Integer isBeyond;

    /**
     * 超节点单证
     */
    private List<BeyondDoc> beyondDocs;

    /**
     * 提货时间
     */
    private DocOrderDocument pickGoods;

    /**
     * 提单草单
     */
    private LadingBill ladingBillDraft;

    /**
     * 入仓核实单
     */
    private PxInBoxCheck pxInBoxCheck;

    /**
     * 重箱进站表
     */
    private List<PxYardLoadedIn> pxYardLoadedIn;

    /**
     * 装柜清单件重尺
     */
    private PxBoxingList pxBoxingList;

    /**
     * 拼箱出入库表
     */
    private PxGoodsInOut pxGoodsInOut;

    /**
     * 回程进站时间表
     */
    private DocOrderInstation instation;

    /**
     * 拆箱代理
     */
    private DocOrderUnpackingagent unpackingagent;
    /**
     * 国外场站到货数据
     */
    private GWCZArrivalGoods gwczArrivalGoods;

    /**
     * 数据权限查询条件
     */
    private String tjr;

    private String tjrId;

    private Long userid;

    private String deptCode;
    // 0，按对应部门编号精准查询,1,2,3,4,5,6
    public String readType;

    private String orderMerchandiser;

    private String containerNo;
}
