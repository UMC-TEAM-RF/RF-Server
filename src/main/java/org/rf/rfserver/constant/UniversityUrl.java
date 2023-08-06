package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.rf.rfserver.constant.University.*;

@Getter
@RequiredArgsConstructor
public enum UniversityUrl {
    INHA1("inha.ac.kr", INHA)
    ,INHA2("inha.edu", INHA)
    ,HANYANG1("hanyang.ac.kr", HANYANG)
    ,HANYANG2("hmail.hanyang.ac.kr", HANYANG)
    ,TUKOREA1("tukorea.ac.kr",TUKOREA)
    ,CATHOLIC1("catholic.ac.kr",CATHOLIC)
    ,CATHOLIC2("songeui.ac.kr",CATHOLIC)
    ;
    private final String url;
    private final University university;
    private static final Map<String, UniversityUrl> BY_URL = Stream.of(values()).collect(Collectors.toMap(UniversityUrl::getUrl, e-> e));
    public static University getUniversityByUrl(String url) {return BY_URL.get(url).university;}
}
