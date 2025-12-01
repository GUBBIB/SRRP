package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthInvalidCredentialsException extends GlobalException {
    public AuthInvalidCredentialsException() {
        super(ErrorCode.AUTH_INVALID_CREDENTIALS);
    }
    public AuthInvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
