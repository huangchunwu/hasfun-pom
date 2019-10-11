package cn.hasfun.framework.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 *  server's heartbeat , get out timeout client
 */
@Slf4j
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
           IdleStateEvent idleStateEvent =    (IdleStateEvent)evt;
           if(idleStateEvent.state() == IdleState.READER_IDLE){
                log.info("[client]-"+ctx.channel().remoteAddress()+" is time out ");
               ctx.channel().close();
           }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }
}
