package cn.hasfun.ding.api.gateway.repo;

import cn.hasfun.ding.api.gateway.config.AppConfig;
import cn.hasfun.ding.api.gateway.config.DingConfig;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangchunwu
 */
@Slf4j
@Service
public class DingOpenApiRepo {

    @Autowired
    private DingConfig dingConfig;


    public OapiGettokenResponse getAccessToken(String appName) throws Exception {
        AppConfig appConfig = dingConfig.getByAppName(appName);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey(appConfig.getAppKey());
        req.setAppsecret(appConfig.getAppSecret());
        req.setHttpMethod("GET");
        OapiGettokenResponse response =  client.execute(req);
        log.info("http invoke: https://oapi.dingtalk.com/gettoken?appkey={}&appSecret={},response:{}",appConfig.getAppKey(),appConfig.getAppName(), response.toString());
        return  response;
    }


    public OapiGetJsapiTicketResponse getJsApiTicket(String accessToken) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setHttpMethod("GET");
        OapiGetJsapiTicketResponse response =  client.execute(req, accessToken);
        log.info("http invoke: https://oapi.dingtalk.com/get_jsapi_ticket?accessToken={},response:{}",accessToken, response.toString());
        return response;
    }

    public OapiUserGetuserinfoResponse getUserInfo(String accessToken,String code) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest req = new OapiUserGetuserinfoRequest();
        req.setHttpMethod("GET");
        req.setCode(code);
        OapiUserGetuserinfoResponse response = client.execute(req, accessToken);
        log.info("http invoke: https://oapi.dingtalk.com/getuserinfo?code={}&accessToken={},response:{}",code,accessToken, response.toString());
        return response;
    }


    public OapiUserGetResponse getUserByUserId(String accessToken,String userId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest req = new OapiUserGetRequest();
        req.setHttpMethod("GET");
        req.setUserid(userId);
        OapiUserGetResponse response= client.execute(req, accessToken);
        log.info("http invoke: https://oapi.dingtalk.com/user/get?userid={}&accessToken={},response:{}",userId,accessToken, response.toString());
        return response;
    }


}
