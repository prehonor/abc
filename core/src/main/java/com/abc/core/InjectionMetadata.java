package com.abc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Collection;

public class InjectionMetadata {
    private final Class<?> targetClass;
    private final Collection<InjectedElement> injectedElements;


    InjectionMetadata(Class<?> targetClass,Collection<InjectedElement> elements){
        this.targetClass = targetClass;
        this.injectedElements = elements;
    }
    public abstract static class InjectedElement {
        protected Member member;
        protected final boolean isField;
        InjectedElement(Member member){
            this.member = member;
            this.isField = (member instanceof Field);
        }
        public abstract void inject(Object bean,String beanName);
    }

    public void inject(Object bean,String beanName){
        for (InjectedElement element : injectedElements) {
            element.inject(bean,beanName);
        }
    }
}
