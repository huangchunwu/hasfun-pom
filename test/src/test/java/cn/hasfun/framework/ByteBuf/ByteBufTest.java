package cn.hasfun.framework.ByteBuf;

import cn.hasfun.framework.utils.ByteUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * 温习一下java数据类型基础知识: byte型占1个字节，int型占4个字节，在java中没有无符号的数据类型
 *
 * 左移的规则只记住一点：丢弃最高位，0补最低位
 * 如果移动的位数超过了该类型的最大位数，那么编译器会对移动的位数取模。如对int型移动33位，实际上只移动了33%32=1位。
 *
 * https://www.wuhaojie.top/2016/01/24/Javabyteint/
 *
 *
 */
public class ByteBufTest {


    @Test
    public void testFirst(){
        byte[] byte_  = "a".getBytes();

        int length = byte_.length;

        for (byte b : byte_){
            System.out.println(b);
        }

        System.out.println("==="+ByteUtils.byteArrayToInt(byte_));


    }




    @Test
    public void test2(){

       char[] a = {'a','b','c'};
       char[]  b = a;
       a[1] = 'd';
       System.out.println(b);

    }

    /**
     * 十进制转化为
     *  十六进制：Integer.toHexString(int i);
     *  八进制：Integer.toOctalString(int i);
     *  二进制：Integer.toBinaryString(int i);
     *
     *  其他进制转化为十进制：
     *  1 二进制：Integer.valueOf("0101",2).toString;
     * 2 八进制：Integer.valueOf("376",8).toString;
     * 3 十六进制：Integer.valueOf("FFFF",16).toString;
     *
     * 使用Integer类中的parseInt()方法和valueOf()方法都可以将其他进制转化为10进制。
     *
     * 不同的是parseInt()方法的返回值是int类型，而valueOf()返回值是Integer对象。
     */
    @Test
    public void test3(){


        Arrays.asList(1,2,3,4,5,6,7,8,9,0).forEach(t ->{
            System.out.print(t+"转为二进制:");
            System.out.println(Integer.toBinaryString(t));
        });

        int i =8143;
        System.out.println(Integer.toBinaryString(i));

        System.out.println(Integer.valueOf("11001111",2).toString());
        System.out.println(Integer.valueOf("00011111",2).toString());

    }
}
