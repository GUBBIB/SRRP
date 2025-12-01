package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class BadRequestException extends GlobalException {

    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
