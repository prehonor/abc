package com.abc.core.propertyeditors;

public class BooleanPropertyEditor implements CustomerPropertyEditor<Boolean> {
    private Class<? extends Boolean> targetType;
    @Override
    public Boolean converToType(Object value) {
        if(value instanceof String){
            if("true".equals(value) || "yes".equals(value) || "1".equals(value) || "on".equals(value))
                return Boolean.TRUE;
            if("false".equals(value) || "no".equals(value) || "0".equals(value) || "off".equals(value)){
                return Boolean.FALSE;
            }
        }
        return false;
    }

    BooleanPropertyEditor(Class<? extends Boolean> targetType){
        this.targetType = targetType;
    }
}
