package com.abc.core;

import com.abc.core.parser.support.RuntimeBeanReference;

public class BeanDefinitionValueResolver {
    private final BeanFactory beanFactory;

    private final String beanName;

    private final BeanDefinition beanDefinition;

    public BeanDefinitionValueResolver(
            BeanFactory beanFactory, String beanName, BeanDefinition beanDefinition) {

        this.beanFactory = beanFactory;
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }
    public Object resolveValueIfNecessary(Object argName, Object value) {
        // We must check each value to see whether it requires a runtime reference
        // to another bean to be resolved.
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            return resolveReference(argName, ref);
        }
        return null;
    }
    private Object resolveReference(Object argName, RuntimeBeanReference ref) {
        Object bean;
        String refName = ref.getBeanName();
        bean = this.beanFactory.getBean(refName);
        return bean;
    }
}
