package cn.hasfun.framework.netty;

import cn.hasfun.framework.netty.channel.DispatcherServletChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class HttpServer implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private int port;

    private ApplicationContext applicationContext;

    private DispatcherServletChannelInitializer channelInitializer;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 启动服务
     * @throws InterruptedException
     */
    public void start() throws Exception {
        long start = System.currentTimeMillis();
        // Configure the server.
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)//backlog就是socket的监听队列，当一个请求（request）尚未被处理或建立时，他会进入backlog。而socket server可以一次性处理backlog中的所有请求，处理后的请求不再位于监听队列中。当server处理请求较慢，以至于监听队列被填满后，新来的请求会被拒绝
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(this.channelInitializer);
            Channel ch = b.bind(port).sync().channel();
            if (logger.isInfoEnabled()) {
                logger.info("http server start at port:" + port);
            } else {
                System.out.println("http server start at port:" + port);
            }
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.info("http server shut down");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        try {
            channelInitializer = new DispatcherServletChannelInitializer(applicationContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
