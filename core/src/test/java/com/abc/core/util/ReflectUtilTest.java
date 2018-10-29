package com.abc.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class ReflectUtilTest{
    private ReflectUtil util = new ReflectUtil();
    @Test
    public void getFieldTypeBeanName() {
        Field[] fields = ReflectUtilTest.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String name = util.getFieldTypeBeanName(field);
            Assert.assertEquals("reflectUtil",name);
        }
    }

    @Test
    public void getDeclaredFields() {
        util.getDeclaredFields(null);
    }

}
