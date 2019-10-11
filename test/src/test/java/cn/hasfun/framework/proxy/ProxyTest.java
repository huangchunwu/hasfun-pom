package cn.hasfun.framework.proxy;

import cn.hasfun.framework.netty.second.server.UserQuery;
import cn.hasfun.framework.netty.second.server.UserRpcService;
import cn.hasfun.framework.netty.second.server.UserRpcServiceImpl;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实现动态代理的方案有下列几种：
 * <p>
 * jdk 动态代理
 * cglib 动态代理
 * javassist 动态代理
 * ASM 字节码
 * javassist 字节码
 * <p>
 * 动态代理其实分成了两步：代理对象的创建，代理对象的调用
 * <p>
 * <p>
 * 动态代理发生在服务调用方 / 客户端，
 * RPC 框架需要解决的一个问题是：像调用本地接口一样调用远程的接口。
 * 于是如何组装数据报文，经过网络传输发送至服务提供方，屏蔽远程接口调用的细节，便
 * 是动态代理需要做的工作了。RPC 框架中的代理层往往是单独的一层，
 * 以方便替换代理方式（如 motan 代理层位于 com.weibo.api.motan.proxy ，
 * dubbo 代理层位于 com.alibaba.dubbo.common.bytecode ）。
 * <p>
 * JDK动态代理 代理接口
 * 代理对象的所有接口方法调用都会转发到InvocationHandler.invoke()方法，
 * 在invoke()方法里我们可以加入任何逻辑，
 * 比如修改方法参数，加入日志功能、安全检查功能等；
 * 之后我们通过某种方式执行真正的方法体，
 * 示例中通过反射调用了Hello对象的相应方法，还可以通过RPC调用远程方法
 * 底层是Constructor.newinstance
 * 顺带一提，很多人认为 jdk 动态代理的原理是反射，其实它的底层也是使用的字节码技术
 * <p>
 * cglib 动态代理 是JDK代理的互补，可以代理没有实现接口的类
 * 基于ASM的字节码生成库
 * https://www.cnkirito.moe/rpc-dynamic-proxy/
 * https://www.iteye.com/blog/javatar-814426
 * <p>
 * <p>
 * 结论 jdk11 环境下
 * <p>
 * Create Cglib  反射 create time is 84ms
 * Create common create time is 1ms
 * Create Javassist Bytecode Proxy:  98ms
 * Create Cglib Proxy  create time is 22ms
 * Create Javassit Proxy create time is 11ms
 * Create JDKProxy  create time is 1ms
 * <p>
 * <p>
 * common invoke cost time is 563ms
 * <p>
 * Cglib  反射 invoke cost 5296ms
 * jdk  反射 invoke cost 4607ms
 * <p>
 * JDKProxy invoke cost 9270ms
 * JavassistBytecode Proxy invoke cost  5271ms
 * Javassit Proxy invoke cost 3135ms
 * Cglib Proxy   invoke cost 9614ms
 * <p>
 * Javassit Proxy最好> JavassistBytecode Proxy> (Cglib Proxy ,JDKProxy )
 * <p>
 * 总结
 * 创建代理对象，Javassit提供者动态代理接口最慢，其次是Cglib 代理对象的Enhancer创建，JDKProxy胜出
 * 代理对象的调用 直接调用与动态调用 性能相差没有数量级，可忽略。
 * Javassit ProxyFactory 性能最好是JDKProxy的4倍， Javassist Bytecode 其次与Cglib Proxy FastClass相当 ，是JDKProxy的2倍。
 */
public class ProxyTest {

    private long LOOPTIMES = 10000000;

