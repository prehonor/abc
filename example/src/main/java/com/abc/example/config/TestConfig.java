package com.abc.example.config;

import com.abc.core.annotation.Bean;
import com.abc.core.annotation.ComponentScan;
import com.abc.core.annotation.Configuration;

@Configuration
@ComponentScan
public class TestConfig {
    @Bean
    public Cleaner newCleaner(){
        Cleaner cleaner = new Cleaner("詹姆斯");
        return cleaner;
    }
    @Bean
    public Manager getManager(){
        Manager manager = new Manager("泰伦卢");
        return manager;
    }
}
