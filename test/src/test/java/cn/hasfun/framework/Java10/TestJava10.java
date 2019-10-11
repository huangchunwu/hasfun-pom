package cn.hasfun.framework.Java10;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * var java10
 */
public class TestJava10 {


    @Test
    public void test1(){
        var words = List.of("a", "b", "c");
        var tempMap = Map.of("a",12,"b",14);
        var tempSet = Set.of("1","2","4");
    }
}
