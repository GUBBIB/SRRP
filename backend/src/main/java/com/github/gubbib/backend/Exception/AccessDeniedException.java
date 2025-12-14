package com.github.gubbib.backend.Exception;

public class AccessDeniedException extends GlobalException {

    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
