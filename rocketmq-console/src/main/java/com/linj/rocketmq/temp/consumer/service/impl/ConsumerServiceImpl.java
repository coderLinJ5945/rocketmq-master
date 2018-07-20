package com.linj.rocketmq.temp.consumer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.linj.rocketmq.temp.admin.service.DashboardCollectService;
import com.linj.rocketmq.temp.config.RMQConfigure;
import com.linj.rocketmq.temp.consumer.model.JsonResult;
import com.linj.rocketmq.temp.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private RMQConfigure rmqConfigure;
    @Resource
    private DashboardCollectService dashboardCollectService;

    @Value(value="classpath:resource/topicConfig.json")
    private org.springframework.core.io.Resource topicConfig;

    public JsonResult initConsumer(){
        return null;
    }

}