    /**
     * 普通方法
     * 创建耗时 6ms
     * 调用耗时37ms
     */
    @Test
    public void testCommon() {
        long beginTime = System.currentTimeMillis();
        UserRpcService userRpcService = new UserRpcServiceImpl();
        System.out.println("Create common create time is " + (System.currentTimeMillis() - beginTime) + "ms");

        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            userRpcService.queryUserInfo(UserQuery.builder().build());
        }
        System.out.println("common invoke cost time is " + (System.currentTimeMillis() - middleTime) + "ms");
    }

    /**
     * JDK 动态代理性能测试
     * <p>
     * 创建耗时 71ms
     * 调用耗时48ms
     */
    @Test
    public void testProxy() {
        long beginTime = System.currentTimeMillis();
        UserRpcService userRpcService = (UserRpcService) Proxy.newProxyInstance(UserRpcService.class.getClassLoader(), new Class[]{UserRpcService.class}, new RpcInvocationHandler());
        System.out.println("Create JDKProxy  create time is " + (System.currentTimeMillis() - beginTime) + "ms");
        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            userRpcService.queryUserInfo(UserQuery.builder().build());
        }
        System.out.println("JDKProxy invoke cost " + (System.currentTimeMillis() - middleTime) + "ms");
    }


    /**
     * cglib 动态代理性能
     * 创建耗时 685ms
     * 调用耗时33ms
     *
     * @throws InvocationTargetException
     */
    @Test
    public void testCglibProxy() {
        long beginTime = System.currentTimeMillis();
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibInterceptor());
        enhancer.setInterfaces(new Class[]{UserRpcService.class});
        UserRpcService cglibProxy = (UserRpcService) enhancer.create();
        System.out.println("Create Cglib Proxy  create time is " + (System.currentTimeMillis() - beginTime) + "ms");
        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            cglibProxy.queryUserInfo(UserQuery.builder().build());
        }
        System.out.println("Cglib Proxy   invoke cost " + (System.currentTimeMillis() - middleTime) + "ms");
    }


    @Test
    public void testJavassit() throws Exception {
        long beginTime = System.currentTimeMillis();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{UserRpcService.class});
        Class proxyClass = proxyFactory.createClass();
        UserRpcService javassistProxy = (UserRpcService) proxyClass.getDeclaredConstructor().newInstance();
        ((ProxyObject) javassistProxy).setHandler(new JavaAssitInterceptor());
        System.out.println("Create Javassit Proxy create time is " + (System.currentTimeMillis() - beginTime) + "ms");
        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            javassistProxy.queryUserInfo(UserQuery.builder().build());
        }
        System.out.println("Javassit Proxy invoke cost " + (System.currentTimeMillis() - middleTime) + "ms");
    }


    @Test
    public void testJavassit2() throws Exception {
        long beginTime = System.currentTimeMillis();

        ClassPool classPool = new ClassPool(true);
        CtClass ctClass = classPool.makeClass("cn.hasfun.proxy.UserRpcServiceImpl");
        ctClass.addInterface(classPool.get(UserRpcService.class.getName()));
        CtMethod queryUserInfo = CtNewMethod.make("public void queryUserInfo(cn.hasfun.framework.netty.second.server.UserQuery userQuery){}", ctClass);
        ctClass.addMethod(queryUserInfo);
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
        Class<UserRpcService> aClass = (Class<UserRpcService>) ctClass.toClass();
        UserRpcService javassistProxy = aClass.getDeclaredConstructor().newInstance();
        System.out.println("Create Javassist Bytecode Proxy:  " + (System.currentTimeMillis() - beginTime) + "ms");
        long middleTime = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            javassistProxy.queryUserInfo(UserQuery.builder().build());
        }
        System.out.println("JavassistBytecode Proxy invoke cost  " + (System.currentTimeMillis() - middleTime) + "ms");
    }

    private static class JavaAssitInterceptor implements MethodHandler {
        public Object invoke(Object self, Method m, Method proceed,
                             Object[] args) throws Throwable {
            return null;
        }
    }


    private static class CglibInterceptor implements MethodInterceptor {
        CglibInterceptor() {
        }

        @Override
        public Object intercept(Object object, Method method, Object[] objects,
                                MethodProxy methodProxy) throws Throwable {
            // 添加代理逻辑
            return null;
//            return methodProxy.invoke(delegate, objects);
        }
    }


    /**
     * Cglib 反射
     *
     * @throws InvocationTargetException
     */
    @Test
    public void testCglibInvoke() throws InvocationTargetException {
        long beginTime = System.currentTimeMillis();
        FastClass serviceFastClass = FastClass.create(UserRpcService.class);

        System.out.println("Create Cglib  FastClass create time is " + (System.currentTimeMillis() - beginTime) + "ms");
        long middleTime = System.currentTimeMillis();

        String methodName = "queryUserInfo";
        Class<?>[] parameterTypes = new Class[]{UserQuery.class};
        Object[] parameters = new Object[]{UserQuery.builder().build()};
        int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
        for (int i = 0; i < LOOPTIMES; i++) {
            serviceFastClass.invoke(methodIndex, new DelegateClass(), parameters);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Cglib  反射 invoke cost " + (endTime - middleTime) + "ms");
    }

    /**
     * ReflectAsm反射调用方法
     * 用方法和字段的索引定位反射方法，性能高
     */
    @Test
    public void testReflectAsm4Index() {
        UserRpcService target = new UserRpcServiceImpl();

        MethodAccess access = MethodAccess.get(UserRpcService.class);
        int index = access.getIndex("queryUserInfo", UserQuery.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            access.invoke(target, index, UserQuery.builder().build());
        }
        System.out.println("ReflectAsm4  反射 invoke cost " + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void jdkReflect() throws Exception {
        UserRpcService target = new UserRpcServiceImpl();
        Class<?> serviceClass = UserRpcServiceImpl.class;
        Method method = serviceClass.getMethod(
                "queryUserInfo", new Class[]{UserQuery.class});
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOPTIMES; i++) {
            method.invoke(target, new Object[]{UserQuery.builder().build()});
        }
        System.out.println("jdk  反射 invoke cost " + ( System.currentTimeMillis() - start) + "ms");
    }
}
