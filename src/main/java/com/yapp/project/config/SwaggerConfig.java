package com.yapp.project.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact(
            "BUDI",
            "https://github.com/YAPP-19th/iOS-Team-2-Backend",
            "goharrm@naver.com"
    );

    private static final ApiInfo Default_API_INFO = new ApiInfo(
            "BUDI REST API",
            "BUDI back-end REST API Service",
            "1.0",
            "https://github.com/YAPP-19th/iOS-Team-2-Backend",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>()
    );

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json")
    );

    @Bean
    public Docket api() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .apiInfo(Default_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)

                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

    @Getter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "????????? ??????(0?????? ??????)", example = "0")
        private Integer page;

        @ApiModelProperty(value = "??? ????????? ??? ?????? ??????(?????? 100)", allowableValues="range[0, 100]", example = "20")
        private Integer size;

        @ApiModelProperty(value = "????????????(?????????: entity?????????,ASC|DESC, ??????: id,DESC (?????????))", example = "createdDate,DESC")
        private List<String> sort;
    }

}
