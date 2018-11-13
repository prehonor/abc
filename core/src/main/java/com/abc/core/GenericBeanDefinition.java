package com.abc.core;

import com.abc.core.parser.support.PropertyValues;

public class GenericBeanDefinition implements BeanDefinition{

    public static final int AUTOWIRE_NO = AutowireCapableBeanFactory.AUTOWIRE_NO;

    private String className;
    private String id;
    private int autowireMode = AUTOWIRE_NO;
    private PropertyValues propertyValues;

    public GenericBeanDefinition(String id, String className){
        this.id = id;
        this.className = className;
    }

    @Override
    public PropertyValues getPropertyValues() {
        if (this.propertyValues == null) {
            this.propertyValues = new PropertyValues();
        }
        return this.propertyValues;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean hasPropertyValues() {
        return (this.propertyValues != null && !this.propertyValues.isEmpty());
    }

    @Override
    public String getFactoryMethodName() {
        return null;
    }

    @Override
    public String getFactoryBeanName() {
        return null;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        //nothing
    }
}
