package com.abc.core;

import com.abc.core.parser.BeanDefinitionRegistry;

public interface BeanDefinitionRegistryPostProcessor {

    /**
     * 解析注册beandefinition
     * */
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry);
}
