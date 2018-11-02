package com.abc.core.parser.support;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValueList;
    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }
    public PropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                pv = mergeIfRequired(pv, currentPv);
                setPropertyValueAt(pv, i);
                return this;
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    /**
     * 目前直接返回新值
     * */
    PropertyValue mergeIfRequired(PropertyValue newPv, PropertyValue currentPv){
        return newPv;
    }

    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.set(i, pv);
    }
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

}
