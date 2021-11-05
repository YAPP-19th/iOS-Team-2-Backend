package com.yapp.project.config;

public class ApplicationConfig {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:application-real.yml,"
            + "classpath:aws.yml";
}
