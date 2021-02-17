package com.szhbl.project.kh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szhbl.project.client.domain.BusiClients;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 客户端登录用户身份权限
 * 
 * @author szhbl
 */
public class LoginClient implements UserDetails
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息
     */
    private BusiClients busiClients;
    /**
     * 用户信息
     */
    private KhUser khUser;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public LoginClient()
    {
    }

    public LoginClient(BusiClients busiClients, Set<String> permissions)
    {
        this.busiClients = busiClients;
        this.permissions = permissions;
        this.userType = "0";
    }

    public LoginClient(KhUser khUser, Set<String> permissions)
    {
        this.khUser = khUser;
        this.permissions = permissions;
        this.userType = "1";
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        if ("0".equals(userType)) {
            return busiClients.getClientLoginpwd();
        } else {
            return khUser.getPassword();
        }
    }

    @Override
    public String getUsername()
    {
        if ("0".equals(userType)) {
            return busiClients.getClientLoginuser();
        } else {
            return khUser.getPhone();
        }
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     * 
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * 
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * 
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public BusiClients getBusiClients()
    {
        return busiClients;
    }

    public void setBusiClients(BusiClients busiClients)
    {
        this.busiClients = busiClients;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public KhUser getKhUser() {
        return khUser;
    }

    public void setKhUser(KhUser khUser) {
        this.khUser = khUser;
    }
}
