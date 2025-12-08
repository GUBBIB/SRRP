package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserSameAsOldPasswordException extends GlobalException {
    public UserSameAsOldPasswordException() {
        super(ErrorCode.USER_SAME_AS_OLD_PASSWORD);
    }
    public UserSameAsOldPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
