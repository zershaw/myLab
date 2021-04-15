package com.czx.spring03web.config;
//全面接管Springmvc

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
//
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public  void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/emp/list.html").setViewName("/emp/list");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login","/login","/login.html");
        //对所有【请求（"/**"）】都添加拦截器，除了excludePathPatterns里的访问和方法请求
    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // 浏览器发送/test ， 就会跳转到test页面；
//        registry.addViewController("/test").setViewName("test");
//    }
//
//    @Bean //放到bean中
//    public ViewResolver myViewResolver(){
//        return new MyViewResolver();
//    }
//
//    //我们写一个静态内部类，视图解析器就需要实现ViewResolver接口
//    private static class MyViewResolver implements ViewResolver{
//        @Override
//        public View resolveViewName(String s, Locale locale) throws Exception {
//            return null;
//        }
//    }
}
