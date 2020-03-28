package cn.hasfun.framework.springboot.controller;


import cn.hasfun.framework.springboot.service.RedisExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/news")
@RestController
public class NewsController {

    @Autowired
    private RedisExampleService redisExampleService;

    @GetMapping(value = "/lastest/list")
    public String queryLastestNews(){
        return "news";
    }

    @GetMapping(value = "/add")
    public String addOneNews(String content){
        redisExampleService.setStringValue(content);
        return "SUCCESS";
    }
}
