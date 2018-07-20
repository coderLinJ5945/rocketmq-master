package com.linj.rocketmq.consumer.simple.service.impl;

import com.linj.rocketmq.configure.RMQConfigure;
import com.linj.rocketmq.consumer.simple.service.BusinessMessageService;
import com.linj.rocketmq.consumer.simple.service.TestService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class BusinessMessageServiceImpl implements BusinessMessageService {

    @Resource
    private RMQConfigure rmqConfigure;

    @Resource
    private TestService testService;

    /**
     * 发送消息的业务中接口 todo 后期需要设计合理的返回值
     * @param consumeConcurrentlyContext
     */
    public void sendMessages(MessageExt message , ConsumeConcurrentlyContext consumeConcurrentlyContext){
        System.out.println(testService.test());
        System.out.println(rmqConfigure.getNamesrvAddr());
        try {
            System.out.println(new String(message.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
