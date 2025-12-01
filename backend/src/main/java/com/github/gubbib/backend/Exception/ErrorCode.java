package com.github.gubbib.backend.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 요청입니다."),

    // Auth(인증) 관련
    AUTH_EMAIL_DUPLICATION(HttpStatus.CONFLICT, "A001", "이미 가입된 이메일입니다."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "A002", "이메일 또는 비밀번호가 올바르지 않습니다."),

    // USER 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
    USER_NICKNAME_DUPLICATION(HttpStatus.CONFLICT, "U002", "이미 존재하는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
