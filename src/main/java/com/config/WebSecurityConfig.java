package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author charles
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 匿名访问白名单
     */
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
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
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证方式配置，如内存身份验证、LDAP身份验证、基于JDBC的身份验证、自定义UserDetailsService、自定义AuthenticationProvider。
     *
     * @param auth 认证管理类
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用配置文件时
        // super.configure(auth);

        // 创建一个密码加密处理对象
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 配置内存身份
        auth.inMemoryAuthentication()
                // 添加用户admin,密码123456,角色admin
                .withUser("admin").password(bCryptPasswordEncoder.encode("123456")).roles("admin")
                .and()
                // 添加用户user,密码123456,角色user
                .withUser("user").password(bCryptPasswordEncoder.encode("123456")).roles("user");
    }

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
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http
                // 启用cors,CORS是一个W3C标准，全称是"跨域资源共享"（Cross-origin resource sharing）。
                // 它允许浏览器向跨源服务器，发出XMLHttpRequest请求，从而克服了AJAX只能同源使用的限制。
                .cors()
                .and()
                // 禁用CSRF（Cross-site request forgery）：跨站请求伪造，
                // 也被称为：one click attack/session riding，缩写为：CSRF/XSRF。
                // CSRF攻击是攻击者盗用了你的身份，以你的名义发送恶意请求。
                // CSRF能够做的事情包括：以你名义发送邮件，发消息，盗取你的账号，甚至于购买商品，虚拟货币转账......
                // 造成的问题包括：个人隐私泄露以及财产安全。
                // 从Spring Security 4.0开始，默认情况下会启用CSRF保护，以防止CSRF攻击应用程序，
                // Spring Security CSRF会针对PATCH，POST，PUT和DELETE方法进行防护。
                // 在项目开发过程中，由于前后端分离，需要使用postman之类软件模拟前端请求，此时需要禁用csrf，
                // 在不禁用CSRF的情况下，postman发起请求会一直失败
                // 但是在正式上线时应开启csrf防护
                .csrf().disable()
                // 认证请求
                .authorizeRequests()
                // 所有请求
                .anyRequest()
                // 认证过才允许访问
                .authenticated()
                .and()
                // 允许登录页匿名访问
                .formLogin()
                .permitAll()
                .and()
                // 允许登出页匿名访问
                .logout()
                .permitAll()
                .and().csrf().ignoringAntMatchers(AUTH_WHITELIST)
        ;
    }

}