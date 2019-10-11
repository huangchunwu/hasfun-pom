package cn.hasfun.framework.netty.client;

import cn.hasfun.framework.netty.protocol.RpcRequest;
import cn.hasfun.framework.netty.protocol.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
       log.info("[Server] -" + channelHandlerContext.channel().remoteAddress()+" ,reply:"+response.toString());


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel active "+  ctx.channel().remoteAddress());
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setProtocolId(100);
        rpcRequest.setContent("heart beat");
        ctx.writeAndFlush(rpcRequest);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("registered channel" + ctx.channel().remoteAddress());
        channel  = ctx.channel();
    }
}
