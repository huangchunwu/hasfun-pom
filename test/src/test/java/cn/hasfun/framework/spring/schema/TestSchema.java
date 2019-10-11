package cn.hasfun.framework.spring.schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSchema {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:hcw.xml");
        //ServiceBean serviceBean = context.getBean(ServiceBean.class);

    }

}
