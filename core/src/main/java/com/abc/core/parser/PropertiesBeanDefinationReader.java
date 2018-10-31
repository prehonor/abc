package com.abc.core.parser;

import com.abc.core.AutowiredAnnotationBeanPostProcessor;
import com.abc.core.BeanDefinition;
import com.abc.core.ComponetBeanDifinitionScanner;
import com.abc.core.DefaultListableBeanFactory;
import com.abc.core.io.ClassPathResource;
import com.abc.core.io.Resource;
import com.abc.core.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 读取properties文件并解析为BeanDefination的实际处理类
 * */
public class PropertiesBeanDefinationReader implements ConfigFileParser {

    private DefaultListableBeanFactory factory = null;

    public PropertiesBeanDefinationReader(DefaultListableBeanFactory factory){
        this.factory = factory;
    }

    public static final String REF_FILE = "file";
    public static final String SCAN_PACKAGE = "scan-package";




    public void doLoadBeanDefination(Resource resource,BeansProperties beansProperties){
        Utils.toDo("解析properties文件定义的bean");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        beansProperties.addProperties(properties);
        String value = properties.getProperty(REF_FILE);
        if(value!=null && !value.equals("")){
            Resource recursionResource = new ClassPathResource(value);
            doLoadBeanDefination(resource , beansProperties);
        }

    }

    public void processBeanDefination(String key,String value){
        BeanDefinition beanDefination = new BeanDefinition(key,value);
        getRegistry().registryBeanDefinition(key,beanDefination);

    }

    public DefaultListableBeanFactory getRegistry(){
        assert factory!=null : "注册中心为空";
        return factory;
    }

    public List<BeanDefinitionHolder> scanCandidateComponents(String key,String value){
        ComponetBeanDifinitionScanner componetBeanDifinitionScanner = new ComponetBeanDifinitionScanner();
        try {
            return componetBeanDifinitionScanner.registryComponentBeanDefinition(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 注册处理依赖注入的类的beandefinition
     * */
    public List<BeanDefinitionHolder> getInnerComponents(){
        List<BeanDefinitionHolder> list = new ArrayList<>();
        if(!factory.containsBeanDefinition(AutowiredAnnotationBeanPostProcessor.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)){
            BeanDefinition beanDefination = new BeanDefinition(AutowiredAnnotationBeanPostProcessor.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,AutowiredAnnotationBeanPostProcessor.class.getName());
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(AutowiredAnnotationBeanPostProcessor.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,beanDefination);
            list.add(beanDefinitionHolder);
        }
        return list;
    }

    @Override
    public ParserData parserFrom(Resource resource) {
        BeansProperties beansProperties = new BeansProperties();
        doLoadBeanDefination(resource,beansProperties);
        return beansProperties;
    }

    @Override
    public List<BeanDefinitionHolder> convertToBeanDefinition(ParserData data) {
        List<BeanDefinitionHolder> list = new ArrayList<>();
        BeansProperties beansProperties = (BeansProperties) data;
        for (Properties properties : beansProperties.getListProperties()) {
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = properties.getProperty(key);
                if (!key.equals(REF_FILE)) {
                    if (key.equals(SCAN_PACKAGE)) {
                        list.addAll(scanCandidateComponents(key, value));
                        list.addAll(getInnerComponents());
                    } else {
                        processBeanDefination(key, value);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public void registryBeanDefinition(List<BeanDefinitionHolder> beanholders, BeanDefinitionRegistry registry) {

    }

}
