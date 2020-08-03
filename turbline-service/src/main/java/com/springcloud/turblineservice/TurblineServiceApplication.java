package com.springcloud.turblineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine  //启动turbine
public class TurblineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurblineServiceApplication.class, args);
    }

}
