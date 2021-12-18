package com.yapp.project;

import com.yapp.project.config.ApplicationConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class BudiApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(BudiApplication.class)
                .properties(ApplicationConfig.APPLICATION_LOCATIONS)
                .run(args);
    }

}
