package com.linj.rocketmq.consumer.simple.service;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 业务逻辑代码，方便解耦操作
 */
public interface BusinessMessageService {
    /**
     * 发送消息的业务中接口 todo 后期需要设计合理的返回值
     * @param consumeConcurrentlyContext
     */
    void sendMessages(MessageExt message, ConsumeConcurrentlyContext consumeConcurrentlyContext);

}
