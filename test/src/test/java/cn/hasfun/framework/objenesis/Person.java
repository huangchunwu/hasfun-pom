package cn.hasfun.framework.objenesis;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private Integer age;
    private String name;
}
