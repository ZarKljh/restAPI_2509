package com.example.demo.global.jpa;

import com.example.demo.article.entity.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDTO {

    private final Long id;
    private final String subject;

    private final String content;

    private final LocalDateTime createDate;

    private final LocalDateTime modifiedDate;

    public ArticleDTO(Article article){
        this.id = article.getId();
        this.subject = article.getSubject();
        this.content = article.getContent();
        this.createDate = article.getCreateDate();
        this.modifiedDate = article.getModifiedDate();
    }
}
