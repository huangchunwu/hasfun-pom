package cn.hasfun.framework.netty.server;

import cn.hasfun.framework.netty.dispatcher.IPacketReceiver;
import cn.hasfun.framework.netty.dispatcher.PacketReceiverDefinition;
import cn.hasfun.framework.netty.dispatcher.Session;
import cn.hasfun.framework.netty.packet.IPacket;
import cn.hasfun.framework.netty.protocol.RpcRequest;
import cn.hasfun.framework.netty.protocol.RpcResponse;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.reflect.FastClass;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RpcServerHandle extends SimpleChannelInboundHandler<RpcRequest> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channels.writeAndFlush("[client]-" + channel.remoteAddress() + "  is add \n");
        channels.add(channelHandlerContext.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channel.writeAndFlush("[client]-" + channel.remoteAddress() + "  is remove");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("[client]-" + incoming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("[client]-" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        log.error(throwable.getMessage());
        channelHandlerContext.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        log.info("Receive request :" + request.getProtocolId());
        this.submit(new Runnable() {
            @Override
            public void run() {
                log.debug("Receive request " + request.getProtocolId());
                RpcResponse response = new RpcResponse();
                response.setProtocolId(request.getProtocolId());
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setMessage(t.toString());
                    log.error("RPC Server handle request error", t);
                }
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        log.debug("Send response for request " + request.getProtocolId());
                    }
                });
            }
        });
    }


    private Object handle(RpcRequest request) throws Throwable {

        //解码报文
        // IPacket

        // 根据protocolId 分发器 路由不同的处理方法

        IPacketReceiver  packetReceiverDefinition =  new PacketReceiverDefinition();
        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(packetReceiverDefinition.getClass());
        // for higher-performance
        int methodIndex = serviceFastClass.getIndex("invoke", new Class[]{Session.class, IPacket.class});
        return serviceFastClass.invoke(methodIndex, packetReceiverDefinition, new Object[]{});
    }

    public void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (RpcServer.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }
}
