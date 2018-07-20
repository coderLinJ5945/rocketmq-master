package com.linj.rocketmq.consumer.simple.controller;

import com.linj.rocketmq.consumer.simple.service.SimpleConsumerService;
import com.linj.rocketmq.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 简单类型的消费者
 * 这样区分的目的是方便不同的消费者类型横向扩展
 */
@Controller
@RequestMapping("/simpleConsumer")
public class SimpleConsumerController {

    @Autowired
    private SimpleConsumerService simpleConsumerService;

    @RequestMapping(value = "init.do",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResult init(){
       return  simpleConsumerService.initSimpleConsumer();
    }
}
