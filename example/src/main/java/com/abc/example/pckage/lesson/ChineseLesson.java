package com.abc.example.pckage.lesson;

import com.abc.core.annotation.Autowired;
import com.abc.core.annotation.Component;
import com.abc.core.util.SystemUtils;
import com.abc.example.pckage.lesson.dao.LessonDao;

@Component("lesson")
public class ChineseLesson {

    @Autowired
    private LessonDao dao;
    public void function(){
        SystemUtils.toDo("ChineseLesson function");
        dao.queryLessons();
    }
}
