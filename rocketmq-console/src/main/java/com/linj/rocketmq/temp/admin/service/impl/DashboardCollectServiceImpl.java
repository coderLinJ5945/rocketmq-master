/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.linj.rocketmq.temp.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.linj.rocketmq.temp.admin.service.DashboardCollectService;
import com.linj.rocketmq.temp.config.RMQConfigure;
import com.linj.rocketmq.temp.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DashboardCollectServiceImpl implements DashboardCollectService {

    @Resource
    private RMQConfigure rmqConfigure;

    private final static Logger log = LoggerFactory.getLogger(DashboardCollectServiceImpl.class);

    /**
     * 从guava 缓存中获取 brokerMap todo 了解CacheBuilder 缓存
     */
    private LoadingCache<String, List<String>> brokerMap = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .concurrencyLevel(10)
        .recordStats()
        .ticker(Ticker.systemTicker())
        .removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
                log.debug(notification.getKey() + " was removed, cause is " + notification.getCause());
            }
        })
        .build(
            new CacheLoader<String, List<String>>() {
                @Override
                public List<String> load(String key) {
                    List<String> list = Lists.newArrayList();
                    return list;
                }
            }
        );

    private LoadingCache<String, List<String>> topicMap = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .concurrencyLevel(10)
        .recordStats()
        .ticker(Ticker.systemTicker())
        .removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
                log.debug(notification.getKey() + " was removed, cause is " + notification.getCause());
            }
        })
        .build(
            new CacheLoader<String, List<String>>() {
                @Override
                public List<String> load(String key) {
                    List<String> list = Lists.newArrayList();
                    return list;
                }
            }
        );

    @Override
    public LoadingCache<String, List<String>> getBrokerMap() {
        return brokerMap;
    }
    @Override
    public LoadingCache<String, List<String>> getTopicMap() {
        return topicMap;
    }

    /**
     * 将从MQ中读取的 node 节点 和 topic 数据缓存到本地的tmp临时文件中，
     * 当再次初始化的时候读取该文件
     * todo ： 需要找到啥时候生成的.json文件
     * @param file todo： 猜测应该是生成的零时文件 \tmp\rocketmq-console\data\dashboard2018-07-18.json
     * @return
     */
    @Override
    public Map<String, List<String>> jsonDataFile2map(File file) {
        List<String> strings;
        try {
            strings = Files.readLines(file, Charsets.UTF_8);
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
        StringBuffer sb = new StringBuffer();
        for (String string : strings) {
            sb.append(string);
        }
        JSONObject json = (JSONObject) JSONObject.parse(sb.toString());
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        Map<String, List<String>> map = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : entries) {
            JSONArray tpsArray = (JSONArray) entry.getValue();
            if (tpsArray == null) {
                continue;
            }
            Object[] tpsStrArray = tpsArray.toArray();
            List<String> tpsList = Lists.newArrayList();
            for (Object tpsObj : tpsStrArray) {
                tpsList.add("" + tpsObj);
            }
            map.put(entry.getKey(), tpsList);
        }
        return map;
    }

    @Override
    public Map<String, List<String>> getBrokerCache(String date) {
        String dataLocationPath = rmqConfigure.getConsoleCollectData();
        File file = new File(dataLocationPath + date + ".json");
        if (!file.exists()) {
            throw Throwables.propagate(new ServiceException(1, "This date have't data!"));
        }
        return jsonDataFile2map(file);
    }

    @Override
    public Map<String, List<String>> getTopicCache(String date) {
        String dataLocationPath = rmqConfigure.getConsoleCollectData();
        File file = new File(dataLocationPath + date + "_topic" + ".json");
        if (!file.exists()) {
            throw Throwables.propagate(new ServiceException(1, "This date have't data!"));
        }
        return jsonDataFile2map(file);
    }

}