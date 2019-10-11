package cn.hasfun.framework.Protostuff;

import cn.hasfun.framework.javassit.Dog;
import cn.hasfun.framework.netty.protocol.SerializationUtil;
import org.junit.Test;

import java.util.Arrays;

/*
 * A java serialization library
 *
 * https://github.com/protostuff/protostuff
 */
public class ProtostuffTest {

    /**
     *  serilize is cost time 170ms
     */
    @Test
    public void testSerilization() {
        Dog dog = Dog.builder().age(10).name("小白").build();
        long begin = System.currentTimeMillis();
        byte[] bytes = SerializationUtil.serialize(dog);
        long end = System.currentTimeMillis();
        String result = Arrays.toString(bytes);
        System.out.println("serilize result is " + result+",cost time is "+(end-begin) +"ms");
    }

    /**
     * deserialize is cost  7ms
     */
    @Test
    public void testDeserialize() {
        Dog dog = Dog.builder().age(10).name("小白").build();
        byte[] bytes = SerializationUtil.serialize(dog);
        long begin = System.currentTimeMillis();
        Dog d = SerializationUtil.deserialize(bytes, Dog.class);
        long end = System.currentTimeMillis();
        System.out.println("deserialize result is " + d.toString() +",cost time is "+(end-begin) +"ms");

    }
}
