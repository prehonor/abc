package com.abc.core;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 定义访问存放类相关信息的数据结构的接口
 * */
public interface ClassMetadata {
    String getClassName();
    Set<String> getAnnotationTypes();
    public String getSpecifiedBeanName();
}
