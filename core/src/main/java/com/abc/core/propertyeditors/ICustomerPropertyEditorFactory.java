package com.abc.core.propertyeditors;

public interface ICustomerPropertyEditorFactory {
    void createCustomerPropertyEditors();
    CustomerPropertyEditor getCustomerPropertyEditor(Class type);
}
