package com.github.gubbib.backend.DTO.Board;

import lombok.Builder;

@Builder
public record BoardCreateDTO(
        String title,
        String description
) {
}
