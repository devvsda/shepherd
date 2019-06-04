package com.devsda.platform.shepherdcore.service.cacheservice;

import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.time.Duration;

public class RedisCache {
    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);

    final JedisPoolConfig poolConfig = buildPoolConfig();

    // new JedisPool() for local redis server @6379
    JedisPool jedisPool = new JedisPool("3.90.55.192",6379);
    private JedisPoolConfig buildPoolConfig() {

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(10);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public void put(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            log.info("Connected to Redis instance? :"+ jedis.isConnected());
            jedis.set(key, value);
        }catch (Exception ex){
            log.error("Could not set key-Value in the redis", ex);
        }
    }

    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            log.info("Connected to Redis instance? :"+ jedis.isConnected());
            return jedis.get(key);
        }catch (Exception ex){
            log.error("Could not get value corresponding to the key redis", ex);
        }
        return null;
    }

    public String hget(String key,String id) {
        try (Jedis jedis = jedisPool.getResource()) {
            log.info("Connected to Redis instance? :"+ jedis.isConnected());
            return jedis.hget(key,id);
        }catch (Exception ex){
            log.error("Could not get value corresponding to the key redis", ex);
        }
        return null;
    }


    public void hset(String key,String id,String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            log.info("Connected to Redis instance? :"+ jedis.isConnected());
            jedis.hset(key, id, value);

        }catch (Exception ex){
            log.error("Could not get value corresponding to the key redis", ex);
        }
    }



}
