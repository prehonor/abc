package com.abc.core;

import static org.junit.Assert.assertTrue;

import com.abc.core.example.Apple;
import com.abc.core.example.pckage.lesson.ChineseLesson;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        ClassPathPropertiesAppContext context = new ClassPathPropertiesAppContext("abc.properties");
        Apple apple = (Apple) context.getBean("apple");
        System.out.println(apple.getName());
        ChineseLesson chineseLesson = (ChineseLesson) context.getBean("lesson");
        chineseLesson.function();
    }
}
