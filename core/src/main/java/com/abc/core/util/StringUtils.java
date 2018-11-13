package com.abc.core.util;

import java.beans.Introspector;
import java.util.Collection;

public class StringUtils {

    /**
     * 首字母小写
     * */
    public static String lowCapitalWorld(String name){
        return Introspector.decapitalize(name);
//        return new StringBuilder(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
    }

    /**
     * 判断目标字符串是否为空
     * */
    public static boolean stringNotEmpty(String str){
        if(str==null || "".equals(str)) return false;
        return true;
    }
    public static String getShortName(String className) {
        int lastDotIndex = className.lastIndexOf(".");
        int nameEndIndex = className.indexOf("$$");
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace("$", ".");
        return shortName;
    }
    /**
     * 将集合转化为数组
     * */
    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[0]);
    }
}
