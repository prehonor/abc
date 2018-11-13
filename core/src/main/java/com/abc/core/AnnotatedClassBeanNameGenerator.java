package com.abc.core;

import com.abc.core.util.ReflectUtil;
import com.abc.core.util.StringUtils;

import java.util.Set;

public class AnnotatedClassBeanNameGenerator implements BeanNameGenerator {
    private String key = "value";

    public AnnotatedClassBeanNameGenerator() {
    }

    @Override
    public String getBeanName(BeanDefinition beanDefinition) {
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) beanDefinition);
            if (StringUtils.stringNotEmpty(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }

        return generateNormal(beanDefinition);
    }

    private String generateNormal(BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        className = StringUtils.getShortName(className);
        className = StringUtils.lowCapitalWorld(className);
        return className;
    }

    public AnnotatedClassBeanNameGenerator(String key){
        this.key = key;
    }
    private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotatedTypeMetadata amd = annotatedDef.getAnnotatioMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(amd, type);
            if (attributes != null && isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
                Object value = attributes.get(this.key);
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.stringNotEmpty(strVal)) {
                        if (beanName != null && !strVal.equals(beanName)) {
                            throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
                                    "component names: '" + beanName + "' versus '" + strVal + "'");
                        }
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    private boolean isStereotypeWithNameValue(String type, Set<String> metaAnnotationTypes, AnnotationAttributes attributes) {
        Boolean isCompoent = metaAnnotationTypes.contains("com.abc.core.annotation.Component") ||
                "com.abc.core.annotation.Component".equals(type);
        return isCompoent && attributes!=null && attributes.containsKey(key);
    }
}
