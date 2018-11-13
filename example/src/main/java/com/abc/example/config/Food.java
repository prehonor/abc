package com.abc.example.config;

import com.abc.core.annotation.Component;

@Component("food")
public class Food {
    public void info(){
        System.out.println("food is enough");
    }
}
