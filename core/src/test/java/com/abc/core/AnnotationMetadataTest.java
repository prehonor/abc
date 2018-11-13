package com.abc.core;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

public class AnnotationMetadataTest {
    AnnotationMetadata metadata = new AnnotationMetadata(Orange.class);
    @Test
    public void getAnnotationTypes() {
        Assert.assertArrayEquals(metadata.getAnnotationTypes().toArray(),
                new String[]{"com.abc.core.annotation.Configuration","com.abc.core.annotation.ComponentScan"});
    }
}