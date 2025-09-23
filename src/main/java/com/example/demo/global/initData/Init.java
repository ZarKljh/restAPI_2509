package com.example.demo.global.initData;

import com.example.demo.domain.article.service.ArticleService;
import com.example.demo.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*h-2 데이터베이스 사용시 매번 데이터를 넣기가 귀찮으므로 시작과 동시에 데이터를 넣는 구문*/
@Configuration
public class Init {

    /*스프링부트가 실행되고 난후 바로 직후 에 실행되는 어노테이션*/
    @Bean
    CommandLineRunner initData(ArticleService articleService, MemberService memberService){
        return args -> {
            memberService.join("admin","1234");
            memberService.join("user1","1234");
            memberService.join("user2","1234");
            memberService.join("user3","1234");
            memberService.join("user4","1234");

            articleService.write("제목1","내용1");
            articleService.write("제목2","내용2");
            articleService.write("제목3","내용3");
            articleService.write("제목4","내용4");
            articleService.write("제목5","내용5");
            articleService.write("제목6","내용6");
        };
    }
}
