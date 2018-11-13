package com.abc.core;

import java.util.Map;
import java.util.Set;

public interface AnnotatedTypeMetadata {


    Set<String> getAnnotationTypes();

    Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString);

    Map<String, Object> getAnnotationAttributes(Class annotationName, boolean classValuesAsString);

    Set<String> getMetaAnnotationTypes(String annotationName);

    boolean isAnnotated(String annotationName);
}
