package com.abc.core.propertyeditors;

public class NumberPropertyEditor implements CustomerPropertyEditor<Number> {
    private Class<? extends Number> targetType;
    @Override
    public Number converToType(Object value) {
        if(int.class.isAssignableFrom(targetType)){
            return Integer.valueOf((String)value);
        }
        return 0;
    }

    NumberPropertyEditor(Class<? extends Number> targetType){
        this.targetType = targetType;
    }

}
