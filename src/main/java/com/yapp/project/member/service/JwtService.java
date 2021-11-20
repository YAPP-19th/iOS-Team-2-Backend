package com.yapp.project.member.service;

import com.yapp.project.member.dto.JwtValidationResult;
import com.yapp.project.member.dto.TokenResponse;
import com.yapp.project.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Duration;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtService {
    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.secret}")
    private String SECRET;

    private final MemberRepository memberRepository;

    private final Long accessExpiredTime = Duration.ofHours(1).toMillis();
    private final Long refreshExpiredTime = Duration.ofDays(30).toMillis();

    private JwtUtil jwtUtil;

    @PostConstruct
    public void postConstruct() {
        jwtUtil = new JwtUtil(ISSUER, SECRET);
    }

    public TokenResponse loginResponse(String loginId){
        return issue(loginId);
    }
    public TokenResponse issue(String loginId) {
        String accessToken = jwtUtil.createToken(loginId, accessExpiredTime);
        String refreshToken = jwtUtil.createToken(loginId, refreshExpiredTime);
        updateToken(loginId, refreshToken);
        return new TokenResponse(loginId, accessToken);
    }

    @Transactional
    public void updateToken(
            String loginId,
            String refreshToken
    ) {
        memberRepository.setRefreshTokenByLoginId(loginId, refreshToken);
    }

    public JwtValidationResult validate(String userId, String accessToken) {
        Jws<Claims> jws;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        JwtValidationResult result;
        JwtParserBuilder jpb = Jwts.parserBuilder();
        jpb.setSigningKey(secretKey);
        try {
            jws = jpb.build().parseClaimsJws(accessToken);
            String jwtUserId = jws.getBody().get("userId").toString();
            if(jwtUserId.equals(userId)){
                result = new JwtValidationResult(Long.parseLong(jws.getBody().get("userId").toString()), true);
            }else{
                result = new JwtValidationResult(Long.parseLong(jws.getBody().get("userId").toString()), false);
            }
        } catch (ExpiredJwtException e) {
            String token = memberRepository.getTokenByLoginId(userId);
            try {
                jpb.build().parseClaimsJws(token);
                result = new JwtValidationResult(null, true);
            } catch (ExpiredJwtException exception){
                result = new JwtValidationResult(null, false);
            }
        }
        catch (JwtException e) {
            result = new JwtValidationResult(null, false);
        }
        return result;
    }
}
