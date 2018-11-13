package com.abc.core;

import com.abc.core.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;
    Object resolveDependency(Object bean,Class beanType,String fieldName);
}
