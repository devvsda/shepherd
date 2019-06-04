package com.devsda.platform.shepherdcore.service.cacheservice;

import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RedisCacheTest {

    static RedisCache redisCache;

    @BeforeClass
    public static void setup() {
        redisCache = new RedisCache();
    }

    @Test
    public void connectionTest1() throws Exception {
        redisCache.put("key","Value");
        String result= redisCache.get("key");
        System.out.println(result);
    }

    @Test
    public void connectionTest2() throws Exception {
        redisCache.hset("cliendIDendpointID","graph","THis is my graph");
        redisCache.hset("cliendIDendpointID","endpointDetails","THis is my endpoint details");
        String result1= redisCache.hget("cliendIDendpointID", "graph");
        String result2= redisCache.hget("cliendIDendpointID", "endpointDetails");
        System.out.println(result1);
        System.out.println(result2);
    }

}
