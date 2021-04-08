package com.phoenixhell.securityBase.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author phoenixhell
 * @since 2021/3/20 0020-下午 3:16
 */
@Configuration
public class FeignConfig {
    //开启feign日志
    @Configuration
    public class LogConfig {
        @Bean
        Logger.Level feignLoggerLevel(){
            return Logger.Level.FULL;
        }
    }
}
