package com.abc.core;

import com.abc.core.io.ClassFileResource;
import com.abc.core.io.PropertiesResource;
import com.abc.core.util.FileUtils;
import com.abc.core.util.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 * 读取properties文件并解析为BeanDefination的实际处理类
 * */
public class PropertiesBeanDefinationReader {

    private ListableBeanFactory factory = null;

    PropertiesBeanDefinationReader(ListableBeanFactory factory){
        this.factory = factory;
    }

    public static final String REF_FILE = "file";
    public static final String SCAN_PACKAGE = "scan-package";

    public void loadBeanDefination(String[] path){
        for(int i=0;i<path.length;i++){
            loadBeanDefination(path[i]);
        }
    }

    public void loadBeanDefination(String path){
        doLoadBeanDefination(path);
    }

    public void doLoadBeanDefination(String path){
        Utils.toDo("解析properties文件定义的bean");
        Properties properties = new Properties();
        Resource resource = new PropertiesResource(path);
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration en = properties.propertyNames();
        while (en.hasMoreElements())
        {
            String key = (String) en.nextElement();
            String value = properties.getProperty(key);
            if(key.equals(REF_FILE)){
                loadBeanDefination(value);
            }else if(key.equals("scan-package")){
                scanCandidateComponents(key,value);
            }else{
                processBeanDefination(key,value);
            }
        }
    }
    public void processBeanDefination(String key,String value){
        BeanDefinition beanDefination = new BeanDefinition(key,value);
        getRegistry().registryBeanDefinition(key,beanDefination);

    }

    public ListableBeanFactory getRegistry(){
        assert factory!=null : "注册中心为空";
        return factory;
    }

    public void scanCandidateComponents(String key,String value){
        ComponetBeanDifinitionScanner componetBeanDifinitionScanner = new ComponetBeanDifinitionScanner(this);
        try {
            componetBeanDifinitionScanner.registryComponentBeanDefinition(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
