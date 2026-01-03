package com.github.gubbib.backend.DTO.Post;

import lombok.Builder;

@Builder
public record PostUpdateResponseDTO(
        Long postId,
        Long boardId
) {}