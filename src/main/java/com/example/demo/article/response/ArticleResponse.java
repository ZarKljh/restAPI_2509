package com.example.demo.article.response;


import com.example.demo.global.jpa.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private final ArticleDTO article;
}
