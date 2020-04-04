package cn.hasfun.framework.springboot.controller;

import cn.hasfun.framework.springboot.service.RedisExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequestMapping(value = "/redis")
@RestController
public class RedisServerController {

    @Autowired
    private RedisExampleService redisExampleService;

    @GetMapping(value = "/all")
    public Map<Object, Object> getAll(){
        return redisExampleService.getAll();
    }

    @GetMapping(value = "/get")
    public String get(String key){
        return redisExampleService.get(key);
    }

    @GetMapping(value = "/add")
    public Map<String,Object> save(String key,String value){
        return redisExampleService.save(key,value);
    }

    @GetMapping(value = "/add2")
    public Map<String,Object> save2(String key,String value){
        return redisExampleService.save2(key,value);
    }

}
