package cn.hasfun.framework.referencr;

public class WeakReference {

    public static void main(String[] args) {
        java.lang.ref.WeakReference<M> m = new java.lang.ref.WeakReference(new M());
        System.gc();
        System.out.println(m.get());
    }
}
