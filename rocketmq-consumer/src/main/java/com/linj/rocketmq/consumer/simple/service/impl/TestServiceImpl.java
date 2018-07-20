package com.linj.rocketmq.consumer.simple.service.impl;

import com.linj.rocketmq.consumer.simple.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    public String test() {
        return "解决 bean在new对象中null问题";
    }
}
