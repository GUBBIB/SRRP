package com.github.gubbib.backend.DTO.Post;

import lombok.Builder;

@Builder
public record PostCreateRequestDTO(
        Long postId,
        Long boardId,
        String title,
        String content
) {
}