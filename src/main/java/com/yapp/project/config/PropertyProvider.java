package com.yapp.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("service.properties")
public class PropertyProvider {

    private final String IMAGE_DIR;

    public PropertyProvider(@Value("${s3.image-dir}") String IMAGE_DIR) {

        this.IMAGE_DIR = IMAGE_DIR;
    }
}
