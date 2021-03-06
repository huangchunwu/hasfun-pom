package cn.hasfun.framework.netty.first.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

// * <pre>
// *                                                 I/O Request
//         *                                            via {@link Channel} or
//        *                                        {@link ChannelHandlerContext}
//        *                                                      |
//        *  +---------------------------------------------------+---------------+
//        *  |                           ChannelPipeline         |               |
//        *  |                                                  \|/              |
//        *  |    +---------------------+            +-----------+----------+    |
//        *  |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |              /|\                                  |               |
//        *  |               |                                  \|/              |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |              /|\                                  .               |
//        *  |               .                                   .               |
//        *  | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
//        *  |        [ method call]                       [method call]         |
//        *  |               .                                   .               |
//        *  |               .                                  \|/              |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |              /|\                                  |               |
//        *  |               |                                  \|/              |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
//        *  |    +----------+----------+            +-----------+----------+    |
//        *  |              /|\                                  |               |
//        *  +---------------+-----------------------------------+---------------+
//        *                  |                                  \|/
//        *  +---------------+-----------------------------------+---------------+
//        *  |               |                                   |               |
//        *  |       [ Socket.read() ]                    [ Socket.write() ]     |
//        *  |                                                                   |
//        *  |  Netty Internal I/O Threads (Transport Implementation)            |
//        *  +-------------------------------------------------------------------+
//        * </pre>
public class RpcServer {

    private Integer port;

    public RpcServer(Integer port) {
        this.port = port;
    }

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new RpcServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            System.out.println("server is start at port "+ port);

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new RpcServer(8899).start();
    }
}
