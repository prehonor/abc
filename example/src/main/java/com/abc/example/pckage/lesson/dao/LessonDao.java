package com.abc.example.pckage.lesson.dao;

import com.abc.core.annotation.Component;
import com.abc.core.util.Utils;

@Component("lessonDao")
public class LessonDao {
    public void queryLessons(){
        Utils.toDo("dao层查询课程");
    }
}
