package com.czx.shirospringboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1. 【subject -> ShiroFilterFactoryBean】
    @Bean(name = "shiroFilterFactoryBean")
    // @Qualifier("securityManager") 指定要的 Bean 的名字为 securityManager，下同
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean subject = new ShiroFilterFactoryBean();
        //设置安全管理器
        //需要关联 securityManager ，通过参数把 securityManager 对象传递过来
        subject.setSecurityManager(securityManager);

        //添加 Shiro 的【内置过滤器】=======================
        /*
        [认证过滤]
        anon 不需要任何认证
        authBasic Http认证
        authc 需要认证之后才可以访问
        user 需要当前存在用户才可以访问
        logout 退出
        [授权过滤]
        perms 需要相关权限才可以访问
        roles 需要相关角色才可以访问
        ssl 安全的协议
        port 相关端口
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 设置 /user/addUser 这个请求,只有认证过才能访问
//        map.put("/user/addUser","authc");
//        map.put("/user/deleteUser","authc");

        //【授权】 perms[必须是user用户：有add权限]
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        //【拦截】 设置 /user/ 下面的所有请求,只有【认证】过才能访问
        filterMap.put("/user/*","authc");
        subject.setFilterChainDefinitionMap(filterMap);

        // 请求【失败】后跳转到登录的页面
        subject.setLoginUrl("/toLogin");
        //未授权页面
        subject.setUnauthorizedUrl("/noAuth");
//============================================
        return subject;
    }

    //2. 【securityManager -> DefaultWebSecurityManager】
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurity(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //需要关联自定义的 Realm，通过参数把 Realm 对象传递过来
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //3. 【realm】
    //让 spring 托管自定义的 realm 类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
