package com.github.gubbib.backend.DTO.Post;

import lombok.Builder;

@Builder
public record PostCreateResponseDTO(
        Long postId,
        Long boardId
) {
}
