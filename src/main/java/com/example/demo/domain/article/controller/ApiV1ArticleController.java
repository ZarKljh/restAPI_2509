package com.example.demo.domain.article.controller;

import com.example.demo.domain.article.dto.ArticleDTO;
import com.example.demo.domain.article.dto.request.ArticleCreateRequest;
import com.example.demo.domain.article.dto.request.ArticleModifyRequest;
import com.example.demo.domain.article.entity.Article;
import com.example.demo.domain.article.response.ArticleCreateResponse;
import com.example.demo.domain.article.response.ArticleModifyResponse;
import com.example.demo.domain.article.response.ArticleResponse;
import com.example.demo.domain.article.service.ArticleService;
import com.example.demo.global.rsData.RsData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//RestController 는 RestAPI에서 사용하는 컨트롤러이다
//@Controller와 @Responbody 를 합친것이라 생각하면 편하다
@RestController
@RequestMapping(value = "/api/v1/articles")
@RequiredArgsConstructor
@Tag(name = "ApiV1ArticleController")
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
//    @GetMapping("/listDTO")
//    @Operation(summary = "게시물 다건 조회")
//    public List<ArticleDTO> listDTO(){
//        List<ArticleDTO> articleList = new ArrayList<>();
//
//        Article article1 = new Article("제목1", "내용1");
//        articleList.add(new ArticleDTO(article1));
//        Article article2 = new Article("제목2", "내용2");
//        articleList.add(new ArticleDTO(article2));
//        Article article3 = new Article("제목3", "내용3");
//        articleList.add(new ArticleDTO(article3));
//
//        return articleList;
//    }
//
//
//
//    @GetMapping("/rsdatalist")
//    public RsData<ArticlesResponse> listRsData(){
//        List<ArticleDTO> articleList = new ArrayList<>();
//
//        Article article1 = new Article("제목1", "내용1");
//        articleList.add(new ArticleDTO(article1));
//        Article article2 = new Article("제목2", "내용2");
//        articleList.add(new ArticleDTO(article2));
//        Article article3 = new Article("제목3", "내용3");
//        articleList.add(new ArticleDTO(article3));
//
//        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList));
//    }
//    /*실제 서비스와 리포지터리를 이용한 데이터 이동실습*/
//    @GetMapping("/rsdatalistv1")
//    public RsData<ArticlesResponse> listRsDatav1(){
//        List<ArticleDTO> articleList = articleService.getList();
//        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList));
//    }
//
//
//    @GetMapping("")
//    public String list(){
//        return "목록";
//    }
//    @GetMapping("/{id}")
//    public ArticleDTO getArticle(@PathVariable("id") Long id){
//        Article article1 = new Article("제목4", "내용4");
//        return new ArticleDTO(article1);
//    }
//    @GetMapping("/rsdata/{id}")
//    public RsData<ArticleResponse> getRsDataArticle(@PathVariable("id") Long id){
//        Article article1 = new Article("제목4", "내용4");
//
//        ArticleDTO articleDTO = new ArticleDTO(article1);
//
//        return RsData.of("200", "게시물 1건 조회 성공", new ArticleResponse(articleDTO));
//    }
    /*실제 서비스와 리포지터리를 이용한 데이터 이동실습*/
    @GetMapping("/rsdatav1/{id}")
    public RsData<ArticleResponse> getRsDataArticlev1(@PathVariable("id") Long id){

        Article article = articleService.getArticle(id);
        ArticleDTO articleDTO = new ArticleDTO(article);
        return RsData.of("200", "게시물 1건 조회 성공", new ArticleResponse(articleDTO));
    }

    @PostMapping("")
    public String create(@RequestParam("subject") String subject, @RequestParam("content") String content){
        System.out.println(subject);
        System.out.println(content);
        return "등록";
    }
    @PostMapping("/rsdata")
    public String createRsData(@Valid @RequestBody ArticleCreateRequest articleCreateRequest){
        System.out.println(articleCreateRequest.getSubject());
        System.out.println(articleCreateRequest.getContent());
        return "등록";
    }

    /*실제 서비스와 리포지터리를 이용한 데이터 이동실습*/
    @PostMapping("/rsdatav1")
    public RsData<ArticleCreateResponse> createRsDatav1(@Valid @RequestBody ArticleCreateRequest articleCreateRequest){
        Article article = articleService.write(articleCreateRequest.getSubject(), articleCreateRequest.getContent());
        return RsData.of("200", "등록성공", new ArticleCreateResponse(article));
    }

    @PatchMapping("/{id}")
    public String modify(@PathVariable("id") Long id, @RequestParam("subject") String subject, @RequestParam("content") String content){
        System.out.println(id);
        System.out.println(subject);
        System.out.println(content);
        return "수정";
    }

    @PatchMapping("/rsdatamodify/{id}")
    public String modifyRsdata(@PathVariable("id") Long id, @Valid @RequestBody ArticleModifyRequest articleModifyRequest){
        System.out.println(id);
        System.out.println(articleModifyRequest.getSubject());
        System.out.println(articleModifyRequest.getContent());
        return "수정";
    }
    @PatchMapping("/rsdatamodifyv1/{id}")
    public RsData<ArticleModifyResponse> modifyRsdatav1(@PathVariable("id") Long id, @Valid @RequestBody ArticleModifyRequest articleModifyRequest){
        Article article = articleService.getArticle(id);

        if(article == null) return RsData.of(
                    "500",
                    "%d번 게시물은 존재하지 않습니다".formatted(id),
                    null
            );
        article = articleService.update(
                article,
                articleModifyRequest.getSubject(),
                articleModifyRequest.getContent()
        );

        return RsData.of("200","수정성공", new ArticleModifyResponse(article));
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println(id);
        return "삭제";
    }
    @DeleteMapping("/v1/{id}")
    public RsData<ArticleResponse> deletev1(@PathVariable("id") Long id){
        Article article = articleService.getArticle(id);
        if(article == null) return RsData.of(
                "500",
                "%d번 게시물은 존재하지 않습니다".formatted(id),
                null
        );
        articleService.delete(article);
        ArticleDTO articleDTO = new ArticleDTO(article);
        return RsData.of("200","삭제성공", new ArticleResponse(articleDTO));
    }
}
