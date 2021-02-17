package com.szhbl.project.track.domain;


import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 位置地名管理 track_address
 *
 * @author szhbl
 * @date 2020-01-10
 */
@Data
public class TrackAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 中文名
     */
    private String nameCh;

    /**
     * 英文名
     */
    private String nameEn;

    /**
     * 删除标志,0存在，1删除
     */
    private int  delFlag;


    /**
     * 查询方法字段,0or，1and
     */
    private int  selectType;


    @Override
    public String toString() {
        return "TrackGoodsStatus{" +
                "id=" + id +
                ", nameCh='" + nameCh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", selectType='" + selectType + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
