package com.abc.example.pckage.lesson.dao;

import com.abc.core.annotation.Component;
import com.abc.core.util.SystemUtils;

@Component("lessonDao")
public class LessonDao {
    public void queryLessons(){
        SystemUtils.toDo("dao层查询课程");
    }
}
