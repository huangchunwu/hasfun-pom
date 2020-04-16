package cn.hasfun.ding.api.gateway.controller;

import cn.hasfun.ding.api.gateway.service.DingApiService;
import cn.hasfun.framework.entity.ExecResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangchunwu
 */
@RestController
@RequestMapping(value = "/dd")
public class DingApiController {

    @Autowired
    private DingApiService dingApiService;


    @RequestMapping(value = "/user/get")
    public ExecResult queryUserByUserId(@RequestParam(value = "appName")String appName,@RequestParam(value = "code")String code) throws Exception {
        return ExecResult.getSuccessResult(dingApiService.queryUserByUserId(appName,code));
    }

    @RequestMapping(value = "/jsapi/get")
    public ExecResult getJsApiInfo(@RequestParam(value = "appName")String appName,@RequestParam(value = "url")String url) throws Exception {
        return ExecResult.getSuccessResult(dingApiService.getJsApiInfo(appName, url));
    }
}
