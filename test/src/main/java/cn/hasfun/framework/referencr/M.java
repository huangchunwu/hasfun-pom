package cn.hasfun.framework.referencr;

public class M {


    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
