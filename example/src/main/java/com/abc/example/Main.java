package com.abc.example;

import com.abc.core.ClassPathPropertiesAppContext;
import com.abc.example.pckage.lesson.ChineseLesson;

public class Main {
    public static void main(String[] args)
    {
        ClassPathPropertiesAppContext context = new ClassPathPropertiesAppContext("abc.properties");
        Orange orange = (Orange) context.getBean("orange");
        System.out.println(orange.getName());
        ChineseLesson chineseLesson = (ChineseLesson) context.getBean("lesson");
        chineseLesson.function();
    }
}
