package cn.hasfun.framework.MDC;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * MDC 用于记录系统调用链路日志
 * 一般的做法：
 * 声明AOP 拦截Controller 通过Http header 头 传入调用的traceId
 */
@Component
@Slf4j
@Aspect
public class RequestIdAspectJHandler {

    private static final String REQUEST_ID = "requestId";

    @Pointcut("execution(public  * cn.hasfun.framework.MDC.MethodExample.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  //      MDC.put(REQUEST_ID,request.getHeader(REQUEST_ID));//将requestId设置到MDC中
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());//将requestId设置到MDC中

        return joinPoint.proceed();
    }
}
