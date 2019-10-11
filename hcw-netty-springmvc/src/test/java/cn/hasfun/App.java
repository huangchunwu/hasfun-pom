package cn.hasfun;

import cn.hasfun.framework.netty.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private static ApplicationContext context;

    /**
     *  启动的时候加启动参数
     * -Dconfig.properties=file:D:\workspace\hasfun-pom\hcw-netty-springmvc\src\main\conf\config.properties
     * @param args
     */
    public static void main(String[] args) throws Exception {
        context = new ClassPathXmlApplicationContext("classpath*:spring/spring-config.xml");

        HttpServer httpServer = context.getBean(HttpServer.class);
        httpServer.start();
    }
}
