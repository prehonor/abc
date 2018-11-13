package com.abc.core;

import com.abc.core.parser.BeanDefinitionHolder;
import com.abc.core.parser.BeanDefinitionRegistry;
import com.abc.core.parser.ConfigurationClassBeanDefinitionReader;

public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor{

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        //获取已注册BeanDefinition
        String[] beanNames = registry.getBeanDefinitionNames();
        for(String beanName : beanNames){
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if(beanDefinition instanceof AnnotatedBeanDefinition){
                AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition) beanDefinition).getAnnotatioMetadata();
                if(registry instanceof DefaultListableBeanFactory && annotationMetadata.isAnnotated("com.abc.core.annotation.Configuration")){//如果是@Configuration注解的配置类
                    BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanName,beanDefinition);
                    ConfigurationClassBeanDefinitionReader reader = new ConfigurationClassBeanDefinitionReader((DefaultListableBeanFactory)registry,beanDefinitionHolder);
                    reader.registryBeanDefinition();
                }
            }
        }
    }

}
