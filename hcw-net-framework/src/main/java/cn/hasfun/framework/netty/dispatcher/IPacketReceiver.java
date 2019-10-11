package cn.hasfun.framework.netty.dispatcher;

import cn.hasfun.framework.netty.packet.IPacket;

public interface IPacketReceiver {

    void invoke(Session session, IPacket packet);

}
