package com.abc.core;

import com.abc.core.util.ReflectUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);


    public void registryBeanDefinition(String key,BeanDefinition beanDefinition){
        if(beanDefinitionMap.get(key)==null){
            beanDefinitionMap.put(key,beanDefinition);
        }
    }

    public Object getBean(String name){
        Object result = null;
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition!=null){
            String beanName = beanDefinition.getClassName();
            result = ReflectUtil.getInstance(beanName);
        }
        return result;
    }
}
