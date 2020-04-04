package cn.hasfun.framework.springboot.constant;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class ExecResult implements Serializable {

    private int code;
    private String message;
    private Object data;


    public ExecResult(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
