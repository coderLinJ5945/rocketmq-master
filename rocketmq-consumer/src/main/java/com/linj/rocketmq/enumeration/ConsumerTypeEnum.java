package com.linj.rocketmq.enumeration;

/**
 * 消费者类型枚举（这里的类型指的是消费者实现类型，而非业务对象类型）
 */
public enum ConsumerTypeEnum {
    BROADCAST("broadcast","广播消息"),
    FILTER("filter","过滤消息"),
    OPENMESSAGING("openmessaging","开放消息"),
    OPERATION("operation","操作消息"),
    ORDERMESSAGE("ordermessage","订单消息"),
    SIMPLE("simple","简单消息");

    String typeCode;
    String desc;

    private ConsumerTypeEnum(String typeCode, String desc){
        this.typeCode = typeCode;
        this.desc = desc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getDesc() {
        return desc;
    }
}
