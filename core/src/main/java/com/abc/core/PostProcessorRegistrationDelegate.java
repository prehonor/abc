package com.abc.core;

public class PostProcessorRegistrationDelegate {
    public static void registerBeanPostProcessors(
            DefaultListableBeanFactory beanFactory, ClassPathPropertiesAppContext applicationContext) {

        String[] beanNames = beanFactory.getBeanNamesByType(BeanPostProcessor.class);
        for(String beanName : beanNames){
            BeanPostProcessor instance = (BeanPostProcessor)beanFactory.getBean(beanName);
            beanFactory.addBeanPostProcessor(instance);
        }
    }
}
