package com.linj.rocketmq.consumer.simple.service;

import com.linj.rocketmq.result.JsonResult;

public interface SimpleConsumerService {
    /**
     * 初始化简单类型的消费者
     * @return
     */
    JsonResult initSimpleConsumer();
}
