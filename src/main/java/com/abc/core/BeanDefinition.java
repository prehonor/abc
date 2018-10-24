package com.abc.core;

public class BeanDefinition {
    private String className;
    private String id;

    BeanDefinition(String id, String className){
        this.id = id;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
