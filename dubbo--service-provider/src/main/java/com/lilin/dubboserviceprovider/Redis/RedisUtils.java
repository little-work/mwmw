package com.lilin.dubboserviceprovider.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {


    private RedisProperties redisProperties;

    private static JedisPool jedisPool = null;

    public RedisUtils() {
    }

    public RedisUtils(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    /**
     * 获取Jedis实例
     */
    public Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisProperties.getMaxActive());
        config.setMaxIdle(redisProperties.getMaxIdle());
        config.setMaxWaitMillis(redisProperties.getMaxWait());
        jedisPool = new JedisPool(config,
                redisProperties.getAddress(),
                redisProperties.getPort(), redisProperties.getTimeout(),
                redisProperties.getPassword(), redisProperties.getDefaultDatabase());
        return jedisPool.getResource();
    }

    /***
     * 释放资源
     */
    public  void returnResource(final Jedis jedis) {
        if(jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
