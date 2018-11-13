package com.abc.core;

import com.abc.core.util.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MethodMetadata implements AnnotatedTypeMetadata{
    private Method method;
    private final Annotation[] annotations;

    public MethodMetadata(Method method) {
        this.method = method;
        annotations = method.getAnnotations();
    }

    @Override
    public Set<String> getAnnotationTypes() {
        Set<String> sets = new LinkedHashSet<>();
        for(Annotation annotation : annotations){
            sets.add(annotation.annotationType().getName());
        }
        return sets;
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
        return (this.annotations.length > 0 ? AnnotatedElementUtils.getMergedAnnotationAttributes(
                (AnnotatedElement) method, annotationName) : null);
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(Class annotationName, boolean classValuesAsString) {
        return (this.annotations.length > 0 ? AnnotatedElementUtils.getMergedAnnotationAttributes(
                (AnnotatedElement) method, annotationName) : null);
    }

    @Override
    public Set<String> getMetaAnnotationTypes(String annotationName) {
        return AnnotatedElementUtils.getMetaAnnotationTypes(method, annotationName);
    }

    @Override
    public boolean isAnnotated(String annotationName) {
        return (this.annotations.length > 0 &&
                AnnotatedElementUtils.isAnnotated(method, annotationName));
    }

    public Type getReturnType(){
        return method.getReturnType();
    }

    public String getFactoryMethodName(){
        return method.getName();
    }

    public Method getFactoryMethod(){
        return method;
    }
}
