package cn.hasfun.ding.api.gateway.service.impl;

import cn.hasfun.ding.api.gateway.config.DingConfig;
import cn.hasfun.ding.api.gateway.repo.DingOpenApiRepo;
import cn.hasfun.ding.api.gateway.service.DingApiService;
import com.dingtalk.api.response.OapiUserGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author huangchunwu
 */
@SuppressWarnings("ALL")
@Service
public class DingApiServiceImpl implements DingApiService {

    @Autowired
    private DingOpenApiRepo dingOpenApiRepo;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DingConfig dingConfig;

    @Override
    public String getAccessToken(String appName) throws Exception {
        String key = appName + "_" + "accesstoken";
        Object accessToken = redisTemplate.opsForValue().get(key);
        if (accessToken == null) {
            var response = dingOpenApiRepo.getAccessToken(appName);
            accessToken = response.getAccessToken();
            Long expireIn = response.getExpiresIn();
            redisTemplate.opsForValue().set(key, accessToken, expireIn, TimeUnit.SECONDS);

        }
        return accessToken.toString();
    }

    @Override
    public Map<String, Object> queryUserInfo(String appName, String code) throws Exception {
        var userGetuserinfoResponse = dingOpenApiRepo.getUserInfo(getAccessToken(appName), code);
        var result = new HashMap<String, Object>(4);
        result.put("userId", userGetuserinfoResponse.getUserid());
        result.put("deviceId", userGetuserinfoResponse.getDeviceId());
        result.put("isSys", userGetuserinfoResponse.getIsSys());
        result.put("sysLevel", userGetuserinfoResponse.getSysLevel());
        return result;
    }

    private String getUserId(String appName, String code) throws Exception {
        var userGetuserinfoResponse = dingOpenApiRepo.getUserInfo(getAccessToken(appName), code);
        return userGetuserinfoResponse.getUserid();
    }

    @Override
    public Map<String, Object> getJsApiTicket(String appName) throws Exception {
        String key = appName + "_" + "jsapiticket";
        Object jsApiTicker = redisTemplate.opsForValue().get(key);
        if (jsApiTicker == null) {
            var jsapiTicketResponse = dingOpenApiRepo.getJsApiTicket(getAccessToken(appName));
            jsApiTicker = jsapiTicketResponse.getTicket();
            Long expireIn = jsapiTicketResponse.getExpiresIn();
            redisTemplate.opsForValue().set(key, jsApiTicker.toString(), expireIn, TimeUnit.SECONDS);
        }
        var result = Map.of(key, jsApiTicker);
        return result;
    }

    @Override
    public Map<String, Object> getJsApiInfo(String appName, String url) throws Exception {
        String key = appName + "_" + "jsapiticket";
        Map<String, Object> jsApiTicketMap = getJsApiTicket(appName);
        Object jsApiTicket = jsApiTicketMap.get(key);
        long timeStamp = System.currentTimeMillis() / 1000;
        String nonceStr = "hcwNo1";
        String  signature = sign(jsApiTicket.toString(),nonceStr,timeStamp,url);
        Map<String, Object> result = new HashMap<>(5);
        result.put("agentId",dingConfig.getAgentId());
        result.put("corpId",dingConfig.getCorId());
        result.put("timeStamp",timeStamp);
        result.put("nonceStr",nonceStr);
        result.put("signature",signature);
        return result;
    }





    public  String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.reset();
        sha1.update(plain.getBytes("UTF-8"));
        return byteToHex(sha1.digest());

    }


    /**
     * 字节数组转化成十六进制字符串
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    @Override
    public Map<String, Object> queryUserByUserId(String appName, String code) throws Exception{
        String  userId = this.getUserId(appName,code);
        OapiUserGetResponse response = dingOpenApiRepo.getUserByUserId(getAccessToken(appName),userId);
        Map<String, Object> result = Map.of("name",response.getName(),"userId",response.getUserid(),"jobNumber",response.getJobnumber(),"extattr",response.getExtattr(),"departMent",response.getDepartment());
        return result;
    }
}
