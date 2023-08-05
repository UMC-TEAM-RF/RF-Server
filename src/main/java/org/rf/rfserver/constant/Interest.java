package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Interest implements EnumModel{
    MUSIC("음악")
    ,KPOP("K-pop")
    ,SPORT("스포츠")
    ,SPORT_GAME("경기")
    ,LANGUAGE_EXCHANGE("언어 교환")
    ,LANGUAGE("언어")
    ,COUNTRY("국가")
    ,FRIENDSHIP("친목")
    ,FOOD("음식")
    ,COOKING("요리")
    ,HOT_PLACE("맛집")
    ,CAFE("카페")
    ,STUDY("공부")
    ,MAJOR("전공")
    ,GRADE("학점")
    ,READING("독서")
    ;
    private final String value;

    @Override
    public String getKey() {return name();}
}
