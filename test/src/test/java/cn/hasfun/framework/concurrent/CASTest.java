package cn.hasfun.framework.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 这方法，会有ABA问题。解决方案，可以加个版本号，对于jdk的方法是AtomicMarkableReference。
 *
 * 最终实现：对应hotpot jvm里面有
 *
 * lock cmpxchg 指令 （compare exchange）
 */
public class CASTest {


    @Test
    public void testCas(){

        AtomicInteger i = new AtomicInteger();
        i.getAndAdd(1);

    }



}
