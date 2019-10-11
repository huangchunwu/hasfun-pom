package cn.hasfun.framework.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码
 */
public class RpcEncoder  extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object request, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(request)){
            byte[] bytes = SerializationUtil.serialize(request);
            byteBuf.writeLong(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
