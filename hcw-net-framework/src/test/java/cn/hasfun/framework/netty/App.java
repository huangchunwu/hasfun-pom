package cn.hasfun.framework.netty;

import cn.hasfun.framework.netty.client.RpcClient;
import cn.hasfun.framework.netty.server.RpcServer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Executors;

public class App {

    private static ApplicationContext context;


    /**
     * 启动的时候加启动参数
     * -Dconfig.properties=file:D:\workspace\hasfun-pom\hcw-net-framework\src\main\conf\config.properties
     */
    @Test
    public void startServer() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config-server.xml");
        RpcServer rpcServer = context.getBean(RpcServer.class);
        rpcServer.start();
    }

    /**
     * 启动的时候加启动参数
     * -Dconfig.properties=file:D:\workspace\hasfun-pom\hcw-net-framework\src\main\conf\config.properties
     */
    @Test
    public void startClient() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config-client.xml");
        RpcClient rpcClient = context.getBean(RpcClient.class);
        rpcClient.start();

        for (int i = 0; i < 10; i++) {
            Executors.newSingleThreadExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    rpcClient.sendPacket();
                }
            });
        }
        Thread.currentThread().sleep(1000*1000);
    }

    /**
     * 启动的时候加启动参数
     * -Dconfig.properties=file:D:\workspace\hasfun-pom\hcw-net-framework\src\main\conf\config.properties
     */
    @Test
    public void startClient2() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config-client.xml");
        RpcClient rpcClient = context.getBean(RpcClient.class);
        rpcClient.start();

        for (int i = 0; i < 10; i++) {
            Executors.newSingleThreadExecutor().submit(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
        Thread.currentThread().sleep(1000*1000);
    }
}
