package com.abc.core;

import com.abc.core.annotation.ComponentScan;
import com.abc.core.annotation.Configuration;

@Configuration
@ComponentScan
public class Orange {

    public String getColor(){
        return "黄色";
    }
}
