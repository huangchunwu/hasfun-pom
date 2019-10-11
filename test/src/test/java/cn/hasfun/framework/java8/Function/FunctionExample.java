package cn.hasfun.framework.java8.Function;

import org.junit.Test;

/**
 * 函数式编程的好处
 */
public class FunctionExample {


    @Test
    public void test0(){
        FunctionExample functionExample  = new FunctionExample();
       System.out.println(functionExample.cal(10,v->v*v));
    }

    private int  cal(int v ,InnerInterface myInterface){
       return myInterface.cal(v);
    }

    @FunctionalInterface
    interface  InnerInterface {
        int cal(int y);
    }
}
