package com.abc.core;

import com.abc.core.io.ClassPathResource;
import com.abc.core.io.Resource;
import com.abc.core.parser.ConfigFileParser;
import com.abc.core.parser.ParserData;
import com.abc.core.parser.YamlBeanDefinitionReader;

public class ClassPathYamlAppContext extends ApplicationContext{
    private String[] paths;
    private ConfigFileParser reader;
    private DefaultListableBeanFactory factory = null;

    public ClassPathYamlAppContext(String... paths){
        factory = createBeanFactory();
        this.paths = paths;
        reader = createConfigFileParser();
        onRefresh();
    }

    public void loadBeanDefination(){
        reader.registryBeanDefinition();
    }

    public DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    public void onRefresh(){

        loadBeanDefination();
        registerBeanPostProcessors(factory);//注册框架预置的BeanPostProcess 比如依赖注入的注解的处理逻辑类实例
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


    public ConfigFileParser createConfigFileParser(){
        return new YamlBeanDefinitionReader(factory,paths);
    }
}
