package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Language {
    KOREAN("한국어")
    ,CHINESE("중국어")
    ,JAPANESE("일본어")
    ,ENGLISH("영어")
    ;
    private final String koreanName;
}
