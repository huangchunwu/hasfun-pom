package cn.hasfun.framework.proxy;

import cn.hasfun.framework.netty.second.server.UserQuery;
import cn.hasfun.framework.netty.second.server.UserRpcService;

public class DelegateClass implements UserRpcService {

    public DelegateClass() {
    }


    @Override
    public void queryUserInfo(UserQuery userQuery) {
        //System.out.println("This is queryUserInfo method");
    }
}
