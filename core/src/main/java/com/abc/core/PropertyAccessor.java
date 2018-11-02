package com.abc.core;

import com.abc.core.parser.support.PropertyValue;
import com.abc.core.util.ReflectUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性访问器
 * */
public class PropertyAccessor {
    Object wrapedObject;   //属性对应的对象实例
    BeanInfo beanInfo;
    private Map<String, PropertyDescriptor> propertyDescriptorCache;  //缓存了属性名和属性描述

    PropertyAccessor(Object instance){
        this.wrapedObject = instance;
        this.propertyDescriptorCache = new LinkedHashMap<>();
    }

    /**
     * 通过反射设置属性值
     * */
    public void setProperty(String propertyName, Object value){

        //目前只考虑简单自定义对象的赋值,对于List,Set集合等属性暂不考虑
        //TODO
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyName);
        final Method writeMethod = propertyDescriptor.getWriteMethod();
        ReflectUtil.makeAccessible(writeMethod);
        try {
            writeMethod.invoke(wrapedObject, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object getWrappedInstance(){
        return wrapedObject;
    }


    /**
     * 根据属性名判断属性能不能被赋值
     * */
    public boolean isWritableProperty(String propertyName){
        //这里只考虑必须有set方法的属性
        //获取对象实例中的所有方法,找到属性对应的set方法
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyName);
        if(propertyDescriptor!=null && propertyDescriptor.getWriteMethod()!=null)
            return true;
        return false;
    }

    /**
     * 获取属性和属性对应的set方法体,没有就创建
     * */
    private PropertyDescriptor getPropertyDescriptor(String propertyName){
        PropertyDescriptor propertyDescriptor = propertyDescriptorCache.get(propertyName);
        if(propertyDescriptor==null){
            propertyDescriptor = doGetPropertyDescriptor(propertyName);
        }
        return propertyDescriptor;
    }

    /**
     * 实际的PropertyDescriptor的创建工作
     * */
    private synchronized PropertyDescriptor doGetPropertyDescriptor(String propertyName){
        PropertyDescriptor propertyDescriptor = propertyDescriptorCache.get(propertyName);
        if(propertyDescriptor==null) {
            Class beanClass = wrapedObject.getClass();
            try {
                beanInfo = Introspector.getBeanInfo(beanClass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd : pds){
                this.propertyDescriptorCache.put(pd.getName(), pd);
            }
            propertyDescriptor = propertyDescriptorCache.get(propertyName);
        }
        return propertyDescriptor;
    }
}
