package com.example.demo.article.service;

import com.example.demo.article.entity.Article;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.global.jpa.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    public List<ArticleDTO> getList(){
        List<Article> articleList = articleRepository.findAll();
        /*받아온 리스트를 DTO에 넣는다*/
        List<ArticleDTO> articleDTO = articleList.stream()/*stream문법이용*/
                .map(article -> new ArticleDTO(article)) /*article을 DTO형태로 하나하나 변환*/
                .collect(Collectors.toList()); /*다시 DTO형태를 List에 저장한다*/
        return articleDTO;
    }
    public Article getArticle(Long id){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        //eturn optionalArticle.map(article -> new ArticleDTO(article)).orElse(null);
        return optionalArticle.orElse(null);
    }
    public Article write(String subject, String content){
        /*build를 사용하기 위해서는 @SuperBuild를 붙여놓아야 한다*/
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();
        articleRepository.save(article);
        return article;
    }
    public Article update(Article article, String subject, String content){
        article.setSubject(subject);
        article.setContent(content);
        articleRepository.save(article);
        return article;
    }
    public void delete(Article article){
        articleRepository.delete(article);
    }
}
