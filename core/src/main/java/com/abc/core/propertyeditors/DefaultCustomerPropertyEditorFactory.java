package com.abc.core.propertyeditors;

import java.util.HashMap;
import java.util.Map;

public class DefaultCustomerPropertyEditorFactory implements ICustomerPropertyEditorFactory{
    private static DefaultCustomerPropertyEditorFactory instance = null;
    private static Map<Class,CustomerPropertyEditor> products = new HashMap<>();

    private DefaultCustomerPropertyEditorFactory(){
        createCustomerPropertyEditors();
    }

    public static DefaultCustomerPropertyEditorFactory getInstance(){
        if(instance==null)
            instance = new DefaultCustomerPropertyEditorFactory();
        return instance;
    }

    @Override
    public void createCustomerPropertyEditors() {
        products.put(int.class,new NumberPropertyEditor(int.class));
        products.put(Integer.class,new NumberPropertyEditor(Integer.class));
        products.put(float.class,new NumberPropertyEditor(float.class));
        products.put(Float.class,new NumberPropertyEditor(Integer.class));
        products.put(double.class,new NumberPropertyEditor(double.class));
        products.put(Double.class,new NumberPropertyEditor(Double.class));
        products.put(boolean.class,new BooleanPropertyEditor(boolean.class));
        products.put(Boolean.class,new BooleanPropertyEditor(Boolean.class));
    }

    @Override
    public CustomerPropertyEditor getCustomerPropertyEditor(Class type){
        return products.get(type);
    }
}
