package cn.hasfun.framework.netty.second.sevice;

import cn.hasfun.framework.netty.second.server.UserQuery;
import cn.hasfun.framework.netty.second.server.UserRpcService;

public class LoginServiceImpl implements LoginService {

    private UserRpcService userRpcService;

    public void login(String userName,String passward){
        UserQuery  userQuery =UserQuery.builder().name(userName).password(passward).build();
        userRpcService.queryUserInfo(userQuery);
    }
}
