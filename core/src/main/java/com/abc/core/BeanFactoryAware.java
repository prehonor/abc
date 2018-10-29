package com.abc.core;

/**
 * 实现该接口的类被设置beanFactory实例
 * */
public interface BeanFactoryAware extends Aware{
    //
    void setBeanFactory(BeanFactory beanFactory);

}