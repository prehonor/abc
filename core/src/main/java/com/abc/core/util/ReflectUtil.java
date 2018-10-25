package com.abc.core.util;

/**
 * 反射工具类
 * */
public class ReflectUtil {
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
}
