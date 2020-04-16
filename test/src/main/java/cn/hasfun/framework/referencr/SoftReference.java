package cn.hasfun.framework.referencr;

/**
 * 适合做缓存，超过内存大小，则回收
 */
public class SoftReference {
    public static void main(String[] args) {
        java.lang.ref.SoftReference<byte[]> m = new java.lang.ref.SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        byte[] b = new byte[1024*1024*15];
        System.out.println(m.get());
    }

}
