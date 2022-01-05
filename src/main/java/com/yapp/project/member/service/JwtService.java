package com.yapp.project.member.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.member.dto.response.JwtValidationResult;
import com.yapp.project.member.dto.response.LoginResponse;
import com.yapp.project.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Duration;

@Service
public class JwtService {
    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.secret}")
    private String SECRET;

    private final MemberRepository memberRepository;

    private final Long accessExpiredTime = Duration.ofDays(365).toMillis();
    private final Long refreshExpiredTime = Duration.ofDays(365).toMillis();

    private JwtUtil jwtUtil;

    @Autowired
    public JwtService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void postConstruct() {
        jwtUtil = new JwtUtil(ISSUER, SECRET);
    }

    public LoginResponse loginResponse(Long memberId, String loginId){
        if (memberId == null){
            memberId = getMemberId(memberRepository.getTokenByLoginId(loginId));
        }
        return issue(memberId, loginId);
    }
    public LoginResponse issue(Long memberId, String loginId) {
        String accessToken = jwtUtil.createToken(memberId, loginId, accessExpiredTime);
        String refreshToken = jwtUtil.createToken(memberId, loginId, refreshExpiredTime);
        updateToken(loginId, refreshToken);
        return new LoginResponse(memberId, loginId, accessToken);
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

    public Long getMemberId(String accessToken) {
        /**
         * methodName : getMemberId
         * description : accesstoken을 넣으면 token내 포함되어 있는 memberId 반환
         * @param accessToken : client로 부터 받은 accesstoken
         */

        if(accessToken == null || accessToken.isEmpty()){
            throw  new IllegalRequestException(ExceptionMessage.ACCESS_TOKEN_IS_EMPTY);
        }
        Jws<Claims> jws;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        JwtParserBuilder jpb = Jwts.parserBuilder();
        jpb.setSigningKey(secretKey);
        jws = jpb.build().parseClaimsJws(accessToken);
        Long jwtMemberId = Long.parseLong(jws.getBody().get("memberId").toString());
        return jwtMemberId;
    }

    public void validateTokenForm(String token){
        if(token.isEmpty() || token == null || token.equals(" ")){
            throw new IllegalRequestException(ExceptionMessage.ACCESS_TOKEN_IS_EMPTY);
        }
    }
}
