package com.abc.core;

public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanNamesByType(Class clazz);
}
