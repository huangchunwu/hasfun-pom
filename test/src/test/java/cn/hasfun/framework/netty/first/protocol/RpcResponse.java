package cn.hasfun.framework.netty.first.protocol;

import lombok.Data;

@Data
public class RpcResponse {
    /**
     * 协议号
     */
    private int protocolId;

    private int errorCode;

    private String message;

    private Object result;

}
