package com.yapp.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class InitialDataConfig {
    //TODO: 테스트용 데이터 값 주입
}
