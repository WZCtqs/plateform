package com.szhbl.project.kh.service;

import com.szhbl.common.constant.Constants;
import com.szhbl.common.exception.CustomException;
import com.szhbl.common.exception.user.UserPasswordNotMatchException;
import com.szhbl.common.utils.MessageUtils;
import com.szhbl.framework.manager.AsyncManager;
import com.szhbl.framework.manager.factory.AsyncFactory;
import com.szhbl.framework.security.provider.ClientAuthenticationProvider;
import com.szhbl.project.kh.domain.LoginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 客户端登录校验方法
 * 
 * @author szhbl
 */
@Component
public class ClientLoginService
{
    @Autowired
    private ClientTokenService clientTokenService;

    @Autowired
    private ClientAuthenticationProvider clientAuthenticationProvider;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password)
    {
        // 客户验证
        Authentication authentication = null;

        try
        {
            // 该方法会去调用ClientDetailsServiceImpl.loadUserByUsername
            authentication = clientAuthenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginClient client = (LoginClient) authentication.getPrincipal();
        // 生成token
        return clientTokenService.createToken(client);
    }
    public String login2(String username, String password)
    {
        // 客户验证
        Authentication authentication = null;

        try
        {

            // 该方法会去调用ClientDetailsServiceImpl.loadUserByUsername
            authentication = clientAuthenticationProvider
                    .authenticate2(new UsernamePasswordAuthenticationToken(username, password));
            if(null== authentication ){
                return "1";
            }
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));


        // 生成token
        return clientTokenService.createToken((LoginClient) authentication.getPrincipal());
    }
}
