package com.github.gubbib.backend.Exception.Post;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;

public class PostNotFoundException extends GlobalException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }

    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
