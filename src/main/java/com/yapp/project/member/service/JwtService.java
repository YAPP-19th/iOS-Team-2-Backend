package com.yapp.project.member.service;

import com.yapp.project.member.dto.JwtValidationResult;
import com.yapp.project.member.dto.LoginResponse;
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

    public LoginResponse loginResponse(Long memberId, String loginId){
        return issue(memberId, loginId);
    }
    public LoginResponse issue(Long memberId, String loginId) {
        String accessToken = jwtUtil.createToken(memberId, loginId, accessExpiredTime);
        String refreshToken = jwtUtil.createToken(memberId, loginId, refreshExpiredTime);
        updateToken(loginId, refreshToken);
        return new LoginResponse(loginId, accessToken);
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
                result = new JwtValidationResult(jws.getBody().get("userId").toString(), true);
            }else{
                result = new JwtValidationResult(jws.getBody().get("userId").toString(), false);
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

    public String getMemberId(String accessToken) {
        /**
         * methodName : getMemberId
         * description : accesstoken을 넣으면 token내 포함되어 있는 memberId 반환
         * @param accessToken : client로 부터 받은 accesstoken
         */
        Jws<Claims> jws;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        JwtParserBuilder jpb = Jwts.parserBuilder();
        jpb.setSigningKey(secretKey);
        jws = jpb.build().parseClaimsJws(accessToken);
        String jwtMemberId = jws.getBody().get("memberId").toString();
        return jwtMemberId;
    }
}
