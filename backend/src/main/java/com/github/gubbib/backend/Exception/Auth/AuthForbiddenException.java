package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthForbiddenException extends GlobalException {
    public AuthForbiddenException() {
        super(ErrorCode.AUTH_FORBIDDEN);
    }
    public AuthForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
