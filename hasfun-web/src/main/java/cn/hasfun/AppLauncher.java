package cn.hasfun;

import cn.hasfun.framework.netty.HttpServer;
import cn.hasfun.framework.netty.client.RpcClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用启动器
 */
public class AppLauncher {


    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) throws Exception {
        context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config.xml");

        HttpServer httpServer = context.getBean(HttpServer.class);
        httpServer.start();
    }
}
