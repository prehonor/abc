package com.abc.core;


import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

public class AnnotationAttributes extends LinkedHashMap<String, Object> {

    private final Class<? extends Annotation> annotationType;


    public AnnotationAttributes(String annotationType, ClassLoader classLoader){

        this.annotationType  = getAnnotationType(annotationType,classLoader);

    }

    public AnnotationAttributes(Class annotationType){
        this.annotationType = annotationType;
    }

    public Class<? extends Annotation> annotationType() {
        return this.annotationType;
    }


    private Class<? extends Annotation> getAnnotationType(String annotationType, ClassLoader classLoader) {
        if (classLoader != null) {
            try {
                return (Class<? extends Annotation>) classLoader.loadClass(annotationType);
            }
            catch (ClassNotFoundException ex) {
                // Annotation Class not resolvable

            }
        }
        return null;
    }

}
