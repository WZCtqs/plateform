package com.szhbl.project.track.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "运踪查询vo")
@Data
public class TrackQueryVo {//运踪查询vo

    /** 订单id*/
    private String orderId;

    /** 班列id*/
    private String classId;

    @ApiModelProperty(value = "去回程")
    //往返0为去(西向) 1为回(东向）
    private String classEastAndWest;

    @ApiModelProperty("班列日期")
    private String classDate;

    @ApiModelProperty(value = "委托书编号")
    private String orderNumber;

    @ApiModelProperty(value = "箱号")
    private String boxNum;

    @ApiModelProperty(value = "货品中文名称")
    private String goodsName;

    @ApiModelProperty(value = "货品英文名称")
    private String goodsEnName;

    @ApiModelProperty(value = "发货人")
    private String shipOrederName;

    @ApiModelProperty(value = "运综状态")
    private String trackState;

    @ApiModelProperty(value = "是否异常")
    private String isNormal;

    //班列编号
    private String classBh;

    /**
     * 数据权限查询条件
     */
    private String deptCode;
    private String readType;
    private String userId;

}
