package com.github.gubbib.backend.Exception.User;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class UserNicknameDuplicationException extends GlobalException {
    public UserNicknameDuplicationException() {
        super(ErrorCode.USER_NICKNAME_DUPLICATION);
    }
    public UserNicknameDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
