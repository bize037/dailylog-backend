package com.project.dailylog.common.constants;

public enum ErrorMessage {
    WRONG_JWT_TOKEN("잘못된 JWT 토큰입니다."),
    NON_JWT_AUTH_INFO("JWT 인증 정보가 존재하지 않습니다."),
    EXPIRED_JWT_TOKEN("만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN("지원하지 않는 JWT 토큰입니다."),
    EMPTY_JWT_TOKEN("JWT 토큰이 비어 있습니다."),
    EMPTY_EMAIL("존재하지 않는 이메일입니다."),
    ALREADY_USED_EMAIL("이미 사용 중인 이메일입니다."),
    NOT_FOUND_USER("존재하지 않는 사용자입니다."),
    NOT_FOUND_BOARD("존재하지 않는 글입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
