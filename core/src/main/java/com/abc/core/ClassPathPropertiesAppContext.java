package com.abc.core;

public class ClassPathPropertiesAppContext {

    private String[] path;
    private PropertiesBeanDefinationReader reader;
    private DefaultListableBeanFactory factory =null;

    public ClassPathPropertiesAppContext(String... path){
        factory = createBeanFactory();
        reader = new PropertiesBeanDefinationReader(factory);
        this.path = path;
        onRefresh();
    }

    public void loadBeanDefination(){
        reader.loadBeanDefination(path);
    }

    public DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    public void onRefresh(){

        loadBeanDefination();
        registerBeanPostProcessors(factory);//注册框架预置的BeanPostProcess 比如依赖注入的注解的处理逻辑类
        instantiateSingletons();
    }

    public Object getBean(String name){
        return factory.getBean(name);
    }

    public void instantiateSingletons(){
        factory.instantiateSingletons();
    }
    protected void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory) {
        PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
    }
}
