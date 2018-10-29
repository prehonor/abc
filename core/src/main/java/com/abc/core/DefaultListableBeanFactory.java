package com.abc.core;

import com.abc.core.util.AutowiredCapableBeanFactory;
import com.abc.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements ListableBeanFactory, AutowiredCapableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();


    public void registryBeanDefinition(String key,BeanDefinition beanDefinition){
        if(beanDefinitionMap.get(key)==null){
            beanDefinitionMap.put(key,beanDefinition);

            beanDefinitionNames.add(key);
        }
    }

    @Override
    public Object getBean(String name){
        Object result = null;
        Object sharedInstance = getSingleton(name,false);
        if(sharedInstance!=null){
            result = sharedInstance;
        }else {
            result = getSingleton(name);
        }
        return result;
    }

    @Override
    public Object getBeanByType(Class clazz) {
        return null;
    }

    /**
     * 从缓存中取单例bean
     * */
    public Object getSingleton(String name){
        return getSingleton(name,true);
    }

    public Object getSingleton(String beanName,boolean isCreating) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if(singletonObject!=null) return singletonObject;
        synchronized (this.singletonObjects) {
            singletonObject = this.singletonObjects.get(beanName);
            boolean newSingleton = false;
            if(singletonObject==null && isCreating){
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singletonObject = create(beanName,beanDefinition);
                applyBeanPostProcessors(beanName,singletonObject);  //
                newSingleton = true;
                populateBean(beanName,singletonObject);//装配bean
                initializeBean(singletonObject,beanName,beanDefinition);//初始化bean
                if (newSingleton) {
                    addSingleton(beanName, singletonObject);
                }
            }
        }
        return singletonObject;
    }

    public void instantiateSingletons(){
        for(String beanName : this.beanDefinitionNames){
            //TODO 这里需要判断类的scope为single
            getBean(beanName);
        }
    }



    public Object create(String name,BeanDefinition beanDefinition){
        return doCreate(name,beanDefinition);
    }

    public void addSingleton(String beanName,Object beanInstance){
        singletonObjects.put(beanName,beanInstance);
    }

    /**
     * 实际的创建bean实例的方法
     * */
    public Object doCreate(String name , BeanDefinition beanDefinition){
        Object singletonObject = null;

        if(beanDefinition!=null){
            String className = beanDefinition.getClassName();
            singletonObject = ReflectUtil.getInstance(className);
        }
        return  singletonObject;
    }

    /**
     * 装配bean属性
     * */
    protected void populateBean(String beanName, Object instance){
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            bp.postProcessPropertyValues(instance,beanName);
        }
    }

    void applyBeanPostProcessors(String beanName,Object instance){
       for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors()){
           beanPostProcessor.postProcessMergedBeanDefinition(instance,beanName);
       }
    }

    /**
     * 获取BeanPostProcessor
     * */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    @Override
    public String[] getBeanNamesByType(Class clazz) {
        String[] array = new String[0];
        ArrayList list = new ArrayList();
        for(String beanName : beanDefinitionNames){
            Object instance = getSingleton(beanName,false);//从缓存中瞥一眼
            if(instance==null){
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                if(ReflectUtil.isTypeClass(clazz.getName(),beanDefinition.getClassName(),ClassLoader.getSystemClassLoader())){
                    list.add(beanName);
                }
            }
        }
        array = (String[])list.toArray(array);
        return array;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public BeanDefinition getBeanDefinition(String beanName){
        return this.beanDefinitionMap.get(beanName);
    }

    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    public void initializeBean(Object bean,String beanName,BeanDefinition beanDefinition){
        invokeAwareMethods(beanName, bean);
    }
    private void invokeAwareMethods(final String beanName, final Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }

    @Override
    public Object resloveDependency(Object bean,Class fieldType,String fieldName){
        return getBean(fieldName);
    }
}
