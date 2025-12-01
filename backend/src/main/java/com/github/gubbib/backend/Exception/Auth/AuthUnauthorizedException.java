package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthUnauthorizedException extends GlobalException {
    public AuthUnauthorizedException() {
        super(ErrorCode.AUTH_UNAUTHORIZED);
    }
    public AuthUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
