package com.github.gubbib.backend.DTO.Comment;

import lombok.Builder;

@Builder
public record CommentCreateResponseDTO(
        Long boardId,
        Long postId
) {
}
