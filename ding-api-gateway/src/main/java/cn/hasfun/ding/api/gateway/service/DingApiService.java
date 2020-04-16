package cn.hasfun.ding.api.gateway.service;

import java.util.Map;

/**
 * @author huangchunwu
 */
public interface DingApiService {

    /**
     * 获取钉钉token
     * @param appName
     * @return
     * @throws Exception
     */
    String getAccessToken(String appName) throws Exception;

    /**
     * 查询钉钉用户信息
     * @param appName
     * @param code
     * @return
     * @throws Exception
     */
    Map<String,Object> queryUserInfo(String appName,String code) throws Exception;

    /**
     * 查询jsapiTicket
     * @param appName
     * @return
     * @throws Exception
     */
    Map<String,Object> getJsApiTicket(String appName) throws Exception;

    /**
     * 获取jsApi的参数
     * @param appName
     * @param url
     * @return
     * @throws Exception
     */
    Map<String,Object> getJsApiInfo(String appName,String url) throws Exception;

    /**
     * 获取钉钉用户信息
     * @param appName
     * @param code
     * @return
     * @throws Exception
     */
    Map<String,Object> queryUserByUserId(String appName, String code) throws Exception;
}
