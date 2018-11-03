package com.abc.core.propertyeditors;

public class NumberPropertyEditor implements CustomerPropertyEditor<Number> {
    private Class<Number> targetType;
    @Override
    public Number converToType(Object value) {
        if(int.class.isAssignableFrom(targetType)){
            return Integer.valueOf((String)value);
        }
        if(float.class.isAssignableFrom(targetType)){
            return Float.valueOf((String)value);
        }
        if(double.class.isAssignableFrom(targetType)){
            return Double.valueOf((String)value);
        }
        return 0;
    }

    NumberPropertyEditor(Class<? extends Number> targetType){
        this.targetType = (Class<Number>) targetType;
    }

}
