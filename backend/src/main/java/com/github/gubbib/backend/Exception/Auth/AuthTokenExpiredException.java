package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthTokenExpiredException extends GlobalException {
    public AuthTokenExpiredException() {
        super(ErrorCode.AUTH_TOKEN_EXPIRED);
    }
    public AuthTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
