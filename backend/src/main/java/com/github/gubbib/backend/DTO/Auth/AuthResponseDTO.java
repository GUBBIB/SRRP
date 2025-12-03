package com.github.gubbib.backend.DTO.Auth;

import lombok.Builder;

@Builder
public record AuthResponseDTO(
        Long userId,
        String email,
        String nickname
) {
}
