package com.lilin.dubboserviceprovider.Redis;

import com.lilin.dubboserviceprovider.mybatis.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {


    private RedisProperties redisProperties;

    private static JedisPool jedisPool = null;

    @Autowired
    private UserDao userDao;
    @Autowired
    RedisLock redisLock;

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

    /**
     * 从缓存中去数据  如果没有的话那么从数据库中取
     * 单个jvm服务器解决缓存穿透
     *
     * @param key
     * @return
     */
    public String getValueSingleJVM(String key) {
        Jedis jedis = this.getJedis();
        String value;
        if (jedis == null) {
            throw new RuntimeException("取法获取jedis实例，请检查");
        }
        synchronized (this) {
            value = jedis.get(key);
            if (value == null) {
                //单机模式的话可以解决缓存击穿问题
                System.out.println("查数据库");
                value = userDao.findUser().get(0).getUsername();
                jedis.set(key, value);
            }
            jedis.close();
        }
        return value;
    }

    /**
     * 分布式解决缓存穿透
     *
     * @param key
     * @return
     */
    public String getValueMoreThread(String key) {
        try{
            Jedis jedis = getJedis();
            String value = null;
            if (jedis == null) {
                throw new RuntimeException("取法获取jedis实例，请检查");
            }
            redisLock.lock();
            value = jedis.get(key);
            if (value == null) {
                //单机模式的话可以解决缓存击穿问题
                System.out.println("查数据库");
                value = userDao.findUser().get(0).getUsername();
                jedis.set(key, value);
            }
            jedis.close();
            return value;
        }finally {
            redisLock.release();
        }

    }

    /**
     * 设置某个键的超时时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        Jedis jedis = getJedis();
        Long result = null;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

    /**
     * 删除某个键
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = getJedis();
        Long result = null;
        try {
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }
}
