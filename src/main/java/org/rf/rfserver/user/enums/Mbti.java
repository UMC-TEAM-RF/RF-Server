package org.rf.rfserver.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Mbti {
    INFP("INFP")
    ,INFJ("INFJ")
    ,INTP("INTP")
    ,INTJ("INTJ")
    ,ISFP("ISFP")
    ,ISFJ("ISFJ")
    ,ISTP("ISTP")
    ,ISTJ("ISTJ")
    ,ENFP("ENFP")
    ,ENFJ("ENFJ")
    ,ENTP("ENTP")
    ,ENTJ("ENTJ")
    ,ESFP("ESFP")
    ,ESFJ("ESFJ")
    ,ESTP("ESTP")
    ,ESTJ("ESTJ")
    ;
    private final String name;
}
