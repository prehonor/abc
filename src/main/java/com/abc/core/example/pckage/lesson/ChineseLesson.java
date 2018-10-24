package com.abc.core.example.pckage.lesson;

import com.abc.core.annotation.Component;
import com.abc.core.util.Utils;

@Component("lesson")
public class ChineseLesson {
    public void function(){
        Utils.toDo("ChineseLesson function");
    }
}
