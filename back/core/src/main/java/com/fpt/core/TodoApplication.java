package com.fpt.core;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.fpt")
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }

    @Bean
    public Module sortJacksonModule() {
        return new SortJacksonModule();
    }
}