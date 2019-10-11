package cn.hasfun.framework.objenesis;

import org.junit.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 *  Objenesis is java library; create a object without
 *  constructor
 *
 *  构造函数要注意的细节：
 *
 * 当类中没有定义构造函数时，系统会指定给该类加上一个空参数的构造函数。这个是类中默认的构造函数。当类中如果自定义了构造函数，这时默认的构造函数就没有了。
 *
 * 备注：可以通过javap命令验证。
 */
public class ObjenesisTest {


    /**
     * 没有默认构造函数
     * 直接通过反射生成对象 失败
     *
     * @throws Exception
     */
    @Test
    public void testReflectCreateObject() throws Exception{
        Person person = (Person) Class.forName("cn.hasfun.framework.objenesis.Person").newInstance();
        System.out.println(person.toString());
    }


    @Test
    public void testCreateObject(){
        Objenesis objenesis = new ObjenesisStd(true);
        Person person  = objenesis.newInstance(Person.class);
        System.out.println(person.toString());
    }
}
