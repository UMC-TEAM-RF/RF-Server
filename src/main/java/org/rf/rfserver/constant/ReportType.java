package org.rf.rfserver.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType implements EnumModel{
    USER("사용자")
    ,PARTY("그룹")
    ,CHAT("채팅")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
