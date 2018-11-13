package com.abc.core;

import com.abc.core.parser.support.PropertyValues;

public class DefaultAnnotatedBeanDefinition implements AnnotatedBeanDefinition {

    private Class targetClazz;
    private AnnotationMetadata annotationMetadata;
    private MethodMetadata methodMetadata;
    private String factoryBeanName;
    public DefaultAnnotatedBeanDefinition(Class clazz) {
        targetClazz = clazz;
        annotationMetadata = new AnnotationMetadata(clazz);
    }

    public DefaultAnnotatedBeanDefinition(Class clazz,MethodMetadata methodMetadata) {
        targetClazz = clazz;
        this.methodMetadata = methodMetadata;
    }

//    public Class getTargetClass(ClassLoader loader){
//        if(tempClazz==null) {
//            try {
//                return loader.loadClass(getClassName());
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return tempClazz;
//    }

    @Override
    public String getClassName() {
        return targetClazz.getName();
    }

    @Override
    public PropertyValues getPropertyValues() {
        return null;
    }

    @Override
    public boolean hasPropertyValues() {
        return false;
    }

    @Override
    public String getFactoryMethodName() {
        return methodMetadata!=null ? methodMetadata.getFactoryMethodName() : null;
    }

    @Override
    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    @Override
    public AnnotationMetadata getAnnotatioMetadata() {
        return annotationMetadata;
    }

    @Override
    public MethodMetadata getFactoryMethodMetadata() {
        return methodMetadata;
    }

    @Override
    public Class getTargetClass() {
        return targetClazz;
    }
}
