package com.szhbl.project.kh.service;

import com.alibaba.fastjson.JSON;
import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.ip.AddressUtils;
import com.szhbl.common.utils.ip.IpUtils;
import com.szhbl.framework.redis.RedisCache;
import com.szhbl.project.kh.domain.LoginClient;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 * 
 * @author szhbl
 */
@Component
public class ClientTokenService
{
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     * 
     * @return 用户信息
     */
    public LoginClient getLoginClient(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_CLIENT_KEY);
            String clientKey = getTokenKey(uuid);
            LoginClient client = JSON.parseObject(redisCache.getCacheObject(clientKey),LoginClient.class);
            return client;
        }
        return null;
    }
    public String getClientId(HttpServletRequest request) {
        LoginClient user = getLoginClient(request);
        String clientId = "1";
        if (!ObjectUtils.isEmpty(user)) {
            clientId = user.getUserType().equals("0") ?
                    user.getBusiClients().getClientId() : user.getKhUser().getId().toString();
        }
        return clientId;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginClient(LoginClient client)
    {
        if (StringUtils.isNotNull(client) && StringUtils.isNotEmpty(client.getToken()))
        {
            String clientKey = getTokenKey(client.getToken());
            redisCache.setCacheObject(clientKey, JSON.toJSONString(client));
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginClient(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String clientKey = getTokenKey(token);
            redisCache.deleteObject(clientKey);
        }
    }

    /**
     * 创建令牌
     * 
     * @param client 客户信息
     * @return 令牌
     */
    public String createToken(LoginClient client)
    {
        String token = IdUtils.fastUUID();
        client.setToken(token);
        setUserAgent(client);
        refreshToken(client);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_CLIENT_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     * 
     * @param
     * @return 令牌
     */
    public void verifyToken(LoginClient client)
    {
        long expireTime = client.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            String token = client.getToken();
            client.setToken(token);
            refreshToken(client);
        }
    }

    /**
     * 刷新令牌有效期
     * 
     * @param client 登录信息
     */
    public void refreshToken(LoginClient client)
    {
        client.setLoginTime(System.currentTimeMillis());
        client.setExpireTime(client.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将client缓存
        String clientKey = getTokenKey(client.getToken());
        redisCache.setCacheObject(clientKey, JSON.toJSONString(client), expireTime, TimeUnit.MINUTES);
    }
    
    /**
     * 设置用户代理信息
     * 
     * @param client 登录信息
     */
    public void setUserAgent(LoginClient client)
    {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        client.setIpaddr(ip);
        client.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        client.setBrowser(userAgent.getBrowser().getName());
        client.setOs(userAgent.getOperatingSystem().getName());
    }
    
    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
