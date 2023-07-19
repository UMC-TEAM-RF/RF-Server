package org.rf.rfserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailRegex {
    UNIVERSITY_MAIL("UNIVERSITY_MAIL_REGEX", "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.+a+c+\\.+k+r+$"),  // 학교 메일 확인 정규식
    MAIL_PARSE("MAIL_PARSE_REGEX", "[@.]");     // "@"와 "."를 구분자로 사용해 학교 이름 추출

    private final String name;
    private final String regex;
}
