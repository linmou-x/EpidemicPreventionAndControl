package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author charles
 */
@Configuration
public class DruidConfig {

    @Bean
    //绑定yml的配置
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    //后台监控功能
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //配置后台登录用户
        HashMap<String, String> initParameters = new HashMap<>();
        //登录的key是固定的
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","admin");

        //设置访问权限
        //允许访问
        initParameters.put("allow","");
        //禁止访问
        //initParameters.put("key","IP地址");
        //初始化参数
        bean.setInitParameters(initParameters);
        return  bean;
    }
}