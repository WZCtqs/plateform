package com.szhbl.project.order.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 上下货站
 */
@Data
public class SiteInfoVo implements Serializable {

    /**
     * 上货站
     */
    private String uploadSite;

    /**
     * 下货站
     */
    private String unloadSite;


    /**
     *  提箱地
     */
    private String tixiang;

    /**
     *  还箱地
     */
    private String huanxiang;

    /**
     *  箱型箱量
     */
    private List<ContainerInfoVo> typenum;
}
