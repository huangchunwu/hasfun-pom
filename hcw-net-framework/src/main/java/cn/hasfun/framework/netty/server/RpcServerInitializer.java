package cn.hasfun.framework.netty.server;

import cn.hasfun.framework.netty.protocol.RpcDecoder;
import cn.hasfun.framework.netty.protocol.RpcEncoder;
import cn.hasfun.framework.netty.protocol.RpcRequest;
import cn.hasfun.framework.netty.protocol.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RpcServerInitializer extends
        ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
       // channelPipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        channelPipeline.addLast("framer", new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        channelPipeline.addLast("decoder", new RpcDecoder(RpcRequest.class));
        channelPipeline.addLast("encoder", new RpcEncoder(RpcResponse.class));
//        channelPipeline.addLast("decoder", new StringDecoder());
//        channelPipeline.addLast("encoder", new StringEncoder());
        channelPipeline.addLast("idleState",new IdleStateHandler(60,0,0, TimeUnit.SECONDS));
        channelPipeline.addLast("idleStateTrigger",new HeartBeatServerHandler());
        channelPipeline.addLast("handler", new RpcServerHandle());
    }
}
