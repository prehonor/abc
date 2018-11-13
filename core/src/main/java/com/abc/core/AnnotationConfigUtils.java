package com.abc.core;

import com.abc.core.parser.BeanDefinitionHolder;
import com.abc.core.parser.BeanDefinitionRegistry;
import com.abc.core.util.AnnotatedElementUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationConfigUtils {
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "com.abc.core.internalAutowiredAnnotationProcessor";
    public static final String CONFIGURATION_BEAN_NAME_GENERATOR =
            "com.abc.core.internalConfigurationClassBeanProcessor";
    /**
     * 注册 诸如{@link com.abc.core.annotation.Autowired}对应{@link AutowiredAnnotationBeanPostProcessor},
     * {@link com.abc.core.annotation.Configuration}对应{@link ConfigurationClassPostProcessor}
     * 等框架级功能性后置处理器
     * */
    public static Set<BeanDefinitionHolder> registryAnnotatedBeanPostProcessors(BeanDefinitionRegistry beanDefinitionRegistry){
        Set<BeanDefinitionHolder> beanDefinitionHolders = new LinkedHashSet<>(8);
        if(!beanDefinitionRegistry.containsBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)){
            GenericBeanDefinition beanDefination = new GenericBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,AutowiredAnnotationBeanPostProcessor.class.getName());
            beanDefinitionRegistry.registryBeanDefinition(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,beanDefination);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,beanDefination);
            beanDefinitionHolders.add(beanDefinitionHolder);
        }
        if(!beanDefinitionRegistry.containsBeanDefinition(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR)){
            GenericBeanDefinition beanDefination = new GenericBeanDefinition(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR,ConfigurationClassPostProcessor.class.getName());
            beanDefinitionRegistry.registryBeanDefinition(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR,beanDefination);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR,beanDefination);
            beanDefinitionHolders.add(beanDefinitionHolder);
        }

        return beanDefinitionHolders;
    }

    public static AnnotationAttributes attributesFor(AnnotatedTypeMetadata amd, String type) {
        return (AnnotationAttributes)amd.getAnnotationAttributes(type,false);
    }

    public static AnnotationAttributes attributesFor(AnnotatedTypeMetadata amd, Class type) {
        return (AnnotationAttributes)amd.getAnnotationAttributes(type,false);
    }
}
