package com.abc.core;

import com.abc.core.annotation.Autowired;
import com.abc.core.util.AnnotatedElementUtils;
import com.abc.core.util.AutowiredCapableBeanFactory;
import com.abc.core.util.ReflectUtil;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor,BeanFactoryAware {

    private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>(256);
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;
    private AutowiredCapableBeanFactory beanFactory;


    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";


    public AutowiredAnnotationBeanPostProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }


    @Override
    public void postProcessMergedBeanDefinition(Object bean, String beanName) {
        InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass());
//        metadata.inject(bean,beanName);
    }
    @Override
    public Object postProcessPropertyValues(Object bean, String beanName) {
        InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass());
        metadata.inject(bean,beanName);
        return bean;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (AutowiredCapableBeanFactory)beanFactory;
    }

    public class AutowiredInjectedElement extends InjectionMetadata.InjectedElement{
        private final boolean required;
        AutowiredInjectedElement(Member member, boolean required){
            super(member);
            this.required = required;
        }
        public  void inject(Object bean,String beanName){
            Field field = (Field) this.member;
            Object value;
            Class fieldType = field.getType();
            value = beanFactory.resloveDependency(bean,fieldType,ReflectUtil.getFieldTypeBeanName(field));
            if (value != null) {
                ReflectUtil.makeAccessible(field);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz){

        InjectionMetadata metadata = this.injectionMetadataCache.get(beanName);
        if(metadata!=null) return metadata;
        synchronized (this.injectionMetadataCache) {
            metadata = this.injectionMetadataCache.get(beanName);
            if (metadata==null) {
                metadata = buildAutowiringMetadata(clazz);
                this.injectionMetadataCache.put(beanName, metadata);
            }else{
                return metadata;
            }
        }
        return metadata;
    }
    private InjectionMetadata buildAutowiringMetadata(final Class<?> clazz) {
        LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
        final LinkedList<InjectionMetadata.InjectedElement> currElements = new LinkedList<>();
        ReflectUtil.doWithLocalFields(clazz, field -> {
            AnnotationAttributes ann = findAutowiredAnnotation(field);
            if (ann != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    return;
                }
                boolean required = determineRequiredStatus(ann);
                elements.add(new AutowiredInjectedElement(field,required));
            }
        });
        return new InjectionMetadata(clazz, elements);
    }

    protected boolean determineRequiredStatus(AnnotationAttributes ann) {
        return (!ann.containsKey(this.requiredParameterName) ||
                this.requiredParameterValue == (Boolean) ann.get(this.requiredParameterName));
    }

    private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
        if (ao.getAnnotations().length > 0) {
            for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
                if (attributes != null) {
                    return attributes;
                }
            }
        }
        return null;
    }
    private class AutowiredMethodElement extends InjectionMetadata.InjectedElement {
        private final boolean required;
        public AutowiredMethodElement(Method method, boolean required) {
            super(method);
            this.required = required;
        }
        public void inject(Object bean,String beanName){
            //TODO 尚未实现
        }
    }
}
