package com.github.gubbib.backend.Exception.Common;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class EntityNotFoundException extends GlobalException {

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
