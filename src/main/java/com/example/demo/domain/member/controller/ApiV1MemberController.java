package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.request.MemberRequest;
import com.example.demo.domain.member.dto.response.MemberResponse;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.rsData.RsData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/api/v1/members")
@RequiredArgsConstructor
@Tag(name="ApiV1MemberController", description ="회원 인증/인가 API")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    /*
    @PostMapping("/join")
    public String join(@Valid @RequestBody MemberRequest memberRequest){
        memberService.join(memberRequest.getUsername(), memberRequest.getPassword());
        return "회원가입 성공";
    }
     */
    @PostMapping("/join")
    public RsData<MemberResponse> join(@Valid @RequestBody MemberRequest memberRequest){
        Member member = memberService.join(memberRequest.getUsername(), memberRequest.getPassword());
        return RsData.of("200", "회원가입이 완료되었습니다", new MemberResponse(member));
    }

    @PostMapping("/login")
    public RsData<MemberResponse> login(@Valid @RequestBody MemberRequest memberRequest, HttpServletResponse res){
        Member member = memberService.getMember(memberRequest.getUsername());

        //access token 발급
        String accessToken = jwtProvider.genAccessToken(member);
        Cookie cookie = new Cookie("accessToken", accessToken);
        /*자바스크립트 접근 방지*/
        cookie.setHttpOnly(true);
        /*https에서만 접근 가능, http에서는 접근금지*/
        cookie.setSecure(true);
        /*접근 가능 사이트는 모든 사이트*/
        cookie.setPath("/");
        /*유효시간은 60*60초 = 1시간*/
        cookie.setMaxAge(60*60);
        res.addCookie(cookie);

        /*자바에서 클라이언트에게 전달할 때 사용하는 개체*/
        /*postman에서 cookie탭에서 결과를 확인가능하다*/
        res.addCookie(new Cookie("accessToken", accessToken));

        return RsData.of("200", "토큰 발급 성공 : " + accessToken, new MemberResponse(member));
    }

    /*쿠키를 토큰으로 꺼내오는 메소드*/
    @GetMapping("/me")
    public RsData<MemberResponse> me(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        String accessToken = "";

        for(Cookie cookie : cookies){
            if("accessToken".equals(cookie.getName())){
                accessToken = cookie.getValue();
            }

        }
        /*토큰안에 있는 사용자 정보를 꺼내온다*/
        Map<String, Object> claims = jwtProvider.getClaims(accessToken);
        /*꺼내온 username은 Object 타입으로 String 으로 형변환한다*/
        String username = (String)claims.get("username");
        Member member = this.memberService.getMember(username);

        return RsData.of("200", "내 회원 정보", new MemberResponse(member));
    }
}
