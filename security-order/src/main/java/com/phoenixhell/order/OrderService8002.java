package com.phoenixhell.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-上午 10:19
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.phoenixhell")
@EnableSwagger2
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class OrderService8002 {
    public static void main(String[] args) {
        SpringApplication.run(OrderService8002.class,args);
    }
}
