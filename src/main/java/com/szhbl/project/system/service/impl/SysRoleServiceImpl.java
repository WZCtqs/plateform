package com.szhbl.project.system.service.impl;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.exception.CustomException;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.aspectj.lang.annotation.DataScope;
import com.szhbl.framework.web.domain.RoleTreeSelect;
import com.szhbl.project.system.domain.SysRole;
import com.szhbl.project.system.domain.SysRoleDept;
import com.szhbl.project.system.domain.SysRoleMenu;
import com.szhbl.project.system.mapper.SysRoleDeptMapper;
import com.szhbl.project.system.mapper.SysRoleMapper;
import com.szhbl.project.system.mapper.SysRoleMenuMapper;
import com.szhbl.project.system.mapper.SysUserRoleMapper;
import com.szhbl.project.system.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色 业务层处理
 * 
 * @author szhbl
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll()
    {
        return roleMapper.selectRoleAll();
    }

    /**
     * 根据用户ID获取角色选择框列表
     * 
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     * 
     * @param role 角色信息
     */
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // 新增角色信息
        log.info("----" + role.getParentRoleId());
        SysRole info = roleMapper.selectRoleWithId(role.getParentRoleId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new CustomException("角色停用，不允许新增");
        }
        role.setAncestors(info.getAncestors() + "," + role.getParentRoleId());
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        //按部门树修改角色

        SysRole newParentRole = roleMapper.selectRoleWithId(role.getParentRoleId());
        SysRole oldRole = roleMapper.selectRoleById(role.getRoleId());
        if (StringUtils.isNotNull(newParentRole) && StringUtils.isNotNull(oldRole))
        {
            String newAncestors = newParentRole.getAncestors() + "," + newParentRole.getRoleId();
            String oldAncestors = oldRole.getAncestors();
            role.setAncestors(newAncestors);
            updateDeptChildren(role.getRoleId(), newAncestors, oldAncestors);
        }
        // 修改角色信息
         roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     * 
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new CustomException(String.format("已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    @Override
    public List<RoleTreeSelect> buildRoleTreeSelect(List<SysRole> sysRoles) {
        List<SysRole> sysRoleTree = buildRoleTree(sysRoles);
        return sysRoleTree.stream().map(RoleTreeSelect::new).collect(Collectors.toList());

    }


    /**
     * 修改子元素关系
     *
     * @param roleId 被修改的角色ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long roleId, String newAncestors, String oldAncestors)
    {
        List<SysRole> children = roleMapper.selectChildrenRoleById(roleId);
        if (children.size() > 0)
        {
            for (SysRole child : children)
            {
                child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
            }
            roleMapper.updateRoleChildren(children);
        }
    }


    /**
     *构建前端所需要树结构
     *
     * @param roles 部门列表
     * @return 树结构列表
     */
    public List<SysRole> buildRoleTree(List<SysRole> roles){
        List<SysRole> returnList = new ArrayList<SysRole>();
        if (StringUtils.isNotEmpty(roles) && StringUtils.isNotNull(roles.stream().findFirst())&&roles.stream().findFirst().get().getParentRoleId()==0)
        {
            roles.stream().findFirst().get().setParentRoleId(null);
        }
        for (Iterator<SysRole> iterator = roles.iterator(); iterator.hasNext();)
        {
            SysRole t = (SysRole) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (StringUtils.isNull(t.getParentRoleId()) || t.getParentRoleId() == 0)
            {
                recursionFn(roles, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = roles;
        }
        return returnList;

    }


    /**
     * 递归列表
     */
    private void recursionFn(List<SysRole> list, SysRole t)
    {
        // 得到子节点列表
        List<SysRole> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysRole tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysRole> it = childList.iterator();
                while (it.hasNext())
                {
                    SysRole n = (SysRole) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }


    /**
     * 得到子节点列表
     */
    private List<SysRole> getChildList(List<SysRole> list, SysRole t)
    {
        List<SysRole> tlist = new ArrayList<SysRole>();
        Iterator<SysRole> it = list.iterator();
        while (it.hasNext())
        {
            SysRole n = (SysRole) it.next();
            if (StringUtils.isNotNull(n.getParentRoleId()) && n.getParentRoleId().longValue() == t.getRoleId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysRole> list, SysRole t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}

