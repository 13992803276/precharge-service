package com.tw.precharge.controller.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lexu
 */

@Configuration
public class FeignConfiguration {
    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // 示例
                requestTemplate.header("Content-Type", "application/json");
            }
        };
    }

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
