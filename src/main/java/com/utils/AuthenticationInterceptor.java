package com.utils;

import ch.qos.logback.classic.Logger;
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
    Token JwtToken;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        /**
         * 从httpServletRequest中获取到token，
         */
        String token = httpServletRequest.getHeader("token");
        logger.debug("获取token:",token);
        /**
         * 非访问接口直接跳过
         */
        if(!(object instanceof HandlerMethod)){
            logger.debug("直接跳过");
            return true;
        }
        /**
         * 获取接口方法名
         */
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        logger.debug("方法名:"+ method);
        /**
         *  检查是否有@passtoken注释，有则跳过认证
         */
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                logger.debug("方法有@passToken注解，跳过验证");
                return true;
            }
        }
        /**
         * 检查是否有@UserLoginToken 有则对token进行解析
         */
        logger.debug("method 注解"+String.valueOf(method.isAnnotationPresent(UserLoginToken.class)));
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            logger.debug("进去方法需要注解");
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            /**
             * required 方法默认为true，即需要
             */
            if (userLoginToken.required()) {

                if (token==null) {
                    logger.debug("无Token,跳过验证:");
                    throw new RuntimeException("无token，请重新登录");
                }
                /**
                 * 调用token工具类验证token可用
                 */
                if (!JwtToken.verifyToken(token)){
                    throw new RuntimeException("Token过期失效");
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

    /**
     * preHandle完成操作
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
    }