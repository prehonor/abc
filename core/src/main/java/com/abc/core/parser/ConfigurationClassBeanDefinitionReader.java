package com.abc.core.parser;

import com.abc.core.*;
import com.abc.core.annotation.Bean;
import com.abc.core.util.ClassUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 解析配置类中注解了@Bean的方法,将其解析为特殊的BeanDefinition
 * */
public class ConfigurationClassBeanDefinitionReader implements ConfigFileParser {

    private DefaultListableBeanFactory factory = null;
    private BeanNameGenerator beanNameGenerator = new AnnotatedFactoryMethodBeanNameGenerator("name");
    private AnnotatedBeanDefinition beanDefinition;//注解类class
    private BeanDefinitionHolder beanDefinitionHolder;

    public ConfigurationClassBeanDefinitionReader(DefaultListableBeanFactory factory, BeanDefinitionHolder beanDefinitionHolder){
        this.factory = factory;
        this.beanDefinitionHolder = beanDefinitionHolder;
        beanDefinition = (AnnotatedBeanDefinition) beanDefinitionHolder.getBeanDefinition();
    }


    @Override
    public void registryBeanDefinition() {

        //处理 @ComponentScan注解
        if(checkCandidateForConfigClass(beanDefinition))
            parseScan();
        //检查 @Bean 注解的方法
        parseBeanMethods();
    }

    private void parseBeanMethods() {
        Set<MethodMetadata> methodMetadataSet = beanDefinition.getAnnotatioMetadata().getAnnotatedMethods("com.abc.core.annotation.Bean");
        for(MethodMetadata methodMetadata : methodMetadataSet){
            DefaultAnnotatedBeanDefinition defaultAnnotatedBeanDefinition = new DefaultAnnotatedBeanDefinition(beanDefinition.getTargetClass(),methodMetadata);
            defaultAnnotatedBeanDefinition.setFactoryBeanName(beanDefinitionHolder.getBeanName());
            AnnotationAttributes annotationAttributes = AnnotationConfigUtils.attributesFor(methodMetadata, Bean.class);
            String value =  beanNameGenerator.getBeanName(defaultAnnotatedBeanDefinition);
            factory.registryBeanDefinition(value,defaultAnnotatedBeanDefinition);
        }
    }

    private String[] getPackages() {
        String[] result;
        AnnotationAttributes annotationAttributes = AnnotationConfigUtils.attributesFor(beanDefinition.getAnnotatioMetadata(),"com.abc.core.annotation.ComponentScan");
        if(annotationAttributes!=null){
            Object value = annotationAttributes.get("basePackages");
            result = (String[])value;
            if(result.length==0){
                result = new String[]{ClassUtils.getPackageName(beanDefinition.getTargetClass())};
            }
            return result;
        }
        return null;
    }

    /**
     * @see com.abc.core.annotation.ComponentScan 注解
     * */
    private void parseScan() {
        String[] packages = getPackages();
        ComponentBeanDefinitionScanner scanner = new ComponentBeanDefinitionScanner();
        for(String packageStr : packages){
            try {
                List<BeanDefinitionHolder> beanDefinitionHolderList = scanner.registryComponentBeanDefinition(packageStr);
                registryBeanDefinition(beanDefinitionHolderList,factory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkCandidateForConfigClass(BeanDefinition beanDefinition){
        if(beanDefinition instanceof AnnotatedBeanDefinition){
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
            return annotatedBeanDefinition.getAnnotatioMetadata().isAnnotated("com.abc.core.annotation.ComponentScan");
        }
        return false;
    }
}
