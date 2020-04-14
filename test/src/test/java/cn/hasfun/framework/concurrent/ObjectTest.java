package cn.hasfun.framework.concurrent;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * <dependency>
 *             <groupId>org.openjdk.jol</groupId>
 *             <artifactId>jol-core</artifactId>
 *             <version>0.9</version>
 *         </dependency>
 *
 * 对象占据多少内存
 * java.lang.Object object internals:
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header) Mark Word                          05 00 00 00 (00000101 00000000 00000000 00000000) (5)
 *       4     4        (object header) Mark Word                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header) Class pointer                          84 06 00 00 (10000100 00000110 00000000 00000000) (1668)
 *      12     4        (loss due to the next object alignment)
 * Instance size: 16 bytes
 *
 * 普通对象 =  对象头（8个字节） +  类型指针（开启压缩下4个字节） + padding
 * 数组对象 = 对象头 + 类型指针 + 数组length + padding
 *
 *
 * java -XX:+PrintCommandLineFlags -version
 * 执行Java指令时候，运行的命令行指令
 *
 * -XX：+UseCompressClassPointers
 *
 * -XX:G1ConcRefinementThreads=8
 * -XX:GCDrainStackTargetSize=64
 * -XX:InitialHeapSize=268435456 （256M）
 * -XX:MaxHeapSize=4294967296 （4G）
 * -XX:MinHeapSize=6815736 （6m）
 * -XX:+PrintCommandLineFlags
 * -XX:ReservedCodeCacheSize=251658240
 * -XX:+SegmentedCodeCache
 * -XX:+UseCompressedClassPointers 压缩类型指针class pointers
 * -XX:+UseCompressedOops  压缩普通对象指针origin object pointers
 * -XX:+UseG1GC
 */
public class ObjectTest {


    @Test
    public void testObjectMem(){
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }


}
