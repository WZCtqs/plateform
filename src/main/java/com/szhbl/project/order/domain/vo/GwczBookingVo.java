package com.szhbl.project.order.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 接收国外场站拼箱订舱数据
 */
@Data
public class GwczBookingVo implements Serializable {

    /* 班列日期 */
    private String ClassNumDate1;

    /* 班列编号 */
    private String ClassNumber;

    /* 往返 方向（东向、西向） */
    private String Direction1;

    /* 邮箱 */
    private String Email;

    /* zih推荐人 */
    private String Zihtjr;

    /* 值为insert/update两种 */
    private String datatype;

    /* insert时不用存，update时需要存下，订单审核成功需要，我这边需要根据Id判断这个回值 */
    private String Id;

    /* 上下货站及箱型箱量信息 */
    private String data1;
    /* 上下货站及箱型箱量信息 */
    private List<SiteInfoVo> typeNumData;

}
