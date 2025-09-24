package com.example.demo.global.jwt;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.global.util.Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
비밀키 관리
getSecretKey
_getSecretKey

토큰 만들기
getRefreshToken
getAccessToken
genToken

토큰 확인/꺼내기
verify
getClaims
 */


/*우리가 직접 new JwtProvider() 를 하지 않아도 시스템이 알아서 주입한다*/
/* JwtProvider 는 Jwt 발급기 + 위조감별기 + 데이터추출기 의 역할을 한다*/
@Component
public class JwtProvider {
    /*application-secret.yml 에 적어놓은 secretKey를 가져온다*/
    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;
    /*secretKey를 자주 사용하므로 SecretKey에 저장해놓는다*/
    private SecretKey cachedSecretKey;

    // 시크릿키 가지고 오기
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    // 스크릿 키 인코딩
    /*문자열을 진짜 암호화 키 객체 (SecretKey 객체) 형식으로 바꾸는 메소드*/
    /*Base64 형식으로 인코딩합니다*/
    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());
        /*hmac 방식의 암호화 키를 만들어서 반환한다*/
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    // refreshToken 토큰 만들기
    /*유효기간 1년으로 설정*/
    /*토큰이 유효기간이 만료되었을 때 새로 발급하는 용도*/
    public String genRefreshToken(Member member) {
        return genToken(member, 60 * 60 * 24 * 365 * 1);
    }

    // accessToken 만들기
    /* 유효기간 10뷴욿 설정*/
    /*실제 api 요청시 인증할 때 사용*/
    public String genAccessToken(Member member) {
        return genToken(member, 60 * 10);
    }


    // 진짜 토큰 생성 로직
    /*여기가 중요하다*/
    public String genToken (Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();

        /*토큰안에 id와 username을 넣어서 body라는 키로 저장한다*/
        claims.put("id", member.getId());
        claims.put("username", member.getUsername());

        /*현재 시간에 second를 더해서 만료일을 만든다*/
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);


        return Jwts.builder()
                .claim("body", Util.json.toStr(claims)) /* payload에 데이터 담기*/
                .setExpiration(accessTokenExpiresIn) /* 언제까지 유효한지 설정*/
                .signWith(getSecretKey(), SignatureAlgorithm.HS512) /* 비밀키로 서명 signature생성*/
                .compact(); /*토큰문자열 완성*/
    }

    // 토큰 유효성 검증
    /*토큰이 유효한지 확인하는 메소드*/
    public boolean verify(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // 클레임 정보 받아오기
    /* body 로 부터 json문자열을 꺼낸다*/
    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);
        /* Map<String, Opject>형태로 반환한다*/
        return Util.toMap(body);
    }
}