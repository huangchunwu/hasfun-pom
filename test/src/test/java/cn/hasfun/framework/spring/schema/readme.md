Spring 中的 XML schema 自定义标签

1. 定义xsd 文件
eg: hcw.xsd

2. 编写NamespaceHandler
eg: hcwNamespaceHandler

3. 编写 BeanDefinitionParser
eg: hcwBeanDefinitionParser

4.注册 schema 和 handler
resources/META-INF/spring.handlers
resources/META-INF/spring.schemas

5:验证扩展
eg: hcw.xml
