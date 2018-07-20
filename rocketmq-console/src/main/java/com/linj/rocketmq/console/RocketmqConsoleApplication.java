package com.linj.rocketmq.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
//初始化SpringBoot 默认配置
@EnableAutoConfiguration
@SpringBootApplication
public class RocketmqConsoleApplication {
	//程序入口必须要放在所有扫描包的上级
	public static void main(String[] args) {
		SpringApplication.run(RocketmqConsoleApplication.class, args);
	}
}
