package cn.hasfun.ding.api.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "ding")
@Data
public class DingConfig {

    private String corId;

    private String agentId;

    private List<AppConfig> apps;

    public AppConfig getByAppName(String appName){
        return apps.stream().filter(v->v.getAppName().equals(appName)).findFirst().orElse(null);
    }


}




