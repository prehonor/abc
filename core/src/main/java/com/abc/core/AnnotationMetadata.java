package com.abc.core;

import com.abc.core.util.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadata implements AnnotatedTypeMetadata{
    private final Annotation[] annotations;
    private Class clazz;
    AnnotationMetadata(Class clazz){
        this.clazz = clazz;
        this.annotations=clazz.getAnnotations();
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
                (AnnotatedElement) getIntrospectedClass(), annotationName) : null);
    }
    @Override
    public Map<String, Object> getAnnotationAttributes(Class annotationName, boolean classValuesAsString) {
        return (this.annotations.length > 0 ? AnnotatedElementUtils.getMergedAnnotationAttributes(
                (AnnotatedElement) getIntrospectedClass(), annotationName) : null);
    }

    private Class getIntrospectedClass() {
        return clazz;
    }

    @Override
    public Set<String> getMetaAnnotationTypes(String annotationName) {
        return AnnotatedElementUtils.getMetaAnnotationTypes(getIntrospectedClass(), annotationName);
    }

    @Override
    public boolean isAnnotated(String annotationName) {
        return (this.annotations.length > 0 &&
                AnnotatedElementUtils.isAnnotated(getIntrospectedClass(), annotationName));
    }

    public Set<MethodMetadata> getAnnotatedMethods(String annotationName) {
        try {
            Method[] methods = getIntrospectedClass().getDeclaredMethods();
            Set<MethodMetadata> annotatedMethods = new LinkedHashSet<>(4);
            for (Method method : methods) {
                if (!method.isBridge() && method.getAnnotations().length > 0 &&
                        AnnotatedElementUtils.isAnnotated(method, annotationName)) {
                    annotatedMethods.add(new MethodMetadata(method));
                }
            }
            return annotatedMethods;
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to introspect annotated methods on " + getIntrospectedClass(), ex);
        }
    }
}
