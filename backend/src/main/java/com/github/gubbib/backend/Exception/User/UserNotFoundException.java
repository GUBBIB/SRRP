package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
