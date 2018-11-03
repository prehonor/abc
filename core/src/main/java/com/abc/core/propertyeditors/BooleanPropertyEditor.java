package com.abc.core.propertyeditors;

public class BooleanPropertyEditor implements CustomerPropertyEditor<Boolean> {
    @Override
    public Boolean converToType(Object value) {
        if(value instanceof String){
            return Boolean.valueOf((String) value);
        }
        return false;
    }

}
