package com.abc.core.util;

import org.junit.Test;

import java.beans.Introspector;
import java.util.Collection;

import static org.junit.Assert.*;

public class StrSystemUtilsTest {

    @Test
    public void lowCapitalWorld() {
        System.out.println(Introspector.decapitalize("TestConfiguration"));
    }

}