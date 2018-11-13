package com.abc.core;

public interface AnnotatedBeanDefinition extends BeanDefinition{
    AnnotationMetadata getAnnotatioMetadata();
    MethodMetadata getFactoryMethodMetadata();
    Class getTargetClass();
}
