package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LifeStyle {
    MORNING_HUMAN("아침형 인간")
    ,OWL_HUMAN("올빼미형 인간")
    ;
    private final String content;
}
