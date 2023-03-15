package com.utils;

import ch.qos.logback.classic.Logger;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.entity.User;
import com.services.Impl.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    UserService userService;

    @Resource
    Token JWTtoken;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        logger.debug("获取token:",token);
        if(!(object instanceof HandlerMethod)){
            logger.debug("直接跳过");
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        logger.debug("方法名:",String.valueOf(method));
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                logger.debug("方法有@passToken注解，跳过验证");
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        logger.debug("method 注解"+String.valueOf(method.isAnnotationPresent(UserLoginToken.class)));
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            logger.debug("进去方法需要注解");
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token==null) {
                    logger.debug("无Token,跳过验证:");
                    throw new RuntimeException("无token，请重新登录");
                }
                if (!JWTtoken.verifyToken(token)){
                    throw new RuntimeException("Token过期失效");
                }
                // 获取 token 中的 user id
                try {
                    logger.debug(String.valueOf(JWT.decode(token)));
                    JWTtoken.getId(token);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
    }