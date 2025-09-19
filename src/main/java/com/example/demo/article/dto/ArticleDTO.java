package com.example.demo.global.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


public class ArticleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
