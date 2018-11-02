package com.abc.core;

import com.abc.core.parser.support.PropertyValues;

public class BeanDefinition {

    public static final int AUTOWIRE_NO = AutowireCapableBeanFactory.AUTOWIRE_NO;

    private String className;
    private String id;
    private int autowireMode = AUTOWIRE_NO;
    private PropertyValues propertyValues;

    public BeanDefinition(String id, String className){
        this.id = id;
        this.className = className;
    }

    public PropertyValues getPropertyValues() {
        if (this.propertyValues == null) {
            this.propertyValues = new PropertyValues();
        }
        return this.propertyValues;
    }

    public String getClassName() {
        return className;
    }
    public boolean hasPropertyValues() {
        return (this.propertyValues != null && !this.propertyValues.isEmpty());
    }
}
