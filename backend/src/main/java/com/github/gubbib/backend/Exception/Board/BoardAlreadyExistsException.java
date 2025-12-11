package com.github.gubbib.backend.Exception.Board;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class BoardAlreadyExistsException extends GlobalException {
    public BoardAlreadyExistsException() {
        super(ErrorCode.BOARD_ALREADY_EXISTS);
    }

    public BoardAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
