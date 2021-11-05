package com.yapp.project;

import com.yapp.project.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BudiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BudiApplication.class)
                .properties(ApplicationConfig.APPLICATION_LOCATIONS)
                .run(args);
    }

}
