package cn.hasfun.framework.netty.second.server;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuery {

    private String name;

    private String password;

}
