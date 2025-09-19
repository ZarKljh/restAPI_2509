package com.example.demo.article.controller;

import com.example.demo.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//RestController 는 RestAPI에서 사용하는 컨트롤러이다
//RestAPI는 자동으로 문자열로 변환해준다.
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    //Api V1 의 의미 restAPI형태 즉 제이슨형태로 데이터를 넘길 것이므로 Api V1 이라고 붙인다
    //이전까지는 articleController라고 했지만 이제부터는 apiV1이라고 앞에 붙인다
    //서비스를 하고 있는 컨트롤러를 수정해야하므로 버전관리를 한다
    private final ArticleService articleService;

    //어노테이션 만으로도 CRUD를 구별할수 있다
    //다건 조회
    //@GetMapping
    // /articles

    //단건조회
    //@GetMapping("/{id}")
    // /articles/1

    //등록
    //@PostMapping
    // /articles

    //수정
    //@PatchMapping("/{id}")
    // /articles/1

    //삭제
    //@DeleteMapping("/{id}")
    // /articles/1

    @GetMapping("")
    public String list(){

        return "목록";
    }
    @GetMapping("/{id}")
    public String getArticle(){

        return "단건";
    }
    @PostMapping("")
    public String create(){

        return "";
    }
    @PatchMapping("/{id}")
    public String modify(){

        return "";
    }
    @DeleteMapping("/{id}")
    public String delete(){

        return "";
    }

}
