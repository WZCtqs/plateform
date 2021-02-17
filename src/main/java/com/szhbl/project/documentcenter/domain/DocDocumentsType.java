package com.szhbl.project.documentcenter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 单证类型对象 doc_documents_type
 *
 * @author szhbl
 * @date 2020-01-13
 */
@Data
@ApiModel(description = "单证类型")
public class DocDocumentsType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty(value = "id")
    private Long id;

    /** 所属订单分类 */
    @Excel(name = "所属订单分类")
    @ApiModelProperty(value = "所属订单分类(一级id)")
    private Long fileOrderStage;

    /** 单证类型 */
    @Excel(name = "单证类型")
    @ApiModelProperty(value = "二级单证类型（不可改变）")
    private String fileTypeKey;

    /** 单证类型说明 */
    @Excel(name = "单证类型说明")
    @ApiModelProperty(value = "单证类型说明")
    private String fileTypeText;

    /** 单证类型说明EN */
    @Excel(name = "单证类型说明英文")
    @ApiModelProperty(value = "单证类型说明英文")
    private String fileTypeTextEn;

    /** 单证来源：0：系统，1：客户 */
    @Excel(name = "单证来源：0：平台，1：客户， 2：其他系统")
    @ApiModelProperty(value = "id")
    private Integer fileFrom;

    @ApiModelProperty(value = "单证来源说明")
    private String fileFromText;

    /** 客户的话，设置提醒节点 */
    @Excel(name = "设置提醒节点")
    @ApiModelProperty(value = "设置提醒节点")
    private Integer fileNotice;

    private String fileNoticeTime;

    /** 单张作用域：0：去程，1：回程，2：去回程 */
    @Excel(name = "单张作用域：0：去程，1：回程，2：去回程")
    @ApiModelProperty(value = "单证作用域：0：去程，1：回程，2：去回程")
    private Integer activeArea;

    /** 整箱，拼箱*/
    @ApiModelProperty(value = "单证作用域：0：整箱，1：拼箱，2：整拼箱")
    private Integer fclLcl;

    /** 是否系统设定 */
    @Excel(name = "是否系统设定")
    @ApiModelProperty(value = "0： 一级分类，1：二级分类")
    private Integer isSystem;

    /**
     * 是否客户端显示
     */
    @ApiModelProperty(value = "0： 显示，1：不显示")
    private Integer showCustomer;

    /** 二级单证类型id数组 */
    @ApiModelProperty(value = "一级类型新增编辑时需要：二级单证类型id数组")
    private Long[] docTypeIds;

    /** docTypesList*/
    private List<DocDocumentsType> docTypesList;
}
