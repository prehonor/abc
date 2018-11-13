package com.abc.core;

import com.abc.core.parser.support.PropertyValue;
import com.abc.core.parser.support.RuntimeBeanReference;
import com.abc.core.parser.support.TypedStringValue;
import com.abc.core.propertyeditors.CustomerPropertyEditor;
import com.abc.core.propertyeditors.DefaultCustomerPropertyEditorFactory;
import com.abc.core.util.ReflectUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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

    public void setProperties(List<PropertyValue> properties){
        for(PropertyValue propertyValue : properties){
            setProperty(propertyValue.getName(),propertyValue.getConvertedValue());
        }
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
        if(propertyDescriptor!=null
                && propertyDescriptor.getWriteMethod()!=null)
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

    public Object converForPropertyIfNecessary(PropertyValue pv) {

        String propertyName = pv.getName();
        Object value = pv.getValue();
        if(value instanceof RuntimeBeanReference) return value;
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyName);
        Class type = propertyDescriptor.getWriteMethod().getParameterTypes()[0];//set方法 约定只有一个参数
        //根据type获取对应的类型转化器
        if(TypedStringValue.class.isAssignableFrom(value.getClass()) ){
            TypedStringValue innervalue = (TypedStringValue) value;
            if(String.class.isAssignableFrom(type)){
                value = innervalue.getValue();
            }else{
                CustomerPropertyEditor propertyEditor = DefaultCustomerPropertyEditorFactory.getInstance().getCustomerPropertyEditor(type);
                value = propertyEditor.converToType(innervalue.getValue());
            }
            pv.converted();
        }
        return value;
    }
}
