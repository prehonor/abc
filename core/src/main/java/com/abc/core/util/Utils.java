package com.abc.core.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Utils {
    public static void toDo(String conment){
        System.out.println("未完成的函数:"+ conment);
    }
    public static boolean stringNotEmpty(String str){
        if(str==null || "".equals(str)) return false;
        return true;
    }
}
