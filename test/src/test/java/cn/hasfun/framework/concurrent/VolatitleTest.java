package cn.hasfun.framework.concurrent;

/**
 * volatile保证了
 * 1：线程可见行
 * 2：指令重排序
 *
 * 本例子验证 线程可见性
 *
 */
public class VolatitleTest {

    private /** volatile **/ boolean running = true;


    void m(){
        System.out.println("m  start");
        while (running){

        }
        System.out.println("m  end");
    }

    public static void main(String[] args) {
        VolatitleTest test = new VolatitleTest();
        new Thread(test::m,"t1").start();
        try {
            Thread.currentThread().sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test.running = false;
    }

}
