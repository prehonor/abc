package com.abc.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类
 * */
public class ReflectUtil {

    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    private static final Field[] NO_FIELDS = {};

    public static Object getInstance(String name){
        Class clazz = null;
        try {
            clazz = Class.forName(name);
            return clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  判读前者是后者的同类或超类
     * */
    public static boolean isTypeClass(Class former,Class later){
        return former.isAssignableFrom(later);
    }
    /**
     *  判读前者是后者的同类或超类
     * */
    public static boolean isTypeClass(String former,String later,ClassLoader loader){
        try {
            return isTypeClass(loader.loadClass(former),(loader.loadClass(later)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        for (Field field : getDeclaredFields(clazz)) {
            try {
                fc.doWith(field);
            }
            catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
            }
        }
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, (result.length == 0 ? NO_FIELDS : result));
            }
            catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface FieldCallback {

        /**
         * Perform an operation using the given field.
         * @param field the field to operate on
         */
        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static String getFieldTypeBeanName(Field field){
        String name = field.getType().getName();
        name = name.substring(name.lastIndexOf(".")+1,name.length());
        name = new StringBuilder().append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
        return name;
    }
}
