package com.abc.core.parser;

import java.util.ArrayList;
import java.util.List;

public class AnnotedClassParserData implements ParserData {
    private List<Class> datas = new ArrayList<>();

    public void addData(Class clazz){
        datas.add(clazz);
    }

    public List<Class> getDatas(){
        return datas;
    }
}
