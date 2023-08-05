package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Country implements EnumModel{
    UNITED_STATES("미국")
    ,JAPAN("일본")
    ,KOREA("한국")
    ,CHINA("중국")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
