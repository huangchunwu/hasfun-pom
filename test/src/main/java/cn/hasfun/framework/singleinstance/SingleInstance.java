package cn.hasfun.framework.singleinstance;

import lombok.Synchronized;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 饿汉式：
 * 如果这个类不被使用，浪费内存
 */
public class SingleInstance {

    private Byte[] bytes = new Byte[1024];

    private SingleInstance() {
    }

    private SingleInstance singleInstance = new SingleInstance();

    public SingleInstance getSingleInstance() {
        return singleInstance;
    }
}

/**
 * DCL
 */
class SingleInstance2 {


    // volatile 防止指令重排序
    private static volatile SingleInstance2 singleInstance;

    // private 防止new
    private SingleInstance2() {
    }

    public static SingleInstance2 getSingleInstance() {
        if (singleInstance == null) {
            synchronized (SingleInstance2.class) {//双重检锁
                if (singleInstance == null) {
                    singleInstance = new SingleInstance2();
                }
                return  singleInstance;
            }
        } else {
            return singleInstance;
        }
    }



}


class Test{
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SingleInstance2 singleInstance2 = SingleInstance2.getSingleInstance();
        SingleInstance2 singleInstance3 = SingleInstance2.getSingleInstance();
        Assert.isTrue(singleInstance2.equals(singleInstance3));//true

        Constructor constructor = SingleInstance2.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleInstance2 singleInstance4 = (SingleInstance2) constructor.newInstance();
        Assert.isTrue(singleInstance2.equals(singleInstance4));//false  ---结论：DCL不安全  私有构造函数会被反射给破坏单例子

    }
}


/**
 * DCL 升级版
 * 防止私有构造函数会被反射给破坏单例子
 */
class SingleInstance3 {


    // volatile 防止指令重排序
    private static volatile SingleInstance3 singleInstance;

    // private 防止new
    private SingleInstance3() throws Exception {
        if (singleInstance!=null){
            throw new Exception("不要通过反射来破坏单例");
        }
    }

    public static SingleInstance3 getSingleInstance() throws Exception {
        if (singleInstance == null) {
            synchronized (SingleInstance3.class) {//双重检锁
                if (singleInstance == null) {
                    singleInstance = new SingleInstance3();
                }
                return  singleInstance;
            }
        } else {
            return singleInstance;
        }
    }

}

class Test2{
    public static void main(String[] args) throws Exception {
        //SingleInstance3 singleInstance3 = SingleInstance3.getSingleInstance();

        Constructor constructor = SingleInstance3.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleInstance3 singleInstance4 = (SingleInstance3) constructor.newInstance();
        //System.out.println(singleInstance3.equals(singleInstance4));//抛异常。防止反射通过构造函数破坏

        Constructor constructor2 = SingleInstance3.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        SingleInstance3 singleInstance5 = (SingleInstance3) constructor2.newInstance();
        System.out.println(singleInstance5.equals(singleInstance4));//false

    }
}


/**
 * DCL 升级版
 * 防止私有构造函数会被反射给破坏单例子
 */
class SingleInstance4 {


    // volatile 防止指令重排序
    private static volatile SingleInstance4 singleInstance;

    private static int flag = 0;

    // private 防止new
    private SingleInstance4() throws Exception {
        if (flag !=0){
            throw new Exception("不要通过反射来破坏单例");
        }
        flag=1;
    }

    public static SingleInstance4 getSingleInstance() throws Exception {
        if (singleInstance == null) {
            synchronized (SingleInstance4.class) {//双重检锁
                if (singleInstance == null) {
                    singleInstance = new SingleInstance4();
                }
                return  singleInstance;
            }
        } else {
            return singleInstance;
        }
    }

}

class Test3{
    public static void main(String[] args) throws Exception {

        Constructor constructor = SingleInstance4.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleInstance4 singleInstance4 = (SingleInstance4) constructor.newInstance();

//        Constructor constructor2 = SingleInstance4.class.getDeclaredConstructor();
//        constructor2.setAccessible(true);
//        SingleInstance4 singleInstance5 = (SingleInstance4) constructor2.newInstance();
//        System.out.println(singleInstance5.equals(singleInstance4));//抛异常。防止反射通过构造函数破坏


        Field field = SingleInstance4.class.getDeclaredField("flag");
        field.setAccessible(true);
        field.setInt(0,0);

        Constructor constructor2 = SingleInstance4.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        SingleInstance4 singleInstance5 = (SingleInstance4) constructor2.newInstance();
        System.out.println(singleInstance5.equals(singleInstance4));//抛异常。防止反射通过构造函数破坏

    }
}


/**
 * 没啥用
 */
class SingleInstance5 {

    private SingleInstance5(){}

    public static SingleInstance5 getInstance(){
        return InnerClass.singleInstance5;
    }

     private static class InnerClass{
        private  static SingleInstance5 singleInstance5 = new SingleInstance5();

    }

}

class Test4{
    public static void main(String[] args) throws Exception {

        Constructor constructor = SingleInstance5.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleInstance5 singleInstance5 = (SingleInstance5) constructor.newInstance();

//        Constructor constructor2 = SingleInstance4.class.getDeclaredConstructor();
//        constructor2.setAccessible(true);
//        SingleInstance4 singleInstance5 = (SingleInstance4) constructor2.newInstance();
//        System.out.println(singleInstance5.equals(singleInstance4));//抛异常。防止反射通过构造函数破坏


        Field field = SingleInstance4.class.getDeclaredField("flag");
        field.setAccessible(true);
        field.setInt(0,0);

        Constructor constructor2 = SingleInstance5.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        SingleInstance5 singleInstance6 = (SingleInstance5) constructor2.newInstance();
        System.out.println(singleInstance5.equals(singleInstance6));//抛异常。防止反射通过构造函数破坏



    }
}