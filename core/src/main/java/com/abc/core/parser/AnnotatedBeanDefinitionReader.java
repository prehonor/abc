package com.abc.core.parser;

import com.abc.core.*;


/**
 * 解析class中的注解
 * */
public class AnnotatedBeanDefinitionReader implements ConfigFileParser {

    private DefaultListableBeanFactory factory = null;
    private Class configurationClass;
    private BeanNameGenerator beanNameGenerator = new AnnotatedClassBeanNameGenerator();

    public AnnotatedBeanDefinitionReader(DefaultListableBeanFactory factory, Class configurationClass) {
        this.factory = factory;
        this.configurationClass = configurationClass;
        AnnotationConfigUtils.registryAnnotatedBeanPostProcessors(factory);
    }

    void registryBeanDefinition(Class annotatedClass) {
        DefaultAnnotatedBeanDefinition abd = new DefaultAnnotatedBeanDefinition(configurationClass);
        String id = beanNameGenerator.getBeanName(abd);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(id,abd);
        factory.registryBeanDefinition(id,abd);
    }

    @Override
    public void registryBeanDefinition() {
        registryBeanDefinition(configurationClass);
    }
}
