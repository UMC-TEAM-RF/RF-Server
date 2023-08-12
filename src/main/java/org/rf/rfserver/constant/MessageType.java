package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType implements EnumModel {
    TEXT("텍스트")
    ,IMAGE("이미지")
    ,SCHEDULE("일정")
    ,REPLY("답장")
    ,INVITE("초대")
    ,LEAVE("나감")
    ,KICK_OUT("강퇴")
    ,ERROR_MASSAGE("오류")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
