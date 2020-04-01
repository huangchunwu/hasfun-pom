package cn.hasfun.framework.jvm.classloader;


/**
 * classloader 双亲委托
 * -XX:+TraceClassLoading (已废弃)
 * -verbose:class
 * 可以看程序启动的时候，哪些class被加载来
 */
public class ClassLoaderExample {

    public static String s = "";

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

class Test{
    //ClassLoaderExample classLoaderExample; //不会加载
    static String s= ClassLoaderExample.s;//不会加载

    public static void main(String[] args) {
        //ClassLoaderExample classLoaderExample= new ClassLoaderExample();//会加载
        //String s= ClassLoaderExample.s;
    }
}
