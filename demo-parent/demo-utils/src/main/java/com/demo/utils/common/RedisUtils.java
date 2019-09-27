package com.demo.utils.common;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.lang.Collections;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author zheng.shk
 * @Date 10:25 2018/4/23
 */
@Component
public class RedisUtils {

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

    public <T> List<T> batchGet(List<String> keys, Class<T> cls) {
        List<Object> cacheStringList = redisTemplate.opsForValue().multiGet(keys);
        if (Collections.isEmpty(cacheStringList)) {
            return null;
        }
        return cacheStringList.stream().filter(cacheString -> cacheString != null).map(cacheString -> {
            return JSONObject.parseObject(cacheString.toString(), cls);
        }).collect(Collectors.toList());
    }
    public <T> Map<String, T> batchGet(List<String> queryKeys, String resultKey, Class<T> cls) {
        Map<String, T> resultMap = new ConcurrentHashMap<String, T>();
        List<Object> cacheStringList = redisTemplate.opsForValue().multiGet(queryKeys);
        if (!Collections.isEmpty(cacheStringList)) {
            cacheStringList.stream().filter(cacheString -> cacheString != null).forEach(cacheString -> {
                JSONObject jsonObject = JSONObject.parseObject(cacheString.toString());
                resultMap.put(jsonObject.get(resultKey).toString(), JSONObject.toJavaObject(jsonObject, cls));
            });
        }
        return resultMap;
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

    public void hset(String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void hset(String key, Map map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public <T> Map<String, Map<String, T>> batchHgetAll(List<String> queryKeys, String resultKey, Class<T> cls) {
        Map<String, Map<String, T>> responseMap = new ConcurrentHashMap<String, Map<String, T>>();
        RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
        List<Object> resultList = redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (String keys : queryKeys) {
                    byte[] key = redisSerializer.serialize(keys);
                    connection.hGetAll(key);
                }
                return connection.closePipeline();
            }
        });
        if (!Collections.isEmpty(resultList)) {
            resultList.stream().filter(cacheObj -> cacheObj != null).forEach(cacheObj -> {
                Map<byte[], byte[]> cacheMap = (Map<byte[], byte[]>) cacheObj;
                Map<String, T> resultMap = new ConcurrentHashMap<String, T>();
                Iterator<byte[]> iterator = cacheMap.keySet().iterator();
                String outerKey = null;
                while (iterator.hasNext()) {
                    byte[] key = iterator.next();
                    byte[] value = cacheMap.get(key);
                    String stringKey = redisSerializer.deserialize(key);
                    String stringVal = redisSerializer.deserialize(value);
                    stringVal = stringVal.replaceAll("\\\\", "");
                    stringVal = stringVal.substring(1, stringVal.length() - 1);
                    JSONObject jsonObject = JSONObject.parseObject(stringVal);
                    outerKey = jsonObject.get(resultKey).toString();
                    resultMap.put(stringKey, JSONObject.toJavaObject(jsonObject, cls));
                }
                if(resultMap.size() > 0)
                {
                    responseMap.put(outerKey, resultMap);
                }
            });
        }
        return responseMap;
    }

    public <T> Map<String, T> batchHget(List<String[]> queryKeys, String resultKey, Class<T> cls) {
        Map<String, T> resultMap = new ConcurrentHashMap<String, T>();
        RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
        List<Object> resultList = redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (String[] keys : queryKeys) {
                    byte[] key = redisSerializer.serialize(keys[0]);
                    byte[] field = redisSerializer.serialize(keys[1]);
                    connection.hGet(key, field);
                }
                return connection.closePipeline();
            }
        });
        if (!Collections.isEmpty(resultList)) {
            resultList.stream().filter(cacheObj -> cacheObj != null).forEach(cacheObj -> {
                String cacheStr = redisSerializer.deserialize((byte[]) cacheObj);
                cacheStr = cacheStr.replaceAll("\\\\", "");
                cacheStr = cacheStr.substring(1, cacheStr.length() - 1);
                JSONObject jsonObject = JSONObject.parseObject(cacheStr);
                resultMap.put(jsonObject.get(resultKey).toString(), JSONObject.toJavaObject(jsonObject, cls));
            });
        }
        return resultMap;
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

    public int incrBy(final String keyStr, int valueStr) {
        Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
            byte[] key = redisSerializer.serialize(keyStr);
            long rs = redisConnection.incrBy(key, valueStr);
            redisConnection.expire(key, 60 * 60 * 24);
            return rs;
        });
        return result.intValue();
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
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
