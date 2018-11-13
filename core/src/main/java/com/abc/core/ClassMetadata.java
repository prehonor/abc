package com.abc.core;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 定义访问存放类相关信息的数据结构的接口
 * */
public interface ClassMetadata {

    /**
     * 获取类全路径名称
     * */
    String getClassName();

    /**
     * 获取类级注解
     * */
    Set<String> getAnnotationTypes();

    /**
     * 获取类指定名称
     * */
    public String getSpecifiedBeanName();
}
