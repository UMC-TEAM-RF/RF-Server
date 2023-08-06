package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum University implements EnumModel{
    INHA("인하대학교")
    ,HANYANG("한양대학교")
    ,TUKOREA("한국공학대학교")
    ,CATHOLIC("가톨릭대학교")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
