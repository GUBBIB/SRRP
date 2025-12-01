package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserPasswordNotMatchException extends GlobalException {
    public UserPasswordNotMatchException() {
        super(ErrorCode.USER_PASSWORD_NOT_MATCH);
    }
    public UserPasswordNotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
