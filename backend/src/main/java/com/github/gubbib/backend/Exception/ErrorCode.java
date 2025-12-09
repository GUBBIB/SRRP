package com.github.gubbib.backend.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "요청 값이 유효하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "지원하지 않는 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "요청한 리소스를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C006", "잘못된 요청입니다."),

    // Auth(인증/인가) 관련
    AUTH_EMAIL_DUPLICATION(HttpStatus.CONFLICT, "A001", "이미 가입된 이메일입니다."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "A002", "이메일 또는 비밀번호가 올바르지 않습니다."),
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A003", "인증이 필요합니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "A004", "해당 기능에 대한 접근 권한이 없습니다."),
    AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A005", "유효하지 않은 토큰입니다."),
    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A006", "만료된 토큰입니다."),
    AUTH_PROVIDER_MISMATCH(HttpStatus.BAD_REQUEST, "A007", "제공자(provider)가 올바르지 않습니다."),
    AUTH_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A008", "유효하지 않은 리프레시 토큰입니다."),

    // User 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
    USER_NICKNAME_DUPLICATION(HttpStatus.CONFLICT, "U002", "이미 존재하는 닉네임입니다."),
    USER_EMAIL_DUPLICATION(HttpStatus.CONFLICT, "U003", "이미 존재하는 이메일입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "U004", "이미 가입된 사용자입니다."),
    USER_STATUS_INACTIVE(HttpStatus.BAD_REQUEST, "U005", "비활성화된 사용자입니다."),
    USER_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "U006", "비밀번호가 일치하지 않습니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN, "U007", "사용자 권한이 충분하지 않습니다."),
    USER_SAME_AS_OLD_PASSWORD(HttpStatus.CONFLICT, "U008", "이전 비밀번호와 동일합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
