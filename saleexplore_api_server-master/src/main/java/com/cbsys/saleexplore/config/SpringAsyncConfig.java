package com.cbsys.saleexplore.config;

import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

// check this for the async configuration
// https://blog.csdn.net/ignorewho/article/details/85603920

@Configuration
@EnableAsync
@ComponentScan({"com.cbsys.saleexplore"})
public class SpringAsyncConfig implements AsyncConfigurer {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpringAsyncConfig.class);

    @Autowired
    private AppProperties appProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(appProperties.getAsync().getAsyncCorePoolSize());
        executor.setMaxPoolSize(appProperties.getAsync().getAsyncMaxPoolSize());
        executor.setQueueCapacity(appProperties.getAsync().getAsyncQueueCapacity());
        executor.setKeepAliveSeconds(appProperties.getAsync().getAsyncKeepAliveSecs());
        executor.setThreadNamePrefix("worker-exec-");
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            LOGGER.warn("当前任务线程池队列已满.");
        });
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            Class<?> targetClass = method.getDeclaringClass();
            LOGGER.error(ex.getMessage(), ex);
        };
    }
}
