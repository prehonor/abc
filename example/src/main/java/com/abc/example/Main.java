package com.abc.example;

import com.abc.core.AnnotationConfigClassAppContext;
import com.abc.core.ClassPathPropertiesAppContext;
import com.abc.core.ClassPathYamlAppContext;
import com.abc.example.config.Cleaner;
import com.abc.example.config.Food;
import com.abc.example.config.Manager;
import com.abc.example.config.TestConfig;
import com.abc.example.config.animal.Chicken;
import com.abc.example.config.animal.Horse;
import com.abc.example.pckage.TeacherService;
import com.abc.example.pckage.lesson.ChineseLesson;

public class Main {

    public static void main(String[] args)
    {
//        testPropertiestAppContext();
//        testYamlAppContext();
//        testInjectProperty();
        testConfiguration();
    }

    public static void testPropertiestAppContext(){
        ClassPathPropertiesAppContext context = new ClassPathPropertiesAppContext("abc.properties");
        Orange orange = (Orange) context.getBean("orange");
        System.out.println(orange.getName());
        ChineseLesson chineseLesson = (ChineseLesson) context.getBean("lesson");
        chineseLesson.function();
    }

    public static void testYamlAppContext(){
        ClassPathYamlAppContext context = new ClassPathYamlAppContext("abc.yaml");
        Orange orange = (Orange) context.getBean("orange");
        System.out.println(orange.getName());
        ChineseLesson chineseLesson = (ChineseLesson) context.getBean("lesson");
        chineseLesson.function();
    }

    public static void testInjectProperty(){
        ClassPathYamlAppContext context = new ClassPathYamlAppContext("abc.yaml");
        TeacherService teacherService = (TeacherService) context.getBean("teacherService");
        teacherService.inventStudentParent();
    }

    public static void testConfiguration(){
        AnnotationConfigClassAppContext annotationConfigClassAppContext = new AnnotationConfigClassAppContext(TestConfig.class);
        Chicken chicken = (Chicken) annotationConfigClassAppContext.getBean("chicken");
        chicken.say();
        Horse horse = (Horse) annotationConfigClassAppContext.getBean("horse");
        horse.say();
        Food food = (Food) annotationConfigClassAppContext.getBean("food");
        food.info();

        Cleaner cleaner = (Cleaner) annotationConfigClassAppContext.getBean("cleaner");
        cleaner.clean();
        Manager manager = (Manager) annotationConfigClassAppContext.getBean("manager");
        manager.manage();

    }
}
