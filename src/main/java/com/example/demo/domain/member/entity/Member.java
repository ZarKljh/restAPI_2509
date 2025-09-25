package com.example.demo.domain.member.entity;

import com.example.demo.global.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
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
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    //보통값들은 json으로 전달되지만 패스워드 같은 민감한 정보는 json으로 주고받지 못하게 해야한다.
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String refreshToken;
}
