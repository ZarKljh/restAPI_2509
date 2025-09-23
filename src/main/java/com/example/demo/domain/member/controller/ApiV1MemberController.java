package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.request.MemberRequest;
import com.example.demo.domain.member.dto.response.MemberResponse;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.rsData.RsData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value="/api/v1/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="ApiV1MemberController", description ="회원 인증/인가 API")
public class ApiV1MemberController {
    private final MemberService memberService;

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
}
