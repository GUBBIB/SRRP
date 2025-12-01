package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class InvalidInputValueException extends GlobalException {

    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
    public InvalidInputValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
