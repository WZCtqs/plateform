package com.szhbl.project.track.domain;


import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 去程整柜场站地址 track_go_station_address
 *
 * @author szhbl
 * @date 2020-01-10
 */
@Data
public class TrackGoStation extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 班列id
     */
    private String classId;

    /**
     * 班列编号
     */
    @Excel(name = "班列编号")
    private String classNum;

    /**
     * 口岸
     */
   // @Excel(name = "口岸")
    private String port;

    /**
     * 站点编码
     */
    @Excel(name = "站点编码")
    private String code;

    /**
     * 下货站
     */
    @Excel(name = "下货站")
    private String downStation;

    /**
     * 车站地址
     */
    @Excel(name = "车站地址")
    private String stationAddress;


    /**
     * 班列日期
     */
    private String classDate;

    /**
     * 删除标志,0存在，1删除
     */
    private int  delFlag;
    @Override
    public String toString() {
        return "TrackGoodsStatus{" +
                "id=" + id +
                ", classId='" + classId + '\'' +
                ", classNum='" + classNum + '\'' +
                ", port=" + port +'\'' +
                ", code=" + code +'\'' +
                ", downStation='" + downStation + '\'' +
                ", stationAddress='" + stationAddress + '\'' +
                ", classDate='" + classDate + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
