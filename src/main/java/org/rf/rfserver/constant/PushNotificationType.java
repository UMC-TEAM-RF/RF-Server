package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PushNotificationType implements EnumModel{
    DEFAULT("기본")
    , CHAT("채팅")
    , APPLY("가입 요청")
    , APPROVE("가입 수락")
    , DENY("가입 거절")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
