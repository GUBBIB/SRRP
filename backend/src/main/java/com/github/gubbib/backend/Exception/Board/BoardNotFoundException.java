package com.github.gubbib.backend.Exception.Board;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class BoardNotFoundException extends GlobalException {
    public  BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }

    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
