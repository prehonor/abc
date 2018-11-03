package com.abc.core.propertyeditors;

public interface CustomerPropertyEditor<T> {
    T converToType(Object value);
}
