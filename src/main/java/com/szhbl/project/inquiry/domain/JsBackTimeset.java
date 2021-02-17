package com.szhbl.project.inquiry.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 集疏部回程提货找车和运输时间对象 js_back_timeset
 *
 * @author szhbl
 * @date 2020-07-21
 */
public class JsBackTimeset extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String station;

    private String pickcountry;

    private Integer fcl;

    private Integer lcl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setPickcountry(String pickcountry) {
        this.pickcountry = pickcountry;
    }

    public String getPickcountry() {
        return pickcountry;
    }

    public void setFcl(Integer fcl) {
        this.fcl = fcl;
    }

    public Integer getFcl() {
        return fcl;
    }

    public void setLcl(Integer lcl) {
        this.lcl = lcl;
    }

    public Integer getLcl() {
        return lcl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("station", getStation())
                .append("pickcountry", getPickcountry())
                .append("fcl", getFcl())
                .append("lcl", getLcl())
                .toString();
    }
}
