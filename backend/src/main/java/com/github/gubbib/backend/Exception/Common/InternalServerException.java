package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class InternalServerException extends GlobalException {
    public InternalServerException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
