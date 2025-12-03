package com.github.gubbib.backend.DTO.Auth;

import org.springframework.http.ResponseCookie;

public record AuthResultDTO (
        AuthResponseDTO authResponseDTO,
        ResponseCookie accessCookie,
        ResponseCookie refreshCookie
) {
}
