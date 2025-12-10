package com.github.gubbib.backend.DTO.Board;

import lombok.Builder;

@Builder
public record BoardListDTO(
        Long boardId,
        String boardTitle,
        String boardDescription
) {
}
