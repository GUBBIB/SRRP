package com.github.gubbib.backend.DTO.Post;

import lombok.Builder;

@Builder
public record PostDeleteResponseDTO(
        Long postId,
        Long boardId
) {}