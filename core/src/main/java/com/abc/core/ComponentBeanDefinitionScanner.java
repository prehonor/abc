package com.abc.core;

import com.abc.core.annotation.Component;
import com.abc.core.io.ClassFileResource;
import com.abc.core.io.Resource;
import com.abc.core.parser.BeanDefinitionHolder;
import com.abc.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

/**
 * 扫描包下class文件,生成指定的 {@link BeanDefinitionHolder}
 * */
public class ComponentBeanDefinitionScanner {
    private final List<Class<? extends Annotation>> includeFilters = new ArrayList<>();
    public ComponentBeanDefinitionScanner(){
        includeFilters.add(Component.class);
    }

    public List<BeanDefinitionHolder> registryComponentBeanDefinition(String packagePath) throws IOException {
        List<BeanDefinitionHolder> beanDefinitionHolders = new ArrayList<>();
        String path = FileUtils.convertClassNameToResourcePath(packagePath);
        Enumeration<URL> resourceUrls = ClassLoader.getSystemClassLoader().getResources(path);
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            Set<File> files = FileUtils.getFile(url);
            for(File file : files){
                Resource resource = new ClassFileResource(file);
                SimpleMetadataReader metadataReader = new SimpleMetadataReader(resource,ClassLoader.getSystemClassLoader());
                if(isCondition(resource,metadataReader)){
                    String className = metadataReader.classMetadata.getSpecifiedBeanName();
                    beanDefinitionHolders.add(new BeanDefinitionHolder(className,new GenericBeanDefinition(className,metadataReader.classMetadata.getClassName())));
                }
            }
        }
        return beanDefinitionHolders;
    }

    public boolean isCondition(Resource resource, SimpleMetadataReader metadataReader){
        for(Class<? extends Annotation> annotation : includeFilters){
            if(metadataReader.classMetadata.getAnnotationTypes().contains(annotation.getName()))
                return true;
        }
        return false;
    }
}
