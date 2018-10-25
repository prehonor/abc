package com.abc.core;

import com.abc.core.io.PropertiesResource;
import com.abc.core.util.Utils;

public class ClassPathPropertiesAppContext {

    private String[] path;
    private PropertiesBeanDefinationReader reader;
    private ListableBeanFactory factory =null;

    public ClassPathPropertiesAppContext(String... path){
        factory = createBeanFactory();
        reader = new PropertiesBeanDefinationReader(factory);
        this.path = path;
        onRefresh();
    }

    public void loadBeanDefination(){
        reader.loadBeanDefination(path);
    }

    public ListableBeanFactory createBeanFactory(){
        return new ListableBeanFactory();
    }

    public void onRefresh(){
        loadBeanDefination();
    }

    public Object getBean(String name){
        return factory.getBean(name);
    }
}
