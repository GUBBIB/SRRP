package com.github.gubbib.backend.DTO.Error;

import com.github.gubbib.backend.Exception.ErrorCode;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDTO(
        int status,
        String code,
        String message,
        String path,
        LocalDateTime timestamp
) {

    public static ErrorResponseDTO of(ErrorCode errorCode, String path) {
        return ErrorResponseDTO.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
