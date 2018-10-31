package com.abc.core.parser;

import com.abc.core.BeanDefinition;

public class BeanDefinitionHolder {
    public BeanDefinition beanDefinition;
    public String beanName;

    public BeanDefinitionHolder(String beanName, BeanDefinition beanDefinition){
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public String getBeanName() {
        return beanName;
    }
}
