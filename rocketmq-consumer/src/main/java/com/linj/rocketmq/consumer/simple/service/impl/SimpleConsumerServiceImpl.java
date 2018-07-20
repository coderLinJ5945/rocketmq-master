package com.linj.rocketmq.consumer.simple.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linj.rocketmq.configure.RMQConfigure;
import com.linj.rocketmq.consumer.simple.service.BusinessMessageService;
import com.linj.rocketmq.consumer.simple.service.ConsumerType;
import com.linj.rocketmq.consumer.simple.service.SimpleConsumerService;
import com.linj.rocketmq.result.JsonResult;
import com.linj.rocketmq.util.JsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class SimpleConsumerServiceImpl implements SimpleConsumerService, ConsumerType {
    // TODO: 2018/7/20  添加日志系统
    @Resource
    private RMQConfigure rmqConfigure;

    @Resource
    private MessageListenerConcurrentlyImpl messageListenerConcurrently;

    @Value(value= "classpath:initConfig/topicConfig.json")
    private org.springframework.core.io.Resource topicConfig;

    /**
     * 初始化简单类型的消费者
     * @return
     */
    public JsonResult initSimpleConsumer() {
        try {
            File topicFile = topicConfig.getFile();
            JSONArray  topicConfigJsonA = JSONArray.parseArray(JsonUtil.jsonFile2String(topicFile));
            for (int i = 0; i < topicConfigJsonA.size(); i++) {
                JSONObject topicJson = topicConfigJsonA.getJSONObject(i);
                switch(topicJson.getShort("consumerType")){
                    case SIMPLE:
                        try {
                            runSimplePushConsumerTask(rmqConfigure.getNamesrvAddr(),topicJson.getString("consumerGroup"),topicJson.getJSONArray("subscription"));
                        } catch (MQClientException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 2018/7/20  需要验证 相同的 consumerGroup 和相同的 subscription会不会报错
    // TODO: 2018/7/20  下午实现两种方式的监听消费信息，并且了解其中的优缺点和合适的使用场景
    private  void runSimplePushConsumerTask(final String namesrvAddr,final String consumerGroup, final JSONArray topicJson) throws MQClientException {
        //通过注册监听方式消费信息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        for (int i = 0; i < topicJson.size(); i++) {
            JSONObject json = topicJson.getJSONObject(i);
            consumer.subscribe(json.getString("topic"), json.getString("subExpression"));
        }
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //关闭VIP通道，避免接收不了消息
        consumer.setVipChannelEnabled(false);
        //注册监听
        /*consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExt, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //doSomething
               //todo 这里想办法设计出和业务代码低耦合
                //businessMessageService.sendMessages(messageExt,consumeConcurrentlyContext);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });*/
        consumer.registerMessageListener(messageListenerConcurrently);

        // TODO: 2018/7/20  setMessageListener 和  registerMessageListener 的区别，以及自定义实现方式的学习
        consumer.start();
        System.out.println(consumerGroup+"：启动成功！！！！");
    }

    //todo DefaultMQPullConsumer 通过拉取的方式消费信息
    private static void runSimplePullConsumerTask(){

    }
}
