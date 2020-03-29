package cn.hasfun.framework.springboot.controller;

import cn.hasfun.framework.springboot.service.RedisExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
