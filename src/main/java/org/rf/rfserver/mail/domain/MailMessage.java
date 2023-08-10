package org.rf.rfserver.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailMessage {
    MAIL_TITLE("[알프] 인증번호를 발송했습니다."),
    MAIL_MESSAGE("[학교 인증을 위한 메일]\n"
            + "이메일 인증을 진행해 주세요.\n"
            + "아래의 인증번호를 입력하여 회원가입을 완료하세요.\n")
    ;

    private final String content;
}
