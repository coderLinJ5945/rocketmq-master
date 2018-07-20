package com.linj.rocketmq.consumer.simple.service;

public interface ConsumerType {
    public static final short BROADCAST = 1;
    public static final short FILTER = 2;
    public static final short OPENMESSAGING = 3;
    public static final short OPERATION = 4;
    public static final short ORDERMESSAGE = 5;
    public static final short SIMPLE = 0;
}
