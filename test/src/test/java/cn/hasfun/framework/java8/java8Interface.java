package cn.hasfun.framework.java8;

/**
 *  Java 8 中，一个接口中能定义如下几种变量/方法：
 *
 * 常量
 * 抽象方法
 * 默认方法
 * 静态方法
 *
 *
 * 在 Java 9 中，一个接口中能定义如下几种变量/方法：
 *
 * 常量
 * 抽象方法
 * 默认方法
 * 静态方法
 * 私有方法
 * 私有静态方法
 */
public interface java8Interface {
    short i =0;
    default int mult(int i,int j){
        return i*j;
    }
    static int add(int i, int j){
        return i+j;
    }
    private void print(){

    }
}
