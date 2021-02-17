package com.szhbl.project.client.domain;

import lombok.Data;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 客户跟单员对象 busi_gendan
 * 
 * @author szhbl
 * @date 2020-06-16
 */
@Data
public class BusiGendan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 跟单员 */
    private String username;

    /** 跟单员id（订舱系统） */
    private String userid;

    /** 跟单员工号 */
    private String usernumber;

    /** 跟单员部门 */
    private String userdepart;

    /** 接收数据处理方式  insert-update-delete*/
    private String type;

    /** 是否删除（0：未删除，1：删除） */
    private Integer delflag;


}
