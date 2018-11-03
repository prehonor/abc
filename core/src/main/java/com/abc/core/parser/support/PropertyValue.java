package com.abc.core.parser.support;

import javax.annotation.Nullable;

public class PropertyValue {

    private final String name;

    private final Object value;

    private Object convertedValue;

    private  boolean converted = false;

    public PropertyValue(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return value;
    }

    public Object getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(Object convertedValue) {
        this.convertedValue = convertedValue;
    }

    public boolean isConverted(){
        return converted;
    }

    public void converted(){
        converted = true;
    }
}
