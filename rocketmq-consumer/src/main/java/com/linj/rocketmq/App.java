package com.linj.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class App {
    //spring boot 程序启动入口,必须放到扫描包的最前
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
