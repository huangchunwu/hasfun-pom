package cn.hasfun.controller;

import cn.hasfun.framework.entity.ExecResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "")
public class TestController {


    @RequestMapping(value = "/get")
    @ResponseBody
    public ExecResult getData() {
        return ExecResult.getSuccessResult();
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String getSUCCESS() {
        return "SUCCESS";
    }

}
