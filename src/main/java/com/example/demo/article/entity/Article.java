package com.example.demo.article.entity;

import com.example.demo.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {

    //@SuperBuilder  -->객체를 만드는 방법을 더 안전하고 보기 쉽게 해준다
    //Student s = new Student("철수", 15, "중학교");
    /*
    Student s = Student.builder()
                   .name("철수")
                   .age(15)
                   .school("중학교")
                   .build();
     */

    //ToString(callsuper = true)   --> 객체 안에 있는 값들을 글자로 예쁘게 보여주는 기능이에요. (일종의 자동 설명서 출력)
     /*

      */
    private String subject;
    private String content;
}
