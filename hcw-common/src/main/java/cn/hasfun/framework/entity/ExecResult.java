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

    public static <T>ExecResult getSuccessResult(T data) {
        return ExecResult.builder().data(data).code(ExecStatusCode.SUCCESS).msg("success").build();
    }
}
