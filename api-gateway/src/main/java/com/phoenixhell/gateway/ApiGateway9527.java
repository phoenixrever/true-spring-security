package com.phoenixhell.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author phoenixhell
 * @since 2021/4/9 0009-下午 2:45
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.phoenixhell")
public class ApiGateway9527 {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateway9527.class,args);
    }
}
