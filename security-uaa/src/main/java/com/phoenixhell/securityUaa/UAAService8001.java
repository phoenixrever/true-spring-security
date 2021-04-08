package com.phoenixhell.securityUaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-上午 9:37
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.phoenixhell")
@EnableSwagger2
public class UAAService8001 {
    public static void main(String[] args) {
        SpringApplication.run(UAAService8001.class,args);
    }
}
