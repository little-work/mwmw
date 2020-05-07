package com.lilin.dubboserviceprovider.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

@Component
public class RedisLock {


    @Autowired
    private RedisUtils redisUtils;

    private static final String KEY = "lock";

    //线程私有
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public void lock() {
        if (tryLock(5)) {
            return;
        } else {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //再去获取锁
            lock();
        }
    }

    /**
     *  NX – 只有键key不存在的时候才会设置key的值
     * XX – 只有键key存在的时候才会设置key的值
     *  EX seconds – 设置键key的过期时间，单位时秒
     *  PX milliseconds – 设置键key的过期时间，单位时毫秒
     *
     * @param seconds
     * @return
     */
    public boolean tryLock(int seconds) {
        Jedis jedis = redisUtils.getJedis();

        //设置键和过期时间需要一起执行否则不满足一致性  因为Redis中的命令都是在队列中没有顺序
        /**
         * 我们的加锁代码满足我们可靠性里描述的三个条件。
         * 首先，set()加入了NX参数，可以保证如果已有key存在，则函数不会调用成功，
         * 也就是只有一个客户端能持有锁，满足互斥性。其次，由于我们对锁设置了过期时间，
         * 即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），
         * 不会发生死锁。最后，因为我们将value赋值为requestId，代表加锁的客户端请求标识，
         * 那么在客户端在解锁的时候就可以进行校验是否是同一个客户端
         */
        String value = UUID.randomUUID().toString();
        String retult = jedis.set(KEY, value, "NX", "EX", seconds);
        jedis.close();
        if ("OK".equalsIgnoreCase(retult)) {
            //拿到了锁
            threadLocal.set(value);
            return true;
        }
        return false;
    }

    /**
     * 解锁方法
     *
     * @return
     */
    public boolean release() {
        Jedis jedis = redisUtils.getJedis();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(KEY), Collections.singletonList(threadLocal.get()));

        if (Integer.valueOf(result.toString()) == 0) {
            jedis.close();
            throw new RuntimeException("解锁失败");
        }
        jedis.close();
        return true;
    }
}
