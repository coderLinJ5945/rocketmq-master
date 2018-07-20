package com.linj.rocketmq.temp.consumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 消费者群组初始化
 */
@Controller
@RequestMapping("/consumer")
public class ConsumerInitController {



    //根据配置文件初始化消费者群组，配置文件使用json文件
    @RequestMapping(value = "init.do",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void consumerInit(){
        //return consumerService.initConsumer();
    }
}
