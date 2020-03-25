package cn.hasfun.framework.springboot.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/news")
@RestController
public class NewsController {

    @GetMapping(value = "/lastest/list")
    public String queryLastestNews(){
        return "news";
    }
}
