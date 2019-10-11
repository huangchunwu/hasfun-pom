package cn.hasfun.framework.netty.client;

import cn.hasfun.framework.netty.protocol.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Slf4j
public class RpcClient implements ApplicationContextAware {

    private ConcurrentHashMap<String, Future<Object>> pendingRPC = new ConcurrentHashMap<>();


    private String serverIp;
    private Integer serverPort;

    private Channel channel;

    public void connect(String ip,Integer port){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new RpcClientInitializer());
            ChannelFuture future =  bootstrap.connect(ip,port).sync();
            channel = future.channel();
            //channel.closeFuture().sync();
            future.syncUninterruptibly();
        }catch (Exception e){
            log.error("client laucher fail ",e);
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void start(){
        this.connect(serverIp,serverPort);
    }

    /**
     *  client send packet to server
     */
    public void sendPacket(RpcRequest rpcRequest){
        channel.writeAndFlush(rpcRequest);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
}
