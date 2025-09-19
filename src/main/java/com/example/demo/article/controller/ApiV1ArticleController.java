package com.example.demo.article.controller;

import com.example.demo.article.entity.Article;
import com.example.demo.article.service.ArticleService;
import com.example.demo.global.jpa.ArticleDTO;
import com.example.demo.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//RestController 는 RestAPI에서 사용하는 컨트롤러이다
//@Controller와 @Responbody 를 합친것이라 생각하면 편하다
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    //Api V1 의 의미 restAPI형태 즉 제이슨형태로 데이터를 넘길 것이므로 Api V1 이라고 붙인다
    //이전까지는 articleController라고 했지만 이제부터는 apiV1이라고 앞에 붙인다
    //서비스를 하고 있는 컨트롤러를 수정해야하므로 버전관리를 한다
    private final ArticleService articleService;

    //restAPI에서는 어노테이션 만으로도 CRUD를 구별할수 있다
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
    @GetMapping("/listDTO")
    public List<ArticleDTO> listDTO(){
        List<ArticleDTO> articleList = new ArrayList<>();

        Article article1 = new Article("제목1", "내용1");
        articleList.add(new ArticleDTO(article1));
        Article article2 = new Article("제목2", "내용2");
        articleList.add(new ArticleDTO(article2));
        Article article3 = new Article("제목3", "내용3");
        articleList.add(new ArticleDTO(article3));
        //보통 이렇게 데이터를 보내지 않는다


        return articleList;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse {
        private final List<ArticleDTO> articleList;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleResponse {
        private final ArticleDTO article;
    }

    @GetMapping("/rsDatalist")
    public RsData<ArticlesResponse> listRsData(){
        List<ArticleDTO> articleList = new ArrayList<>();

        Article article1 = new Article("제목1", "내용1");
        articleList.add(new ArticleDTO(article1));
        Article article2 = new Article("제목2", "내용2");
        articleList.add(new ArticleDTO(article2));
        Article article3 = new Article("제목3", "내용3");
        articleList.add(new ArticleDTO(article3));
        //보통 이렇게 데이터를 보내지 않는다


        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList));
    }




    @GetMapping("")
    public String list(){
        return "목록";
    }
    @GetMapping("/{id}")
    public ArticleDTO getArticle(@PathVariable("id") Long id){
        Article article1 = new Article("제목4", "내용4");
        return new ArticleDTO(article1);
    }
    @GetMapping("/rsdata/{id}")
    public RsData<ArticleResponse> getRsDataArticle(@PathVariable("id") Long id){
        Article article1 = new Article("제목4", "내용4");

        ArticleDTO articleDTO = new ArticleDTO(article1);

        return RsData.of("200", "게시물 1건 조회 성공", new ArticleResponse(articleDTO));
    }

    @Data
    public static class ArticleRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    @PostMapping("")
    public String create(@RequestParam("subject") String subject, @RequestParam("content") String content){
        System.out.println(subject);
        System.out.println(content);
        return "등록";
    }
    @PostMapping("/rsdata")
    public String createRsData(@Valid @RequestBody ArticleRequest articleRequest){
        System.out.println(articleRequest.getSubject());
        System.out.println(articleRequest.getContent());
        return "등록";
    }


    @PatchMapping("/{id}")
    public String modify(@PathVariable("id") Long id, @RequestParam("subject") String subject, @RequestParam("content") String content){
        System.out.println(id);
        System.out.println(subject);
        System.out.println(content);
        return "수정";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println(id);
        return "삭제";
    }
}
