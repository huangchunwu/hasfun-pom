package cn.hasfun.ding.api.gateway.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author huangchunwu
 */
@Data
@ToString
public class AppConfig {

    private String appName;
    private String appKey;
    private String appSecret;
}
