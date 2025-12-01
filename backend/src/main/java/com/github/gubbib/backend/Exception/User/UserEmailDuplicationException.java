package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserEmailDuplicationException extends GlobalException {
    public UserEmailDuplicationException() {
        super(ErrorCode.USER_EMAIL_DUPLICATION);
    }
    public UserEmailDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
