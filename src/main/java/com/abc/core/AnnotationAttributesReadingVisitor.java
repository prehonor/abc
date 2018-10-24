package com.abc.core;

import org.objectweb.asm.AnnotationVisitor;

import java.lang.annotation.Annotation;
import java.util.*;

public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {


    protected final Map<String, List<AnnotationAttributes>> attributesMap;
    protected final AnnotationAttributes attributes;
    private final String annotationType;

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }


    public AnnotationAttributesReadingVisitor(String annotationType, Map<String,List<AnnotationAttributes>> attributesMap,ClassLoader classLoader) {
        super(CommonValues.ASM_VERSION);
        this.attributesMap = attributesMap;
        this.annotationType = annotationType;
        attributes = new AnnotationAttributes(annotationType,classLoader);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();

        Class<?> annotationClass = this.attributes.annotationType();
        if (annotationClass != null) {
            List<AnnotationAttributes> attributeList = this.attributesMap.get(this.annotationType);
            if (attributeList == null) {
                attributeList = new ArrayList<AnnotationAttributes>();
                attributeList.add(this.attributes);
                this.attributesMap.put(this.annotationType, attributeList);
            }
            else {
                attributeList.get(0).putAll(this.attributes);
            }

        }
    }
}
