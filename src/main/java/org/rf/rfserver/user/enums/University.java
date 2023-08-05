package org.rf.rfserver.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum University {
    INHA("인하대학교")
    ,HANYANG("한양대학교")
    ,TUKOREA("한국공학대학교")
    ,CATHOLIC("가톨릭대학교")
    ;
    private final String koreanName;

}
