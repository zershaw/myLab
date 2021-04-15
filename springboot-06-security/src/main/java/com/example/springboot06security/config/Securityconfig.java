package com.example.springboot06security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//AOP:拦截器
@EnableWebSecurity
public class Securityconfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //首页所有人都可以访问，功能耶只有对应有权限的人才能访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");
        //没有权限默认会到登录页面，需要开启登录的页面
        http.formLogin(); //springboot默认的登录页面
        //http.formLogin().loginPage("/tologin").loginProcessingUrl("/login");

        //注销并跳到首页
        http.csrf().disable(); //关闭csrf功能，一种挟制用户在当前已登录的Web应用程序上执行非本意的操作的攻击方法。
        http.logout().logoutSuccessUrl("/");

        //开启记住我功能 cookie默认保留两周
        http.rememberMe();
    }
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //super.configure(auth);
        //inMemoryAuthentication()：没连数据库，直接在内存中设置
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("czx").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip1")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip1","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}
