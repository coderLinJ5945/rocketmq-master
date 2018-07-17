package com.linj.rocketmq.consumer.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.apache.rocketmq.client.ClientConfig.SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY;

/**
 * rocketmq 配置类
 */
//配置文件标签
@Configuration
//配置 application.properties文件中以rocketmq.config开头的key
@ConfigurationProperties(prefix = "rocketmq.config")
public class RMQConfigure {

    /**
     * volatile:同步机制，当运行内存（从内存）变量改变时，会立即通知到其他线程，性能优于synchronized 锁，但是使用的环境条件有限
     * 出于性能考虑使用volatile，而不使用synchronized 锁
     * volatile使用环境：
     * 1、volatile修饰的变量不依赖本身的结果，或者 只有单一线程改变其变量
     * 2、变量不依赖其他状态变量共同参与约束（状态变量可以简单理解成为开关的状态）
     * namesrvAddr 服务器地址
     */
    // nameAddr 使用 MixAll 类中的配置 todo 需要解决 MixAll 类的作用
    private volatile String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));
    //是否开启VIP通道 com.rocketmq.sendMessageWithVIPChannel
    private volatile String isVIPChannel = System.getProperty(SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY, "true");

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        if (StringUtils.isNotBlank(namesrvAddr)) {
            this.namesrvAddr = namesrvAddr;
            System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, namesrvAddr);
            //logger.info("setNameSrvAddrByProperty nameSrvAddr={}", namesrvAddr);
        }
    }

    public String getIsVIPChannel() {
        return isVIPChannel;
    }

    public void setIsVIPChannel(String isVIPChannel) {
        this.isVIPChannel = isVIPChannel;
    }

//private String dataPath;

    //private boolean enableDashBoardCollect;
}
