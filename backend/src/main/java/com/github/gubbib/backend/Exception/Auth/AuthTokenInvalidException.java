package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthTokenInvalidException extends GlobalException {
    public AuthTokenInvalidException() {
        super(ErrorCode.AUTH_TOKEN_INVALID);
    }
    public AuthTokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
