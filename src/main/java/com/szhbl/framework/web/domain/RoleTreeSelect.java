package com.szhbl.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szhbl.project.system.domain.SysRole;

import java.util.List;
import java.util.stream.Collectors;

public class RoleTreeSelect {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RoleTreeSelect> children;

    public RoleTreeSelect()
    {

    }
    public RoleTreeSelect(SysRole sysRole)
    {
        this.id = sysRole.getRoleId();
        this.label = sysRole.getRoleName();
        this.children = sysRole.getChildren().stream().map(RoleTreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<RoleTreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<RoleTreeSelect> children)
    {
        this.children = children;
    }
}
