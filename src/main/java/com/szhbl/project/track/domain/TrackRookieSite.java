package com.szhbl.project.track.domain;


import com.szhbl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 菜鸟站点管理 track_rookie_site
 *
 * @author szhbl
 * @date 2020-01-10
 */
@Data
public class TrackRookieSite extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 站点/地名
     */
    private String siteName;

    /**
     * 区域0东区1西区
     */
    private int  area =0;

    /**
     * 删除标志,0存在，1删除
     */
    private int  delFlag;


    @Override
    public String toString() {
        return "TrackGoodsStatus{" +
                "id=" + id +
                ", timeZone='" + timeZone + '\'' +
                ", siteName='" + siteName + '\'' +
                ", area='" + area + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
