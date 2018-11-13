package com.abc.example.config;

public class Manager {
    private String name;
    Manager(String name){
        this.name = name;
    }

    public void manage(){
        System.out.println(name +" 下课了");
    }
}
