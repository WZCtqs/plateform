package com.szhbl.project.documentcenter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szhbl.framework.web.domain.BaseEntity;
import com.szhbl.project.client.VO.ProblemFileVo2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 托书单证对象 order_document
 *
 * @author szhbl
 * @date 2020-01-03
 */
@ApiModel(description = "托书单证表")
@Data
public class DocOrderDocument extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /**
     * 东西向
     */
    private String classEastAndWest;
    /**
     * 整拼箱
     */
    private String isConsolidation;

    /**
     * 托书编号（舱位号）
     */
    @ApiModelProperty(value = "托书编号（舱位号）")
    private String orderNumber;

    /**
     * 单证类型
     */
    @ApiModelProperty(value = "单证类型")
    private String fileTypeKey;

    private String fileTypeText;

    private Integer fileOrderStage;

    /**
     * 单证地址url
     */
    @ApiModelProperty(value = "单证地址url")
    private String fileUrl;

    /**
     * 单证文件名
     */
    private String fileName;
    /**
     * 单证地址urls
     */
    @ApiModelProperty(value = "单证地址urls--客户端上传使用")
    private String[] fileUrls;

    /**
     * 客户上传提货时间
     */
    @ApiModelProperty(value = "客户上传提货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String pickGoodsTimec;

    /**
     * 客户上传提货联系人
     */
    @ApiModelProperty(value = "客户上传提货联系人")
    private String pickGoodsContacts;

    @ApiModelProperty(value = "客户上传提货联系方式")
    private String pickGoodsPhone;
    /**
     * 客户上传提货详细地址
     */
    @ApiModelProperty(value = "客户上传提货详细地址")
    private String pickGoodsAddress;

    private Integer pickGoodsTimecConfirm;

    /**
     * 箱号
     */
    @ApiModelProperty(value = "箱号")
    private String containerNo;

    /**
     * 箱型
     */
    @ApiModelProperty(value = "箱型")
    private String containerType;

    @ApiModelProperty(value = "铅封号")
    private String sealnumber;

    private String containerFail;
    //0：待审核， 1：失败，2：成功
    private Integer containerStatus;

    //0：待审核， 1：失败，2：成功
    private Integer boxingphotoStatus;

    private String boxingphotoFail;

    /**
     * 发货方、出口商
     */
//    @ApiModelProperty(value = "发货方、出口商")
//    private String shipperExporter;
    /**
     * 收货方
     */
//    @ApiModelProperty(value = "收货方")
//    private String consignee;
    /**
     * 通知方
     */
//    @ApiModelProperty(value = "通知方")
//    private String notifyParty;
    /**
     * 商品描述
     */
//    @ApiModelProperty(value = "商品描述")
//    private String goodsDescription;


    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;

    /**
     * 上传着
     */
    @ApiModelProperty(value = "上传着")
    private String uploadBy;

    /**
     * 上传者部门
     */
    @ApiModelProperty(value = "上传者部门")
    private String uploadDept;

    /**
     * 来源系统
     */
    @ApiModelProperty(value = "来源系统")
    private String formSystem;

    @ApiModelProperty(value = "确认备注")
    private String confirmRemark;

    /**
     * 货品中文名称
     */
    private String goodsName;

    /**
     * 货品英文名称
     */
    private String goodsEnName;

    /**
     * 规格
     */
    private List<String> goodsStandard;

    /**
     * 重量
     */
    private String goodsKgs;

    /**
     * 体积
     */
    private String goodsCbm;

    /**
     * 提货文件上传
     */
    private List<ProblemFileVo2> urlList;

    private String driverInfo;
}
