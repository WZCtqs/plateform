package com.szhbl.project.kh.controller;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.ServletUtils;
import com.szhbl.framework.security.service.SysPermissionService;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.kh.domain.KhUser;
import com.szhbl.project.kh.domain.LoginClient;
import com.szhbl.project.kh.request.LoginRequest;
import com.szhbl.project.kh.service.ClientLoginService;
import com.szhbl.project.kh.service.ClientTokenService;
import com.szhbl.project.system.domain.SysMenu;
import com.szhbl.project.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 登录验证
 * 
 * @author szhbl
 */
@Api(tags = "客户登录")
@RequestMapping("/client")
@RestController
@Slf4j
public class ClientLoginController
{
    @Autowired
    private ClientLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ClientTokenService tokenService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录方法
     *
     * @return 结果
     */

    @ApiOperation(value = "客户登录")
    @PostMapping("/login")
    public AjaxResult login(LoginRequest loginRequest)
    {
        log.debug("-------------------------------start-----------------------------------");
        log.debug("当前时间:"+new Date().toLocaleString());
        log.debug("输入的验证码："+loginRequest.getCode());
        log.debug("输入的codeToken："+loginRequest.getCodeToken());
        String codeRedis = (String) redisTemplate.opsForValue().get(loginRequest.getCodeToken());
        log.debug("Redis通过codeToken得到的code："+codeRedis);
        if (!(StringUtils.isNotEmpty(codeRedis) && codeRedis.equals(loginRequest.getCode()))) {
            log.debug("/client/login接口：验证码错误");
            log.debug("--------------------------------end----------------------------------");
            return new AjaxResult(10003, "验证码错误");
        }
        log.debug("/client/login接口：验证码正确");
        log.debug("--------------------------------end----------------------------------");
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginRequest.getUserName(), loginRequest.getPassword());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 登录方法
     *
     * @return 结果
     */
    @ApiOperation(value = "老版订舱登录")
    @PostMapping("/oldLogin")
    public AjaxResult oldLogin(LoginRequest loginRequest)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login2(loginRequest.getUserName(), loginRequest.getPassword());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @ApiOperation(value = "获取客户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        AjaxResult ajax = AjaxResult.success();
        LoginClient client = tokenService.getLoginClient(ServletUtils.getRequest());
        if (client.getUserType().equals("0")) {
            BusiClients busiClients = client.getBusiClients();
            ajax.put("busiClients", busiClients);
        } else {
            KhUser khUser = client.getKhUser();
            ajax.put("busiClients", khUser);
        }
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginClient client = tokenService.getLoginClient(ServletUtils.getRequest());
        // 用户信息
        BusiClients busiClients = client.getBusiClients();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(null,null);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
