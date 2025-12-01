package com.github.gubbib.backend.Exception.Auth;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AuthEmailDuplicationException extends GlobalException {

    public AuthEmailDuplicationException() {
        super(ErrorCode.AUTH_EMAIL_DUPLICATION);
    }
    public AuthEmailDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
