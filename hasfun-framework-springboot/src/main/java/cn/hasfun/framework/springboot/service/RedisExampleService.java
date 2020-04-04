package cn.hasfun.framework.springboot.service;


import cn.hasfun.framework.springboot.constant.Field;
import cn.hasfun.framework.springboot.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ClusterOperations;
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
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisUtil redisUtil;


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


    public String queryClusterData(){
       return redisConnectionFactory.getClusterConnection().get("name".getBytes()).toString();
    }

    public Map<String, Object> save(String key, String value) {
        Map<String, Object> result = new HashMap<>();
        result.put(key,redisConnectionFactory.getClusterConnection().set(key.getBytes(),value.getBytes()));
        return result;
    }

    public Map<String, Object> save2(String key, String value) {
        Map<String, Object> result = new HashMap<>();
        result.put(Field.RESULT,"1");
        result.put(key,redisUtil.setCacheMap(key,result));
        return result;
    }

    public Map<String, Object> save3(String key, String value) {
        Map<String, Object> result = new HashMap<>();
        redisTemplate.boundValueOps(key).set(value);
        result.put(key,"");
        return result;
    }
}
