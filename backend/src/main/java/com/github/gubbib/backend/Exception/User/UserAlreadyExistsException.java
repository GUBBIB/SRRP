package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserAlreadyExistsException extends GlobalException {
    public UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
    public UserAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
