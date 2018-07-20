package com.linj.rocketmq.consumer.simple.service.impl;

import com.linj.rocketmq.consumer.simple.service.BusinessMessageService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageListenerConcurrentlyImpl implements MessageListenerConcurrently {
    /**
     * 将Spring标签的 业务代码写在MessageListenerConcurrently的实现中，避免的new对监听对象中的注解无效
     */
    @Resource
    private BusinessMessageService businessMessageService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt message : messages) {
            businessMessageService.sendMessages(message,consumeConcurrentlyContext);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
