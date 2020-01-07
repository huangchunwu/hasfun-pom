package cn.hasfun.framework.sigar;

/**
 * sigar的依赖放置位置是根据 Sysytem.getProperty("java.library.path")获取的路径来确定的
 *
 * -Djava.library.path="C:\\Users\\user\\Downloads\\hyperic-sigar-1.6.4\\sigar-bin\lib"
 */
public class Test {

    @org.junit.Test
    public void testSysInfo(){
        System.out.println(System.getProperty("java.library.path"));
    }
}
