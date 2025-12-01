package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthRefreshTokenInvalidException extends GlobalException {
    public AuthRefreshTokenInvalidException() {
        super(ErrorCode.AUTH_REFRESH_TOKEN_INVALID);
    }
    public AuthRefreshTokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
