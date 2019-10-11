package cn.hasfun.framework.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class HcwNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        super.registerBeanDefinitionParser("application", new HcwBeanDefinitionParser(null));
        super.registerBeanDefinitionParser("service", new HcwBeanDefinitionParser(null));
    }
}
