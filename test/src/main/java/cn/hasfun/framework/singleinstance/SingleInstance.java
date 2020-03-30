package cn.hasfun.framework.singleinstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


//DCL 双重检锁
public class SingleInstance {

    //避免反射，被破解无用
    private SingleInstance(){

    }

    private  static volatile SingleInstance  singleInstance;//防止指令重排序

    public static SingleInstance  getInstance(){
        if (singleInstance ==null){
            synchronized (SingleInstance.class){
                if (singleInstance==null){
                    singleInstance = new SingleInstance();
                }
            }
        }
        return singleInstance;
    }

}

class Demo {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        SingleInstance  s1  = SingleInstance.getInstance();
        SingleInstance  s2  = SingleInstance.getInstance();
        System.out.println(s1==s2);

        // 获得public的所有构造器Constructor[] getConstructors();
        Constructor[] constructors = SingleInstance.class.getConstructors();
        Arrays.stream(constructors).forEach(V->System.out.println(V));

// public, protected, default
//     * (package) access, and private constructors
        Constructor[] constructors2   = SingleInstance.class.getDeclaredConstructors();
        Arrays.stream(constructors2).forEach(V->System.out.println(V));
        constructors2[0].setAccessible(true);
        SingleInstance s3 = (SingleInstance) constructors2[0].newInstance();
        SingleInstance s4 = (SingleInstance) constructors2[0].newInstance();
        System.out.println(s3==s4);
    }
}
