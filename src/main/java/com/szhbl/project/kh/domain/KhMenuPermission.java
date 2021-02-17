package com.szhbl.project.kh.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 客户端---菜单权限对象 kh_menu_permission
 * 
 * @author jhm
 * @date 2020-03-21
 */
public class KhMenuPermission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户id */
    @Excel(name = "客户id")
    private String clientId;

    /** 菜单id */
    @Excel(name = "菜单id")
    private String menuId;

    /** 半菜单id */
    @Excel(name = "半菜单id")
    private String halfMenuId;

    /** 权限名称 */
    @Excel(name = "权限名称")
    private String permissionName;

    /** 0 1 2 分别代表东向 西向 东西向 */
    @Excel(name = "0 1 2 分别代表东向 西向 东西向")
    private String type;


    private String[] menuIds;
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }

    public String getMenuId()
    {
        return menuId;
    }
    public void setPermissionName(String permissionName)
    {
        this.permissionName = permissionName;
    }

    public String getPermissionName() 
    {
        return permissionName;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public String getHalfMenuId() {
        return halfMenuId;
    }

    public void setHalfMenuId(String halfMenuId) {
        this.halfMenuId = halfMenuId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clientId", getClientId())
            .append("menuId", getMenuId())
            .append("permissionName", getPermissionName())
            .append("remark", getRemark())
            .append("type", getType())
            .append("halfMenuId", getHalfMenuId())
            .toString();
    }

    public String[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String[] menuIds) {
        this.menuIds = menuIds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
