package cn.hasfun.framework.MDC;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MethodExample {

    public void test(){
        log.info("invoke ......");
    }
}
