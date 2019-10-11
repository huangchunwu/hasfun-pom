package cn.hasfun.framework.entity;

import cn.hasfun.framework.constant.ExecStatusCode;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExecResult<T> {

    private String code;

    private String msg;

    private T data;

    public static ExecResult getSuccessResult() {
        return ExecResult.builder().code(ExecStatusCode.SUCCESS).msg("success").build();
    }
}
