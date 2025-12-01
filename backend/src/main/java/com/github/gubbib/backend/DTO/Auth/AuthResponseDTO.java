package com.github.gubbib.backend.DTO.Auth;

public record AuthResponseDTO(
        Long userId,
        String email,
        String refreshToken,
        String accessToken
) {
}
