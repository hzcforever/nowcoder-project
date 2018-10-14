package com.nowcoder.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    public static void print(int index, Object obj) {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://127.0.0.1:6379/1");
//        jedis.flushDB();
//        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
//        jedis.setex("bye", 10, "vanish");
//        print(2, jedis.get("bye"));

        jedis.set("pv", "100");
        jedis.incr("pv");
        print(3, jedis.get("pv"));
        jedis.incrBy("pv", 100);
        jedis.decrBy("pv", 50);
        print(3, jedis.get("pv"));

        print(4, jedis.keys("*"));

        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(4, jedis.lrange(listName, 0, 3));
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.lrange(listName, 0, 10));
        print(8, jedis.lindex(listName, 2));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://127.0.0.1:6379/1");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常!" +e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常!" +e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常!" +e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常!" +e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}