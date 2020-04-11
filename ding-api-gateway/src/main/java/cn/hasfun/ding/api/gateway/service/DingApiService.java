package cn.hasfun.ding.api.gateway.service;

import java.util.Map;

public interface DingApiService {

    String getAccessToken(String appName) throws Exception;

    Map<String,Object> queryUserInfo(String appName,String code) throws Exception;

    Map<String,Object> getJsApiTicket(String appName) throws Exception;

    Map<String,Object> getJsApiInfo(String appName,String url) throws Exception;

    Map<String,Object> queryUserByUserId(String appName, String code) throws Exception;
}
