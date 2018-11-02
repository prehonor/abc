package com.abc.core.parser.support;

public class RuntimeBeanReference implements BeanReference{
    private final String beanName;
    public RuntimeBeanReference(String beanName){
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }
}
