package com.example.tutorial.CenterManagement.caching;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private final RedisTemplate redisTemplate;

    public <T extends Object> void saveExpireDataInRedis(String key, T t, long timeout) {
        redisTemplate.opsForValue().set(key, t);
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
    }

    public <T extends Object> Optional<T> getValueFromRedis(String key) {
        Object redisResult = redisTemplate.opsForValue().get(key);
        if (redisResult == null)
            return Optional.empty();
        Optional<T> result = Optional.of((T) redisResult);
        return result;
    }

    public <T extends Object> void pushToQueue(final String queueName, T t) {
        redisTemplate.opsForList().leftPush(queueName, t);
    }
    public <T> Optional<T> popFromQueue(final String queueName, boolean shouldRemove) {
        if (redisTemplate.hasKey(queueName)) {
            T val = (T) (shouldRemove ? redisTemplate.opsForList().rightPop(queueName) : redisTemplate.opsForList().range(queueName, -1, -1).get(0));
            if (val != null)
                return Optional.of(val);
            else
                return Optional.empty();

        }
        return Optional.empty();
    }


    public <T extends Enum> boolean hasSmsMore(final String queueName) {
        return redisTemplate.hasKey(queueName);
    }

    public void incrementByOneKey(Object hashKey) {
        redisTemplate.opsForHash().increment("metrics", hashKey, 1);
    }
    public <T extends Object> Optional<T>  getAllHashKeyValues(String key) {
        return  Optional.ofNullable((T) redisTemplate.opsForHash().entries(key));
    }

    public Long saveOrIncrementExpireDataInRedis(String key, int delta, long timeout) {
        if (!redisTemplate.hasKey(key)) {
            this.saveExpireDataInRedis(key, delta, timeout);
            return Long.valueOf(delta);
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    public Long saveOrIncrementExpireDataInRedis(String key, int delta, long timeout, TimeUnit timeUnit) {
        if (!redisTemplate.hasKey(key)) {
            this.saveExpireDataInRedis(key, delta, timeout, timeUnit);
            return Long.valueOf(delta);
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    public <H extends Object, K extends Object, V extends Object, T> void setHash(H hashKey, K key, V value, Class<T> clazz) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    public <H extends Object, K extends Object, T> Optional<T> getHash(H hashKey, K key, Class<T> clazz) {
        return Optional.ofNullable((T) redisTemplate.opsForHash().get(hashKey, key));
    }

    public <T extends Object> void saveExpireDataInRedis(String key, T t, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, t);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public <T extends Object> void setValueInRedis(String key, T t) {
        redisTemplate.opsForValue().set(key, t);

    }
}
