package com.example.demo.global.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass //부모가 되는 entity에 붙여주는 어노테이션, @superBuilder를 붙여준 어노테이션은 이 부모 entity를 상속받는다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BaseEntity {

    //@MappedSuperclass 부모 클래스가 "DB 테이블"은 아니지만, 자식 클래스들이 공통으로 물려받을 필드들을 정의하는 클래스라는 표시
    //@EntityListeners((AuditingEntityListener.class)) --> 엔티티에서 자동으로 특정 동작을 감지하고 처리하게 만들어주는 어노테이션

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
