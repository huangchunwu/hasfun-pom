package cn.hasfun.framework.jvm.classloader;


import org.apache.commons.exec.ExecuteWatchdog;

/**
 * classloader 双亲委托
 */
public class ClassLoaderExample {



    static class MyClassLoader extends ClassLoader{
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name);
        }
    }


    public static void main(String[] args) {
        MyClassLoader classLoader = new MyClassLoader();
        try {
            Class  clazz = classLoader.getParent().loadClass("cn.hasfun.framework.jvm.classloader.Object");
            System.out.println(clazz);

            Class  clazz2 = classLoader.loadClass("java.lang.Object");
            System.out.println(clazz2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
