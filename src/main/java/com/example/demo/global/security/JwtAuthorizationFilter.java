package com.example.demo.global.security;

import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.rsData.RsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;


/*@Component 어노테이션을 이용하면 Bean Configuration 파일에 Bean을 따로 등록하지 않아도 사용할 수 있다.
빈 등록자체를 빈 클래스 자체에다가 할 수 있다는 의미이다.*/
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        /*로그인 로그아웃 일때에는 jwt검증하지 않고 통과시킨다*/
        if (request.getRequestURI().equals("/api/v1/members/login") || request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        /*쿠키에서 accessToken을 가지고 온다*/
        String accessToken = _getCookie("accessToken");

        // accessToken 검증 or refreshToken 발급

        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증 즉 유효성 검증
            if (!memberService.validateToken(accessToken)) {
                /*만약유효하지 않다면*/
                /*refreshToken 을 가지고 온다*/
                String refreshToken = _getCookie("refreshToken");
                /*refreshAccessToken 메소드를 이용해서 새로운 accessToken을 발급받는다*/
                RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                /*새로운 accessToken을 _addHeaderCookie에 저장한다*/
                _addHeaderCookie("accessToken", rs.getData());
            }

            // securityUser 가져오기
            /*토큰에서 사용자 정보를 가지고 온다*/
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            // 인가 처리
            /**/
            SecurityContextHolder.getContext().setAuthentication(securityUser.genAuthentication());
        }

        filterChain.doFilter(request, response);
    }

    private String _getCookie(String name) {
        Cookie[] cookies = req.getCookies();

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    private void _addHeaderCookie(String tokenName, String token) {
        ResponseCookie cookie = ResponseCookie.from(tokenName, token)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

}
