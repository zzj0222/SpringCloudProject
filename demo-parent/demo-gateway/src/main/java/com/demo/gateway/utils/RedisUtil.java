package com.demo.gateway.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 */
@Component
public class RedisUtil {

    @Resource
    RedisTemplate<String, Object> redisTemplate;


    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return;
    }

    public void set(String key, String value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return;
    }

    public Boolean expire(String key, long expire) {
        return this.expire(key, expire, TimeUnit.SECONDS);
    }

    public Boolean expire(String key, long expire, TimeUnit unit) {
        return redisTemplate.expire(key, expire, unit);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
        return;
    }

    public String get(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        if (null != object) {
            return object.toString();
        } else {
            return null;
        }
    }

    public Boolean hset(String key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
        return this.expire(key, 12, TimeUnit.HOURS);
    }

    public Boolean hset(String key, Object field, Object value, long expire) {
        this.hset(key, field, value);
        if(expire==-1){
            return redisTemplate.expire(key, 99999,TimeUnit.DAYS);
        }else {
            return this.expire(key, expire);
        }
    }



    public Object hget(String key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Object hkeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    public boolean hhasKey(String key, Object field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public Object hentries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Long hremove(String key, Object... fields) {

        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean setNx(final String keyStr, String valueStr, long seconds) {

        boolean result = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
            byte[] key = redisSerializer.serialize(keyStr);
            byte[] value = redisSerializer.serialize(valueStr);
            boolean rs = redisConnection.setNX(key, value);
            if (rs) {
                redisConnection.expire(key, seconds);
            }
            return rs;
        });
        return result;
    }

    public Long incrBy(final String keyStr, int valueStr) {
        Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
            byte[] key = redisSerializer.serialize(keyStr);
            long rs = redisConnection.incrBy(key, valueStr);
            redisConnection.expire(key, 60 * 60 * 24);
            return rs;
        });
        return result;
    }

    public int decrBy(final String keyStr, int valueStr) {
        Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
            byte[] key = redisSerializer.serialize(keyStr);
            long rs = redisConnection.decrBy(key, valueStr);
            redisConnection.expire(key, 60 * 60 * 24);
            return rs;
        });
        return result.intValue();
    }

    public int incrBy(final String keyStr, int valueStr, long seconds) {
        Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
            byte[] key = redisSerializer.serialize(keyStr);
            redisConnection.expire(key, seconds);
            return redisConnection.incrBy(key, valueStr);
        });

        return result.intValue();
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String getString(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

}
