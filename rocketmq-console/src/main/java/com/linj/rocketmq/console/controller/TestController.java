package com.linj.rocketmq.console.controller;

import com.linj.rocketmq.console.config.RMQConfigure;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {


    @Resource
    private RMQConfigure rmqConfigure;

    @RequestMapping("/init")
    @ResponseBody
    public String init(){

        return rmqConfigure.getNamesrvAddr();
    }
}
