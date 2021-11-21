package com.yapp.project.member.service;

import io.jsonwebtoken.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

class JwtUtil {
    private final String ISSUER;
    private final String SECRET;

    JwtUtil(String issuer, String secret) {
        ISSUER = issuer;
        SECRET = secret;
    }

    public String createToken(String userId, Long expiredTime){
        Date ext = new Date(System.currentTimeMillis()); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("issuer", ISSUER);
        payloads.put("ExpiredPeriod", "3600");
        payloads.put("userId",userId);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact(); // 토큰 생성

        return jwt;
    }
}

