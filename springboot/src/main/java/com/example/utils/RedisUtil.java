package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil<T> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 当key值不存在时新增缓存返回true，否则返回false
     */
    public boolean setNx(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 向redis中set集合添加值，点赞
     */
    public void sAdd(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
    }

    /**
     * 向redis中set集合移除，取消点赞
     */
    public Long srem(String key, String value) {
        return stringRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 判断key，value是否是集中值
     */
    public boolean isMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合大小
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 获取集合里所有数据
     */
    public Set<String> sMembers(String key) {
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        return members;
    }

    /**
     * 初始化
     */
    public void set(String key) {
        stringRedisTemplate.opsForValue().set(key, "0");
    }

    /**
     * 计数
     */
    public Long incr(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }
}
