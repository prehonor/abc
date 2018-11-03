package com.abc.core.propertyeditors;

import java.util.HashMap;
import java.util.Map;

public class CustomerPropertyEditorFactory {

    private static CustomerPropertyEditorFactory instance = null;
    private static Map<Class,CustomerPropertyEditor> products = new HashMap<>();
    static{
        products.put(int.class,new NumberPropertyEditor(int.class));
        products.put(Integer.class,new NumberPropertyEditor(Integer.class));
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
