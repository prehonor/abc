package com.abc.core;

public interface BeanFactory {
    public Object getBean(String name);
    public Object getBeanByType(Class clazz);
}
