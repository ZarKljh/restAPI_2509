package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.request.MemberRequest;
import com.example.demo.domain.member.dto.response.MemberResponse;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.rsData.RsData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
    public RsData<?> login(@Valid @RequestBody MemberRequest memberRequest){
        Member member = memberService.getMember(memberRequest.getUsername());

        //access token 발급
        String token = jwtProvider.genAccessToken(member);

        return RsData.of("200", "토큰 발급 성공", token);
    }
}
