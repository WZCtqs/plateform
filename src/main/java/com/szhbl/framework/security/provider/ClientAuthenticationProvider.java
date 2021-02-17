package com.szhbl.framework.security.provider;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.kh.service.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ClientAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("用户名不可以为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadCredentialsException("密码不可以为空");
        }
        //获取用户信息
        UserDetails user = clientDetailsService.loadUserByUsername(username);
        //比较前端传入的密码明文和数据库中密码是否相等
        if (StringUtils.isNotBlank(password) && (!password.equals(user.getPassword()))) {
            throw new BadCredentialsException("密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
    public Authentication authenticate2(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("用户名不可以为空");
        }
//        if(StringUtils.isBlank(password)){
//            throw new BadCredentialsException("密码不可以为空");
//        }
        //获取用户信息
        if(null==clientDetailsService.loadUserByUsername2(username)){
            return null;
        }
        UserDetails user = clientDetailsService.loadUserByUsername2(username);
        //比较前端传入的密码明文和数据库中密码是否相等
        if (StringUtils.isNotBlank(password) && (!password.equals(user.getPassword()))) {
            throw new BadCredentialsException("密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
