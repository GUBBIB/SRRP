package com.github.gubbib.backend.DTO.Board;

import lombok.Builder;

@Builder
public record BoardDTO(
        Long boardId,
        String title,
        String description
) {
}
