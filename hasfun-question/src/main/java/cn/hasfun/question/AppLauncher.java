package cn.hasfun.question;

import cn.hasfun.framework.netty.server.RpcServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用启动器
 */
public class AppLauncher {


    /**
     * 启动的时候加启动参数
     * -Dconfig.properties=file:D:\workspace\hasfun-pom\hasfun-question\src\main\conf\config.properties
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config.xml");
        RpcServer rpcServer = context.getBean(RpcServer.class);
        rpcServer.start();
    }
}
