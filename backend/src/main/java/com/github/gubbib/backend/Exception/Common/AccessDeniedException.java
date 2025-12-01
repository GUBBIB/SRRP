package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class AccessDeniedException extends GlobalException {

    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
