package com.abc.core.util;

import com.abc.core.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotatedElementUtils {
    private static final Map<Class<? extends Annotation>, List<Method>> attributeMethodsCache =
            new ConcurrentHashMap<>(256);

    public static AnnotationAttributes getMergedAnnotationAttributes(
            AnnotatedElement element, Class<? extends Annotation> annotationType) {

        AnnotationAttributes attributes = searchWithGetSemantics(element, annotationType, null,new MergedAnnotationAttributesProcessor());

        return attributes;
    }

    public static AnnotationAttributes getMergedAnnotationAttributes(
            AnnotatedElement element, String annotationType) {

        AnnotationAttributes attributes = searchWithGetSemantics(element, null, annotationType,new MergedAnnotationAttributesProcessor());

        return attributes;
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element,
                                                Class<? extends Annotation> annotationType, String annotationName,Processor<T> mergedAnnotationAttributesProcessor) {

        T result = null;
        for (Annotation annotation : element.getAnnotations()) {
            if(annotationType==annotation.annotationType() || annotation.annotationType().getName().equals(annotationName)){
                result = mergedAnnotationAttributesProcessor.process(element,annotation);
                break;
            }
        }
        return result;
    }

    public static Set<String> getMetaAnnotationTypes(Object introspectedClass, String annotationName) {
        final Set<String> types = new LinkedHashSet<>();
        searchWithGetSemantics((AnnotatedElement) introspectedClass, null, annotationName, new Processor<Object>() {
            @Override
            public Object process(AnnotatedElement annotatedElement, Annotation annotation) {
                types.add(annotation.annotationType().getName());
                return types;
            }
        });
        return types;
    }

    public static boolean isAnnotated(Object introspectedClass, String annotationName) {
        return Boolean.TRUE.equals(searchWithGetSemantics((AnnotatedElement) introspectedClass, null, annotationName, new Processor<Boolean>() {
            @Override
            public Boolean process(AnnotatedElement annotatedElement, Annotation annotation) {
                return true;
            }
        }));
    }


    private interface Processor<T> {
        T process(AnnotatedElement annotatedElement, Annotation annotation);
    }

    /**
     * 通过反射调用注解实例的属性值的get方法,即获取注解中的参数值,没有参数值就获取注解参数的默认值
     * */
    private static class MergedAnnotationAttributesProcessor implements Processor<AnnotationAttributes> {

        @Override
        public AnnotationAttributes process(AnnotatedElement annotatedElement, Annotation annotation) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            AnnotationAttributes attributes = new AnnotationAttributes(annotationType);
            for (Method method : getAttributeMethods(annotationType)) {

                try {
                    Object attributeValue = method.invoke(annotation);
                    Object defaultValue = method.getDefaultValue();
                    attributes.put(method.getName(),attributeValue==null?defaultValue:attributeValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            return attributes;
        }
    }

    static List<Method> getAttributeMethods(Class<? extends Annotation> annotationType) {
        List<Method> methods = attributeMethodsCache.get(annotationType);
        if (methods != null) {
            return methods;
        }

        methods = new ArrayList<>();
        for (Method method : annotationType.getDeclaredMethods()) {
            ReflectUtil.makeAccessible(method);
            methods.add(method);

        }
        attributeMethodsCache.put(annotationType, methods);
        return methods;
    }
}
