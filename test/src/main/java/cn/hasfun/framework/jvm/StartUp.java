package cn.hasfun.framework.jvm;

public class StartUp {

    public static void main(String[] args) {
        try {
            Thread.currentThread().sleep(1000*1*60*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
