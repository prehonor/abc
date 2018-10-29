package com.abc.core.util;

import com.abc.core.BeanFactory;

public interface AutowiredCapableBeanFactory extends BeanFactory {
    Object resloveDependency(Object bean,Class beanType,String fieldName);
}
