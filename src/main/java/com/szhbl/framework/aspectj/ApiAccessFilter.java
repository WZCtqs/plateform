package com.szhbl.framework.aspectj;


import com.alibaba.fastjson.JSON;
import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.ip.IpUtils;
import com.szhbl.common.utils.spring.SpringUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.security.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Order(5)
@Aspect
@Component
public class ApiAccessFilter {
    @Pointcut("execution(public * com.szhbl.project.*.controller.*.*(..))")
    public void log() {
    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApiAccessFilter.class);

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<String> name = new ThreadLocal<>();

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();// 请求的地址
        String ip = IpUtils.getIpAddr(request);
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

        logger.debug("Request_IP: " + ip);
        if(loginUser != null) {
            logger.debug("Request_UserName: " + loginUser.getUsername());
            logger.debug("Request_LoginLocation: " + loginUser.getLoginLocation());
        }
        //记录请求的内容
        logger.debug("Aspect_Method: " + request.getMethod());
        logger.debug("Aspect_MethodAspect_URL: " + request.getRequestURL().toString());
        name.set(request.getRequestURL().toString());
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        //处理完请求后，返回内容
        logger.debug("Method Result:" + JSON.toJSONString(ret));
        if(System.currentTimeMillis() - startTime.get()>200){
            logger.error("Method execution time:" + (System.currentTimeMillis() - startTime.get()) +"--"+ name.get());
        }else {
            logger.debug("Method execution time:" + (System.currentTimeMillis() - startTime.get()) +"--"+ name.get());
        }
    }
}
