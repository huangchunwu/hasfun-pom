package cn.hasfun.framework.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Springboot redis 例子
 * 1： <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-data-redis</artifactId>
 *         </dependency>
 * 2： @Autowired
 *     private StringRedisTemplate stringRedisTemplate;
 *
 */
@Service
public class RedisExampleService {


    @Autowired
    private RedisTemplate redisTemplate;


    public  void setStringValue(String value){
        redisTemplate.boundValueOps("news-1001").set(value);
    }

    public Map<Object,Object> getAll() {
        Map<Object,Object> result = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(key->{
            result.put(key,0);
        });
        return result;
    }
}
