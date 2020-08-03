package com.springcloud.zuulproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy    //开启Zuul的API网关功能
public class ZuulProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }

}
