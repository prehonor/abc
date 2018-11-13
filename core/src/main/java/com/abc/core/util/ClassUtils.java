package com.abc.core.util;

public class ClassUtils {
    public static String getPackageName(Class clazz) {
        String fqClassName = clazz.getName();
        int lastDotIndex = fqClassName.lastIndexOf(".");
        return (lastDotIndex != -1 ? fqClassName.substring(0, lastDotIndex) : "");
    }
}
