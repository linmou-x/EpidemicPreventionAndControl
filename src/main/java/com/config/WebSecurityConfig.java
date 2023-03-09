package com.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.User;
import com.mapper.UserMapper;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author charles
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserMapper userMapper;

    /**
     * 匿名访问白名单
     */
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            /*
            正常过滤
            */
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/common/**",
            "/druid/**"
    };

    /**
     * 设置加密方式（强hash方式加密）
     * spring security官方推荐使用更加安全的bcrypt加密方式
     * @return BCryptPasswordEncoder 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 认证方式配置，如内存身份验证、LDAP身份验证、基于JDBC的身份验证、自定义UserDetailsService、自定义AuthenticationProvider。
     *
     * @param auth 认证管理类
     * @throws Exception 异常
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 配置内存身份
//        auth.inMemoryAuthentication()
//                // 添加用户admin,密码123456,角色admin
//                .withUser("admin").password(bCryptPasswordEncoder.encode("123456")).roles("admin")
//                .and()
//                // 添加用户user,密码123456,角色user
//                .withUser("user").password(bCryptPasswordEncoder.encode("123456")).roles("user");
//    }

    /**
     * Web安全配置，如白名单、防火墙、调试模式、自定义Web过滤内容等
     * @param web Web安全管理类
     */
    /**
     * 基于Web安全的http请求配置，如登入、登出、异常处理、会话管理、OAuth2.0、记住我、cors、csrf、请求访问过滤等等
     * 默认情况下配置应用于所有请求，可以通过requestMatcher(RequestMatcher)或其他方法进行限制
     * @param http 请求管理类
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService())
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected  void  configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();//允许表单登录 登录成功后调转到该路径
    }
//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
//        queryWrapper.eq("phone",phone);
//        User user=userMapper.selectOne(queryWrapper);
//        List<String> authority=new ArrayList<>();
//        if (user.getName()!=null) {
//            String auth = null;
//            auth = authority.toString();
//            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//            UserDetails userDetails = org.springframework.security.core.userdetails.User
//                    .withUsername(user.getPhone())
//                    .password(user.getPassword())
//                    .authorities(auth.toUpperCase())
//                    .build();
//            return userDetails;
//        }
//        return null;
//    }
}