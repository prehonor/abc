package com.abc.core.propertyeditors;

import java.util.HashMap;
import java.util.Map;

public class CustomerPropertyEditorFactory {
    private static CustomerPropertyEditorFactory instance = null;
    private static Map<Class,CustomerPropertyEditor> products = new HashMap<>();
    static{
        products.put(int.class,new NumberPropertyEditor(int.class));
        products.put(Integer.class,new NumberPropertyEditor(Integer.class));
        products.put(float.class,new NumberPropertyEditor(float.class));
        products.put(Float.class,new NumberPropertyEditor(Integer.class));
        products.put(double.class,new NumberPropertyEditor(double.class));
        products.put(Double.class,new NumberPropertyEditor(Double.class));
        products.put(boolean.class,new BooleanPropertyEditor(boolean.class));
        products.put(Boolean.class,new BooleanPropertyEditor(Boolean.class));
    }

    private CustomerPropertyEditorFactory(){

    }

    public static CustomerPropertyEditorFactory getInstance(){
        if(instance==null)
            instance = new CustomerPropertyEditorFactory();
        return instance;
    }

    public CustomerPropertyEditor getCustomerPropertyEditor(Class type){
        return products.get(type);
    }
}
