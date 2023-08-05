package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PreferAges {
    NONE("무관")
    ,EARLY_TWENTIES("20 초반")
    ,MID_TWENTIES("20 중반")
    ,LATE_TWENTIES("20 후반")
    ;
    private final String content;
}
