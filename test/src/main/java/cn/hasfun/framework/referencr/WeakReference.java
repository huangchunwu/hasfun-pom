package cn.hasfun.framework.referencr;

/**
 * 垃圾回收器看到了就回收
 */
public class WeakReference {

    public static void main(String[] args) {
        java.lang.ref.WeakReference<M> m = new java.lang.ref.WeakReference(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> mThreadLocal = new ThreadLocal<>();
        mThreadLocal.set(new M());
        mThreadLocal.remove();
    }
}
