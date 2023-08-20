package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailMessage implements EnumModel{
    RESET_PASSWORD("비밀번호 재설정을 위한 이메일이 발송되었습니다. 이메일을 확인해 주세요."),
    FIND_ID("아이디를 찾기 위한 이메일이 발송되었습니다. 이메일을 확인해 주세요.")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
