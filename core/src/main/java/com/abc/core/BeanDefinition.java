package com.abc.core;

import com.abc.core.parser.support.PropertyValues;

public interface BeanDefinition {
    public String getClassName();
    public PropertyValues getPropertyValues();
    public boolean hasPropertyValues();
    String getFactoryMethodName();
    String getFactoryBeanName();
    void setFactoryBeanName(String factoryBeanName);
}
