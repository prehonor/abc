package com.abc.core;

import com.abc.core.parser.AnnotatedBeanDefinitionReader;
import com.abc.core.parser.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;

public class AnnotationConfigClassAppContext extends ApplicationContext {

    private AnnotatedBeanDefinitionReader reader;
    private ComponentBeanDefinitionScanner scanner;     //扫描指定包下的beandefination
    private DefaultListableBeanFactory factory = null;

    /**
     * @param clazz 添加@Configuration注解的配置类
     */
    public AnnotationConfigClassAppContext(Class clazz) {
        this.factory = createBeanFactory();
        this.scanner = new ComponentBeanDefinitionScanner();
        this.reader = new AnnotatedBeanDefinitionReader(factory,clazz);
        registry(clazz);
        onRefresh();
    }


    public DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    void registry(Class clazz){
        this.reader.registryBeanDefinition();
    }

    void onRefresh(){
        invokeBeanFactoryPostProcessors(factory,new ArrayList<BeanDefinitionRegistryPostProcessor>());//调用
        instantiateSingletons();
    }

    private void invokeBeanFactoryPostProcessors(BeanFactory beanFactory, List<BeanDefinitionRegistryPostProcessor> beanFactoryPostProcessors) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

            for (BeanDefinitionRegistryPostProcessor postProcessor : beanFactoryPostProcessors) {
                postProcessor.postProcessBeanDefinitionRegistry(registry);
            }
        }

        String[] postProcessorNames =
                factory.getBeanNamesByType(BeanDefinitionRegistryPostProcessor.class);
        List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

        //获取BeanDefinitionRegistryPostProcessor的实例
        for (String ppName : postProcessorNames) {
            currentRegistryProcessors.add((BeanDefinitionRegistryPostProcessor) factory.getBean(ppName));
        }
        //调用BeanDefinitionRegistryPostProcessor的接口方法
        for(BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor : currentRegistryProcessors){
            beanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(factory);
        }

    }
    public void instantiateSingletons(){
        factory.instantiateSingletons();
    }

    public Object getBean(String name){
        return factory.getBean(name);
    }
}
