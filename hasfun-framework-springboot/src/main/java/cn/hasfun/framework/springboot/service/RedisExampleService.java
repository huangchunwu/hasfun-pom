package cn.hasfun.framework.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
    private StringRedisTemplate stringRedisTemplate;

    public  void setStringValue(String value){
        stringRedisTemplate.boundValueOps("news-1001").set(value);
    }

}
