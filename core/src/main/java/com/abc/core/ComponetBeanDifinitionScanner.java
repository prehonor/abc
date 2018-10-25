package com.abc.core;

import com.abc.core.annotation.Component;
import com.abc.core.io.ClassFileResource;
import com.abc.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

public class ComponetBeanDifinitionScanner {
    private final List<Class<? extends Annotation>> includeFilters = new ArrayList<>();
    PropertiesBeanDefinationReader reader;
    ComponetBeanDifinitionScanner(PropertiesBeanDefinationReader reader){
        includeFilters.add(Component.class);
        this.reader = reader;
    }

    public void registryComponentBeanDefinition(String packagePath) throws IOException {

        String path = FileUtils.convertClassNameToResourcePath(packagePath);
        Enumeration<URL> resourceUrls = ClassLoader.getSystemClassLoader().getResources(path);
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            Set<File> files = FileUtils.getFile(url);
            for(File file : files){
                Resource resource = new ClassFileResource(file);
                SimpleMetadataReader metadataReader = new SimpleMetadataReader(resource,ClassLoader.getSystemClassLoader());
                if(isCondition(resource,metadataReader)){
                    reader.processBeanDefination(metadataReader.classMetadata.getSpecifiedBeanName(),metadataReader.classMetadata.getClassName());
                }
            }
        }

    }

    public boolean isCondition(Resource resource, SimpleMetadataReader metadataReader){
        for(Class<? extends Annotation> annotation : includeFilters){
            if(metadataReader.classMetadata.getAnnotationTypes().contains(annotation.getName()))
                return true;
        }
        return false;
    }
}
