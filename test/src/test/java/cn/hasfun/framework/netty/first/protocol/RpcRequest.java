package cn.hasfun.framework.netty.first.protocol;

import lombok.Data;

@Data
public class RpcRequest {

    /**
     * 协议号
     */
    private int protocolId;

    private String content;
}
