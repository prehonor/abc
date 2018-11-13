package com.abc.core.parser;

import com.abc.core.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
    boolean containsBeanDefinition(String beanName);
    String[] getBeanDefinitionNames();

    BeanDefinition getBeanDefinition(String beanName);
}
