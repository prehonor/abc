package com.abc.core.parser;

import java.util.List;

public interface ConfigFileParser {
//    ParserData parserFrom(Resource resource);//解析资源
////    List<BeanDefinitionHolder> convertToBeanDefinition(ParserData data);//转化为BeanDefinition
    void registryBeanDefinition();
    default void registryBeanDefinition(List<BeanDefinitionHolder> beanholders,BeanDefinitionRegistry registry)//注册beandefinition
    {
        for(BeanDefinitionHolder beanDefinitionHolder : beanholders){
            registry.registryBeanDefinition(beanDefinitionHolder.getBeanName(),beanDefinitionHolder.getBeanDefinition());
        }
    }
}
