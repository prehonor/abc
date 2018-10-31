package com.abc.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeansProperties implements ParserData {

    private List<Properties> listProperties = new ArrayList<>();

    public void addProperties(Properties properties){
        listProperties.add(properties);
    }

    public List<Properties> getListProperties(){
        return listProperties;
    }
}
