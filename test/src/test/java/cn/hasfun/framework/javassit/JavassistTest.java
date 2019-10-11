package cn.hasfun.framework.javassit;

import javassist.*;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class JavassistTest {

    int LOOPTIMES = 100000;



    /**
     * 10W次调用，6ms
     */
    @Test
    public void testCommon() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            Dog dog = new Dog();
            dog.setAge(10);
        }
        long end = System.currentTimeMillis();
        System.out.println("普通的new对象，set值，耗时：" + (end - start) + "ms");
    }


    /**
     * 10W次调用，135ms
     * 是new 性能的20倍
     * 所以讲class缓存起来，避免重复加载class
     *
     * 通过反射的 class.forName()，耗时：1ms
     * 通过反射的newInstance对象， class.newInstance()，耗时：21ms
     * @throws Exception
     */
    @Test
    public void testReflect() throws Exception {

        long start = System.currentTimeMillis();
        Class dogClazz = Class.forName("cn.hasfun.framework.javassit.Dog");
        long loadClassTime = System.currentTimeMillis();
        System.out.println("通过反射的 class.forName()，耗时：" + (loadClassTime - start) + "ms");
        for (int i = 0; i < LOOPTIMES; i++) {
            //Class dogClazz = Class.forName("cn.hasfun.javassit.Dog");
            Dog dog = (Dog) dogClazz.newInstance();
            dog.setAge(10);
        }
        long end = System.currentTimeMillis();
        System.out.println("通过反射的newInstance对象， class.newInstance()，耗时：" + (end - loadClassTime) + "ms");
    }


    /**
     * 通过ctClass.toClass(),，耗时：342ms  Javaassit使用的时候注意，要提前缓存class
     * 通过javassit的生成字节码，ctClass.toClass().生成对象，set值，耗时：17ms
     * @throws Exception
     */

    @Test
    public void testJavassit() throws Exception {
        long start = System.currentTimeMillis();

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("cn.hasfun.javassit.Cat");
        ctClass.addInterface(classPool.get(IAnimal.class.getCanonicalName()));

        CtField ctField_name = new CtField(classPool.get("java.lang.String"),"name",ctClass);
        ctField_name.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField_name);
        CtField ctField_age = new CtField(CtClass.intType,"age",ctClass);
        ctField_age.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField_age);

        CtMethod  ctMethod_age = CtMethod.make("public void setAge(int age){this.age = age;}",ctClass);
        ctClass.addMethod(ctMethod_age);
        CtMethod ctMethod_name = CtMethod.make("public void setName(String name){this.name = name;}",ctClass);
        ctClass.addMethod(ctMethod_name);

        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        constructor.setBody("{}");
        ctClass.addConstructor(constructor);
        Class<IAnimal>  aClass = (Class<IAnimal>) ctClass.toClass();

        long loadClassTime = System.currentTimeMillis();

        System.out.println("通过ctClass.toClass(),，耗时：" + (loadClassTime - start) + "ms");


        for (int i = 0; i < LOOPTIMES; i++) {
            IAnimal o  = aClass.newInstance();
            o.setAge(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("通过javassit的生成字节码，ctClass.toClass().生成对象，set值，耗时：" + (end - loadClassTime) + "ms");
    }


    /**
     * 236ms
     * @throws Exception
     */
    @Test
    public void testJavassit2() throws Exception {
        long start = System.currentTimeMillis();

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("cn.hasfun.javassit.Cat");

        CtField ctField_name = new CtField(classPool.get("java.lang.String"),"name",ctClass);
        ctField_name.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField_name);
        CtField ctField_age = new CtField(CtClass.intType,"age",ctClass);
        ctField_age.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField_age);

        CtMethod  ctMethod_age = CtMethod.make("public void setAge(int age){this.age = age;}",ctClass);
        ctClass.addMethod(ctMethod_age);
        CtMethod ctMethod_name = CtMethod.make("public void setName(String name){this.name = name;}",ctClass);
        ctClass.addMethod(ctMethod_name);

        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        constructor.setBody("{}");
        ctClass.addConstructor(constructor);
        Class  aClass = ctClass.toClass();
        Constructor constructor1 =  aClass.getConstructor();
        for (int i = 0; i < LOOPTIMES; i++) {

            Object o  = constructor1.newInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("通过javassit的生成字节码，ctClass.toClass().生成对象，set值，耗时：" + (end - start) + "ms");
    }


    /**
     * 反射的方法调用 29ms,比普通方法调用慢4倍
     * @throws Exception
     */
    @Test
    public void testReflectInvoke() throws Exception {

        long start = System.currentTimeMillis();
        Class dogClazz = Class.forName("cn.hasfun.framework.javassit.Dog");
        Method method = dogClazz.getMethod("setAge", int.class);
        long loadClassTime = System.currentTimeMillis();
        System.out.println("通过反射的 class.forName()，耗时：" + (loadClassTime - start) + "ms");
        for (int i = 0; i < LOOPTIMES; i++) {
            method.invoke(new Dog(),1);
        }
        long end = System.currentTimeMillis();
        System.out.println("通过反射的set，耗时：" + (end - loadClassTime) + "ms");
    }
}
