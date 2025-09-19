package com.example.demo.global.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RsData<T> {
    //500, 400번 에러메세지가 나오는 변수
    private String resultCode;
    //메세지 출력 변수
    private String msg;

    private T data;

    public static <T> RsData<T> of(String resultCode, String msg, T data){
        return new RsData<>(resultCode, msg, data);
    }
    public static <T> RsData<T> of(String resultCode, String msg){
        return new RsData<>(resultCode, msg, null);
    }
    //@JsonIgnore 은 json에 표시하고 싶지 않은 정보를 가릴수 있다
    @JsonIgnore
    public boolean isSuccess(){
        return resultCode.startsWith("200");
    }
    @JsonIgnore
    public boolean isFail(){
        return !isSuccess();
    }
}
