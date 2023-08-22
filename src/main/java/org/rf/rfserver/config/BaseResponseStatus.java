package org.rf.rfserver.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_USER(false, 2004, "해당 유저가 존재하지 않습니다"),
    INVALID_PARTY(false, 2005, "해당 모임이 존재하지 않습니다"),
    INVALID_JOIN_APPLICATION(false, 2006, "이미 가입한 모임입니다"),
    EXCEEDED_PARTY_COUNT(false, 2007, "모임은 5개를 초과할 수 없습니다"),
    EXCEEDED_PARTY_USER_COUNT(false, 2008, "해당 모임에 가입할 수 있는 인원이 초과되었습니다."),
    INVALID_APPLICATION(false, 2009, "해당하는 요청이 존재하지 않습니다"),
    FULL_OF_KOREAN(false, 2010, "해당 모임에 가입할 수 있는 한국인 인원이 초과되었습니다."),
    NOT_RECRUITING(false, 2011, "해당 모임은 모집마감 상태입니다."),
    DUPLICATED_LOGIN_ID(false, 2012, "해당 아이디가 이미 존재합니다. 다른 아이디를 사용해주세요"),
    NOT_OWNER(false, 2013, "모임장이 아니면 강퇴할 수 없습니다"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),


    INVALID_MAIL(false, 6002, "유효하지 않은 이메일입니다."),
    INVALID_CODE(false, 6003, "인증번호가 올바르지 않습니다."),
    INVALID_UNIVERSITY(false, 6004, "해당 학교의 이메일 주소가 아닙니다."),
    NOT_USER_MAIL(false,6005,"해당 아이디에 가입된 이메일과 일치하지 않습니다."),
    NO_SUCH_USER(false, 5000, "존재하지 않는 사용자 입니다."),
    NO_SUCH_PARTY(false, 5001, "존재하지 않는 그룹 입니다."),
    NO_SUCH_CHAT(false, 5002, "존재하지 않는 채팅 입니다."),
    USER_NOT_FOUND(false, 7001, "해당 사용자를 찾을 수 없습니다."),
    PARTY_NOT_FOUND(false, 7002, "해당 모임을 찾을 수 없습니다."),
    ALREADY_LEFT_PARTY(false, 7003, "이미 나간 모임입니다."),
    ALREADY_JOINED_PARTY(false, 7004, "이미 가입한 모임입니다."),
    NOT_JOINED_PARTY(false, 7005, "가입한 모입이 아닙니다."),
    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요
    NOT_PARTY_OWNER(false,7006,"모임장이 아니면 해당 권한이 없습니다."),
    SCHEDULE_NOT_FOUND(false,7007,"모임장이 아니면 해당 권한이 없습니다.")
    ;
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
