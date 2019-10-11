package cn.hasfun.framework.netty.first.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcClient {

    public void connect(String ip,Integer port){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new RpcClientInitializer());
            ChannelFuture future =  bootstrap.connect(ip,port).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("client laucher fail ",e);
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new RpcClient().connect("127.0.0.1",8899);
    }
}
