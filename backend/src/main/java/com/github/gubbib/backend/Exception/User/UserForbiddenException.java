package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserForbiddenException extends GlobalException {
    public UserForbiddenException() {
        super(ErrorCode.USER_FORBIDDEN);
    }
    public UserForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
