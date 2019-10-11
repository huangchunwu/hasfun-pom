package cn.hasfun.framework.logback;


import lombok.extern.slf4j.Slf4j;

/**
 * 日志门面有 slf4j
 *
 * logback,log4j 只是实现
 *
 * 其中logback 可以设置字体颜色
 *  其他的特性参见：https://logback.qos.ch/reasonsToSwitch.html
 *
 */
@Slf4j
public class logbackExample {


    public static void main(String[] args) {
        log.info("{} {}","logback","benefit your program");
    }
}
