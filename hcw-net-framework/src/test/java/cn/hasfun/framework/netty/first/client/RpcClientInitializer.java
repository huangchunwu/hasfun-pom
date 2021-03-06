package cn.hasfun.framework.netty.first.client;

import cn.hasfun.framework.netty.protocol.RpcDecoder;
import cn.hasfun.framework.netty.protocol.RpcEncoder;
import cn.hasfun.framework.netty.protocol.RpcRequest;
import cn.hasfun.framework.netty.protocol.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new RpcEncoder(RpcRequest.class));
        cp.addLast(new RpcDecoder(RpcResponse.class));
        cp.addLast(new RpcClientHandler());
    }
}
