package com.abc.core;

public interface BeanPostProcessor {
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
    default Object postProcessPropertyValues(Object bean, String beanName) {
        return bean;
    }
    void postProcessMergedBeanDefinition(Object bean, String beanName);
}
