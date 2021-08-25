package com.example.ordermanagement.domain.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum GradeStatus {

    NORMAL(0,"일반회원","신입 회원"),
    BRONZE(1,"브론즈","1년동안 5만원 이상 구입 회원"),
    SILVER(2,"실버","1년동안 50만원이상 구입 회원"),
    GOLD(3,"골드","1년동안 100만원 이상 구입 회원"),
    PLATINUM(4,"플레티넘","1년동안 500만원 이상 구입 회원"),
    DIAMOND(5,"다이아몬드","1년동안 1000만원 이상 구입 회원"),
    GRANDMASTER(6,"그랜드마스터","1년동안 2000만원 이상 구입 회원");

    private Integer gradeId;
    private String title;
    private String description;

    public static GradeStatus getData(Integer id){
        return Arrays.stream(GradeStatus.values())
                .filter(v->v.getGradeId().equals(id))
                .findAny()
                .orElseThrow(()->new NoSuchElementException("값이 존재하지 않습니다."));
    }
}
