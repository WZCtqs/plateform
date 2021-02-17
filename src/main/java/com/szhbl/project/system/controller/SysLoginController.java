package com.szhbl.project.system.controller;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.ServletUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.security.service.SysLoginService;
import com.szhbl.framework.security.service.SysPermissionService;
import com.szhbl.framework.security.service.TokenService;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.system.domain.SysMenu;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.domain.vo.LoginVo;
import com.szhbl.project.system.service.ISysMenuService;
import com.szhbl.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author szhbl
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;
    /**
     * 登录方法
     *
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVo loginVo)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        System.out.println(loginVo.toString());
        String token = loginService.login(loginVo.getUsername(), loginVo.getPassword(), loginVo.getCode(), loginVo.getUuid(), loginVo.getSubSystem());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        SysUser u1 = userService.selectUserById(user.getUserId());
        user.setDeptCode(u1.getDeptCode());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters(String modelId)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId(),modelId);

        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
