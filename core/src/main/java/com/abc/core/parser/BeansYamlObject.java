package com.abc.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeansYamlObject implements ParserData {
    private List<Object> listObjects = new ArrayList<>();

    public void add(Object object){
        listObjects.add(object);
    }

    public List<Object> getList(){
        return listObjects;
    }
}
