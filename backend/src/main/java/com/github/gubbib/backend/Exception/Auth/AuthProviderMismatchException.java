package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthProviderMismatchException extends GlobalException {
    public AuthProviderMismatchException() {
        super(ErrorCode.AUTH_PROVIDER_MISMATCH);
    }
    public AuthProviderMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
