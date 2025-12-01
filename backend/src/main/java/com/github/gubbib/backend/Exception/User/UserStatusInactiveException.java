package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserStatusInactiveException extends GlobalException {
    public UserStatusInactiveException() {
        super(ErrorCode.USER_STATUS_INACTIVE);
    }
    public UserStatusInactiveException(ErrorCode errorCode) {
        super(errorCode);
    }
}
