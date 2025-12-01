package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class MethodNotAllowedException extends GlobalException {

    public MethodNotAllowedException() {
        super(ErrorCode.METHOD_NOT_ALLOWED);
    }
    public MethodNotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
